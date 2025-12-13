package pn.market.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pn.market.additional.ActionType;
import pn.market.additional.Paging;
import pn.market.entities.Item;
import pn.market.repo.ItemRepo;
import pn.market.services.TService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service

public class ItemServiceImpl implements TService<Item> {

    @Value("${spring.web.resources.static-locations}")
    private String imgPath;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private DatabaseClient databaseClient;

    public Mono<Item> findById(Long id) {
        return itemRepo.findById(id);
    }


    @Override
    public Flux<Item> findAll() {
        return itemRepo.findAll();
    }

    @Override
    public Optional<Item> getById(Long id) {
        return itemRepo.findById(id).blockOptional();
    }

    int pageSize;
    int offset;


    @Override
    public Mono<Paging> findAllAndPaging(Mono<Pageable> pageable) {
        Mono<Pageable> pageableMono = pageable.map(p -> {
                    pageSize = p.getPageSize();
                    offset = p.getPageNumber() * pageSize;
                    return p;
                }
        );
        Flux<Item> itemsFlux = itemRepo.findAll()
                .skip(offset)
                .take(pageSize);
        Mono<Long> countMono = itemRepo.count();

        return Mono.zip(itemsFlux.collectList(), countMono, pageable)
                .map(tuple -> {
                    List<Item> items = tuple.getT1();
                    long total = tuple.getT2();
                    Pageable p = tuple.getT3();
                    boolean hasPrevious = p.hasPrevious();
                    boolean hasNext = p.next() == null;

                    Paging paging = new Paging(
                            p.getPageSize(), p.getPageNumber(),
                            (int) (total / p.getPageSize() + 1),
                            hasNext, hasPrevious);
                    return paging;
                });
    }

    public List<Item> getItemsByCartId(Long id) {
        Flux<Item> items = itemRepo.findByCartId(id);
        return items.collectList().block();
    }

    public List<Item> getItemsByOrderId(Long id) {
        Flux<Item> items = itemRepo.findByOrderId(id);
        return items.collectList().block();
    }

    public long getTotalSum(List<Item> items) {
        return items.stream()
                .mapToLong(i -> i.getPrice() * i.getCount()).sum();
    }


    public Mono<Item> plusForMono(Item item, long cartId) {
        item.plusCount(cartId);
        System.out.println("       plusForMono  item = " + item);
        return itemRepo.save(item);//.flatMap(i -> itemRepo.findById(i.getId())).log();
    }

    public Item plus(Item item, long cartId) {
        item.plusCount(cartId);
        return itemRepo.save(item).block();
    }


    public Mono<Item> minusForMono(Item item) {
        item.minusCount();
        return itemRepo.save(item);
    }

    public Item minus(Item item) {
        item.minusCount();
        return itemRepo.save(item).block();
    }

    public Item uploadFile(MultipartFile file, long id) throws IOException {
        Optional<Item> itemOptional = itemRepo.findById(id).blockOptional();
        Item item = null;
        if (itemOptional.isPresent()) {
            item = itemOptional.get();
            String dirToUpload = createImagePath();
            String fileName = fileService.storeFile(file, dirToUpload, id).block();
            if (fileName != null) {
                item.setImgPath(fileName);
            }
        }
        return item;
    }

    public void setNullOderId(List<Item> items) {
        for (Item item : items) {
            item.setOrderId(null);
        }
        itemRepo.saveAll(items).blockLast();
    }


    public Mono<Long> getItemCartId(Mono<Item> itemMono) {
        if (itemMono == null) return null;
        return itemMono.map(Item::getCartId);
    }

    private String createImagePath() {
        imgPath = imgPath.split("file:")[1]
                .replace("//", "/")
                .replace(",", "");

        return imgPath;
    }

    public Mono<Item> addToCart(Item i, long cartId, String action) {
        if (ActionType.PLUS.name().equalsIgnoreCase(action.trim())) {
            return this.plusForMono(i, cartId);
        }
        if (ActionType.MINUS.name().equalsIgnoreCase(action.trim())) {
            return this.minusForMono(i);
        }
        return Mono.just(i);
    }
}
