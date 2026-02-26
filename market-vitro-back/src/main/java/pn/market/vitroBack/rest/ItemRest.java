package pn.market.vitroBack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.market_entities.forWEB.Item;
import pn.market.market_entities.forWEB.Order;
import pn.market.vitroBack.servicies.impl.ItemServiceImpl;
import pn.market.vitroBack.servicies.impl.OrderServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/vitro/items")
public class ItemRest {


    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping
    public Flux<Item> allItems() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Item> itemById(@PathVariable("id") Long id) {
        return itemService.findById(id);
    }

    @GetMapping("/addOrder/{userId}/{itemId}")
    public Mono<Order> addToExistingOrderOfUser(
            @PathVariable("userId") Long userId,
            @PathVariable("itemId") Long itemId) {
        return orderService.createOrUseCartOfUser(userId, itemId);
    }

    @GetMapping("/get-items-by-cart/{cartId}")
    public Flux<Item> getItemsByCartId(
            @PathVariable("cartId") Long cartId
    ) {
        return itemService.getItemsByCartIdT(cartId);
    }

    @GetMapping("/get-cart-of-user/{userId}")
    public Flux<Item> getCartOfUser(@PathVariable("userId") Long userId) {
        return itemService.getItemsOfUser(userId);
    }

    @GetMapping("/get-total-sum-cart-of-user/{userId}")
    public Mono<Long> getTotalCartOfUser(@PathVariable("userId") Long userId) {
        return itemService.getTotalSumCartOfUser(userId);
    }

    @GetMapping("/by-order/{orderId}")
    public Flux<Item> getItemsByOrderId(@PathVariable("orderId") Long orderId) {
        return itemService.getItemsByOrderId(orderId);
    }

}
