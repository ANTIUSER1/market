package pn.market.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.entities.Cart;
import pn.market.entities.Item;
import pn.market.repo.CartRepo;
import pn.market.services.autocreate.CartsCreateService;
import pn.market.services.impl.ItemServiceImpl;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
public class CartRestController {
    @Autowired
    ItemServiceImpl itemService;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartsCreateService cartsCreateService;

    @GetMapping("/carts/create/{itemId}")
    public ResponseEntity<?> addItemToNewOrder(@PathVariable("itemId") long itemId) {
        Optional<Item> item = itemService.findById(itemId);
        if (item.isPresent()) {
            Cart cart = cartsCreateService.createCart(item.get());
            // item.get().setOrder(order);
            cart = cartRepo.save(cart);
            System.out.println("\n-- NEW --\n " + cart);
            return ResponseEntity.ok(cart);
        }
        return ResponseEntity.ok("item not exists");
    }

    @GetMapping("/carts/add/{orderId}")
    public ResponseEntity<?> addManyRandomItemsToNewOrder(@PathVariable("orderId") long orderId) {
        Cart cart = cartsCreateService.addRandomIremSetToCart(orderId);
        if (cart != null) {
            cart = cartRepo.save(cart);
            System.out.println("\n--   --\n " + cart.toString());
            return ResponseEntity.ok(cart);
        }
        return ResponseEntity.ok("order not exists");
    }

}

