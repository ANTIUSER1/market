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
    @PutMapping("/add-cart/{cartID}/{action}")
    //   @PreAuthorize("hasAuthority('SERVICE')")
    public Mono<Item> addToCart(
            @RequestBody Item i,
            @PathVariable("cartID") long cartId,
            @PathVariable("action") String action) {
        return itemService.addToCart(i, cartId, action);
    }

    @PutMapping("/get-items-by-cart")
    public Flux<Item> getItemsByCartDataFromMonoToFlux(
            @RequestBody Item item) {
        System.out.println("   GET ITEMS BY CART ");
        return itemService.getItemsByCartDataFromMonoToFlux(
                Mono.just(item));
    }

    @PutMapping("/get-total-sum")
    public Mono<Long> getTotalSum(
            @RequestBody Flux<Item> itemsFlux) {
        System.out.println("   NNN --- TOTAL SUM ");
        return itemService.getTotalSum(itemsFlux);
    }

//    public Mono<Long> createNewCart() {
//        return cartService.createNewCart();
//    }
}
