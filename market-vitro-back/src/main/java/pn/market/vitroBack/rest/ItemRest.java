package pn.market.vitroBack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('SERVICE')")
    Flux<Item> allItems() {
        return itemService.findAll();
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('SERVICE')")
    Mono<Item> itemById(@PathVariable("id") long id) {
        return itemService.findById(id);
    }

    @GetMapping("/add-cart/{cartID}/{action}")
    public Mono<Item> addToCart(
            @RequestBody Item i,
            @PathVariable("cartID") long cartId,
            @PathVariable("action") String action) {
        return itemService.addToCart(i, cartId, action);
    }

    public Mono<Long> createNewCart() {
        return cartService.createNewCart();
    }
}
