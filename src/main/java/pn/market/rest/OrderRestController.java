package pn.market.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.entities.Item;
import pn.market.entities.Order;
import pn.market.repo.OrderRepo;
import pn.market.services.autocreate.OrdersCreateService;
import pn.market.services.impl.ItemServiceImpl;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
public class OrderRestController {
    @Autowired
    ItemServiceImpl itemService;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrdersCreateService ordersCreateService;

    @GetMapping("/orders/create/{itemId}")
    public ResponseEntity<?> addItemToNewOrder(@PathVariable("itemId") long itemId) {
        Optional<Item> item = itemService.findById(itemId);
        if (item.isPresent()) {
            Order order = ordersCreateService.createOrder(item.get());
            // item.get().setOrder(order);
            order = orderRepo.save(order);
            System.out.println("\n-- NEW --\n " + order);
            return ResponseEntity.ok(order);
        }

        return ResponseEntity.ok("item not exists");
    }

    @GetMapping("/orders/add/{orderId}")
    public ResponseEntity<?> addManyRandomItemsToNewOrder(@PathVariable("orderId") long orderId) {
        Order order = ordersCreateService.addRandomIremSetToOrder(orderId);
        if (order != null) {
            order = orderRepo.save(order);
            System.out.println("\n--   --\n " + order.toString());
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.ok("order not exists");
    }

}

