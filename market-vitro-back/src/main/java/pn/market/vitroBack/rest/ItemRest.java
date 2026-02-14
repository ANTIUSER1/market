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

    @GetMapping("/i/{id}")
    public Mono<Item> itemById(@PathVariable("id") long id) {
        System.out.println("   ITEM BY ID " + id);
        return itemService.findById(id);
    }

    //   /api/vitro/items/add-cart/{cartID}/{action}
    @GetMapping("/add-cart/{itemID}/{cartID}/{action}")
    //   @PreAuthorize("hasAuthority('SERVICE')")
    public Mono<Item> addToCart(
            @PathVariable("itemID") Long itemId,
            @PathVariable("cartID") Long cartId,
            @PathVariable("action") String action) {
        System.out.println("            DDD itemId " + itemId);
        System.out.println("            DDD cartId " + cartId);
        System.out.println("            DDD action " + action);
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
