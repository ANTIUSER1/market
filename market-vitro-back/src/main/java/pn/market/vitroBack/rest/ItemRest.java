package pn.market.vitroBack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
//    public Mono<Item> itemSaveId(
            @PathVariable("userId") Long userId,
            @PathVariable("itemId") Long itemId) {
        return  orderService.createOrUseCartOfUser(userId,itemId);
    }



    @GetMapping("/get-cart-of-user/{userId}")
    public Flux<Item> getCartOfUser(@PathVariable("userId") Long userId) {
        return itemService.getItemsOfUser(userId);
    }

    @GetMapping("/get-total-sum-cart-of-user/{userId}")
    public Mono<Long> getTotalCartOfUser(@PathVariable("userId") Long userId) {
        return itemService.getTotalSumCartOfUser(userId);
    }

//    @GetMapping("/remove-order/{orderId}")
//    public Flux<Item> removeItemsFromOrderId(@PathVariable("orderId") Long orderId) {
//        return itemService.removeItemsFromOrderId(orderId);
//    }

    @GetMapping("/by-order/{orderId}")
    public Flux<Item> getItemsByOrderId(@PathVariable("orderId") Long orderId) {
        return itemService.getItemsByOrderId(orderId);
    }

//    @GetMapping("/add-item-to-cart/{itemID}/{cartID}/{action}")
//    public Mono<Item> addToCart(
//            @PathVariable("itemID") Long itemId,
//            @PathVariable("cartID") Long cartId,
//            @PathVariable("action") String action) {
//        Mono<Item> itemMono = itemService.findById(itemId)
//                .map(i -> itemService.addToCart(i, cartId, action))
//                .flatMap(i -> i);
//        return itemMono;
//    }


//
//    @GetMapping("/get-by-cart/{cartId}")
//    public Flux<Item> getItemsByCartIdToFlux(
//            @PathVariable("cartId") Long cartId
//    ) {
//        return itemService.getItemsByCartIdToFlux(cartId);
//    }
//
//    @GetMapping("/get-total-sum/{cartId}")
//    public Mono<Long> getTotalSum(
//            @PathVariable("cartId") Long cartId) {
//        Flux<Item> itemsFlux = this.getItemsByCartIdToFlux(cartId);
//        System.out.println("   NNN --- TOTAL SUM ");
//        return itemService.getTotalSum(itemsFlux);
//    }


}
