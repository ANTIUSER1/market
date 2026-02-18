package pn.market.vitroBack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pn.market.market_entities.forWEB.Cart;
import pn.market.vitroBack.servicies.impl.CartServiceImpl;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/vitro/carts")
public class CartRest {

    @Autowired
    private CartServiceImpl cartService;

    @GetMapping("/create/{itemId}")
    public Mono<Cart> createCart(
            @RequestParam(value = "user", required = true) Long user,
            @PathVariable("itemId") Long itemId

    ) {
        return cartService.createNewCart(itemId);
    }


}
