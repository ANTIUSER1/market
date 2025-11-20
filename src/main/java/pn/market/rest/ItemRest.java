package pn.market.rest;

import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pn.market.entities.Item;
import pn.market.repo.ItemRepo;
import pn.market.services.autocreate.ItemsCreateService;
import pn.market.services.autocreate.OrdersCreateService;
import pn.market.services.impl.ItemServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@Slf4j
public class ItemRest {


    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ItemsCreateService itemsCreateService;

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private OrdersCreateService ordersCreateService;


    @Autowired
    private ServletContext servletContext;

    @GetMapping("/setof/{count}")
    public List<Item> createSet(@PathVariable("count") int count) {

        List<Item> items = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            Item item = new Item();
            item.setTitle("Item " + i);
            item.setDescription("Description " + i);
            item.setCount((int) (100*Math.random()));
            item.setImgPath( i+".jpg");
            item.setPrice((long) (i+10000*Math.random()));
            items.add(item);
        }
        items=itemRepo.saveAll(items);
        return items;
    }

    @PutMapping("/img/{id}")
    public ResponseEntity<?> loadItemImage(
           @PathVariable("id") Long id, @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (file != null) {
            Item item = itemService.uploadFile(file, id);
            if (item != null)
                return ResponseEntity.ok(item);
        }
        return ResponseEntity.badRequest().build();
    }
}
