package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pn.market.market_entities.Paging;
import pn.market.market_entities.TService;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroBack.additional.ActionType;
import pn.market.vitroBack.error.FileException;
import pn.market.vitroBack.repo.ItemRepo;
import pn.market.vitroBack.repo.OrderRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ItemServiceImpl implements TService<Item> {

    private int pageSize;
    private int offset;
    @Value("${spring.web.resources.static-locations}")

    private String imgPath;
    @Autowired
    private FileServiceImpl fileService;
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private CartServiceImpl cartService;
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
    public Mono<Item> getById(Long id) {
        return itemRepo.findById(id);
    }

    @Override
    public Mono<Paging> findAllAndPaging(Mono<Pageable> pageable) {

    /*
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

     */
        return null;
    }

    public Flux<Item> getItemsByCart(Long id) {
        Flux<Item> items = itemRepo.findByCartId(id);
        return items;
    }

    public Flux<Item> getItemsByOrderId(Long id) {
        return itemRepo.findByOrderId(id);
    }


    public Mono<Long> getTotalSum(Flux<Item> items) {
        return items.collectList()
                .map(i -> {
                    System.out.println("   SUM COUNT!!!   " + i);
                    return i.stream()
                            .mapToLong(it -> it.getPrice() * 100000).sum();
                });
    }

    public Mono<Item> plusForMono(Item item, long cartId) {
        System.out.println("-----BEFORE -----");

        return itemRepo.save(item);//.flatMap(i -> itemRepo.findById(i.getId())).log();
    }

    public Mono<Item> uploadFileMono(MultipartFile file, Long id) {
        if (id == null) {
            throw new RuntimeException();
        }
        Mono<Item> itemMono = itemRepo.findById(id);
        itemMono = itemMono.map(i -> {
                    String dirToUpload = createImagePath();
                    AtomicReference<String> fileName = new AtomicReference<>();
                    if (fileName.get() != null) {
                        try {
                            fileService.storeFile(file, dirToUpload, id)
                                    .subscribe(s -> fileName.set(s));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (FileException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    i.setImgPath(String.valueOf(fileName));
                    return i;
                }
        );
        return itemMono;
    }

    public String createImagePath() {
        imgPath = imgPath.split("file:")[1]
                .replace("//", "/")
                .replace(",", "");

        return imgPath;
    }

    public Mono<Item> addToCart(Item i, long cartId, String action) {
        System.out.println("*******  " + (ActionType.PLUS.name().equalsIgnoreCase(action.trim())));
        if (ActionType.PLUS.name().equalsIgnoreCase(action.trim())) {
            System.out.println("      ++++++++");
            return this.plusForMono(i, cartId);
        }
        if (ActionType.MINUS.name().equalsIgnoreCase(action.trim())) {
            return this.minusForMono(i);
        }
        return Mono.just(i);
    }

    public Mono<Item> save(Item item) {
        System.out.println("           ITEM SAVING  \n " + item);
        return itemRepo.save(item);
    }

    public Flux<Item> getItemsByCartIdToFlux(Long id) {
        return itemRepo.findByCartId(id);
    }

    public Mono<Item> minusForMono(Item item) {
        return itemRepo.save(item);
    }


    public Flux<Item> getItemsByCartDataFromMonoToFlux(Mono<Item> itemMono) {
        return Flux.empty();
//        return itemMono.map(i -> {
//            return this.getItemsByCartIdToFlux(i.getCartId());
//        }).flatMapMany(f -> f);
    }

    public Flux<Item> getItemsByCartDataFromMonoToFlux(long cartId) {
        return itemRepo.findByCartId(cartId);
    }

    public void removeFromOrder(long orderId) {
        itemRepo.findByOrderId(orderId).collectList()
                .map(items -> {
                    for (Item item : items) {
                        System.out.println("    remove order links in item: " + item);
                        itemRepo.save(item)
                                .map(
                                        i -> {
                                            System.out.println("    remove order links in item: "
                                                    + i + "\n        DONE! ");
                                            return i;
                                        }
                                )
                                .map(i -> {
                                    orderRepo.deleteById(orderId).log().subscribe();
                                    return i;
                                }).log()
                                .subscribe();
                    }
                    return items;
                })
                .subscribe();
        System.out.println("removed from order id: " + orderId);
    }

    public Flux<Item> removeItemsFromOrderId(Long orderId) {
        Flux<Item> itemFlux = itemRepo.findByOrderId(orderId)
                .map(ii -> {
                    return Mono.just(ii);
                }).flatMap(i -> i);
        return itemRepo.saveAll(itemFlux);
    }

    public Flux<Item> getCartOfUser(Long userId) {
        return itemRepo.findByUserId(userId)
                .map(i -> {
                    System.out.println("-----I III " + i);
                    return i;
                });
    }

    public Mono<Long> getTotalSumCartOfUser(Long userId) {
        Flux<Item> itemFlux = getCartOfUser(userId);
        return getTotalSum(itemFlux);
    }
}









