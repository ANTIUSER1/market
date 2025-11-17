package pn.market.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.entities.Item;
import pn.market.repo.ItemRepo;
import pn.market.services.autocreate.ItemsCreateService;
import pn.market.services.autocreate.OrdersCreateService;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class ItemRestController {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ItemsCreateService itemsCreateService;

    @Autowired
    private OrdersCreateService ordersCreateService;

    @GetMapping("/items/add/{num}")
    public ResponseEntity<?> addMuliply(@PathVariable("num") int n) {
        if (n < 0) {
            return null;
        }
        List<Item> itemList = itemsCreateService.autoCreate(n);


        int result = itemRepo.saveAll(itemList).size();
        for (Item itl : itemList)
            System.out.println(itl);

        return ResponseEntity.ok(result);
        // return ResponseEntity.ok(0);
    }
}
