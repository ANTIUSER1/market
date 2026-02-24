package pn.market.vitroBack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroBack.servicies.impl.CartServiceImpl;
import pn.market.vitroBack.servicies.impl.ItemServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/vitro/items")
public class ItemRest {


    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private CartServiceImpl cartService;

    @GetMapping
    public Flux<Item> allItems() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Item> itemById(@PathVariable("id") Long id) {
         return itemService.findById(id);
    }

    @GetMapping("/addOrder/{userId}/{orderId}/{itemId}")
    public Mono<Item> itemSaveId(
            @PathVariable("userId") Long userId,
            @PathVariable("orderId") Long orderId,
            @PathVariable("itemId") Long itemId) {
        return  itemService.placeItemToOrderOfUser(userId, orderId,itemId);
//                itemService.findById(itemId)
//                .map(i -> {
//                    return itemService.save(i);
//                }).flatMap(i -> i);
    }

    @GetMapping("/get-cart-of-user/{userId}")
    public Flux<Item> getCartOfUser(@PathVariable("userId") Long userId) {
        return itemService.getCartOfUser(userId);
    }

    @GetMapping("/get-total-sum-cart-of-user/{userId}")
    public Mono<Long> getTotalCartOfUser(@PathVariable("userId") Long userId) {
        return itemService.getTotalSumCartOfUser(userId);
    }

    @GetMapping("/remove-order/{orderId}")
    public Flux<Item> removeItemsFromOrderId(@PathVariable("orderId") Long orderId) {
        return itemService.removeItemsFromOrderId(orderId);
    }

    @GetMapping("/by-order/{orderId}")
    public Flux<Item> getItemsByOrderId(@PathVariable("orderId") Long orderId) {
        return itemService.getItemsByOrderId(orderId);
    }

    @GetMapping("/add-cart/{itemID}/{cartID}/{action}")
    public Mono<Item> addToCart(
            @PathVariable("itemID") Long itemId,
            @PathVariable("cartID") Long cartId,
            @PathVariable("action") String action) {
        Mono<Item> itemMono = itemService.findById(itemId)
                .map(i -> itemService.addToCart(i, cartId, action))
                .flatMap(i -> i);
        return itemMono;
    }

    @PutMapping("/get-items-by-cart")
    public Flux<Item> getItemsByCartDataFromMonoToFlux(
            @RequestBody Item item) {
        System.out.println("   GET ITEMS BY CART ");
        return itemService.getItemsByCartDataFromMonoToFlux(
                Mono.just(item));
    }

    @GetMapping("/get-by-cart/{cartId}")
    public Flux<Item> getItemsByCartIdToFlux(
            @PathVariable("cartId") Long cartId
    ) {
        return itemService.getItemsByCartIdToFlux(cartId);
    }

    @GetMapping("/get-total-sum/{cartId}")
    public Mono<Long> getTotalSum(
            @PathVariable("cartId") Long cartId) {
        Flux<Item> itemsFlux = this.getItemsByCartIdToFlux(cartId);
        System.out.println("   NNN --- TOTAL SUM ");
        return itemService.getTotalSum(itemsFlux);
    }


}
