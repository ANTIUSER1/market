package pn.market.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pn.market.entities.Item;
import pn.market.repo.ItemRepo;
import pn.market.services.TService;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItemServiceImpl implements TService<Item> {

    @Value("${spring.web.resources.static-locations}")
    private String imgPath;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private ItemRepo itemRepo;

    @Override
    public Optional<Item> getById(Long id) {
        return itemRepo.findById(id).blockOptional();
    }

    @Override
    public Page<Item> findAllAndPaging(Pageable pageable) {
        List<Item> items = itemRepo.findAll().collectList().block();
        Page<Item> page = new PageImpl<>(items, pageable, items.size());
        System.out.println(page);
        return page;
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

    public Item plus(Item item, long cartId) {
        item.plusCount();
        item.setCartId(cartId);
        return itemRepo.save(item).block();
    }

    public Item minus(Item item) {
        item.minusCount();
        item.setCartId(null);
        return itemRepo.save(item).block();
    }

    public Item uploadFile(MultipartFile file, long id) throws IOException {
        Optional<Item> itemOptional = itemRepo.findById(id).blockOptional();
        Item item = null;
        if (itemOptional.isPresent()) {
            item = itemOptional.get();
            String dirToUpload = createImagePath();
            String fileName = fileService.storeFile(file, dirToUpload, id);
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
itemRepo.saveAll(items).log().blockLast();
    }

    private String createImagePath() {
        imgPath = imgPath.split("file:")[1]
                .replace("//", "/")
                .replace(",", "");

        return imgPath;
    }

}
