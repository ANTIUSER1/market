package pn.market.vitroBack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.market_entities.forWEB.Cart;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroBack.servicies.impl.CartServiceImpl;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/vitro/carts")
public class CartRest {

    @Autowired
    private CartServiceImpl cartService;


    @GetMapping("/create/{userId}/{itemId}")
    public Mono<Cart> createCartOfUser(
            @PathVariable("itemId") Long itemId,
            @PathVariable("userId") Long userId
    ) {
        return cartService.createOrUseCartOfUser(itemId, userId);
    }

    @GetMapping("/total-sum-of-cart/{cartId}")
    public Mono<Long> getTotalSumOfCart(
            @PathVariable("cartId") Long cartId
    ) {
        return cartService.getTotalSumOfCart(cartId);
    }

    @GetMapping("/remove/{userId}/{itemId}")
    public Mono<Item> removeCartOfUser(
            @PathVariable("userId") Long userId,
            @PathVariable("itemId") Long itemId

    ) {
        return cartService.removeFromCartOfUser(userId, itemId);
    }

}
