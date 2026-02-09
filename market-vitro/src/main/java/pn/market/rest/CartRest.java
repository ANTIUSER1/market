package pn.market.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.market_entities.forWEB.Cart;
import pn.market.services.autocreate.CartsCreateService;

@RestController
@RequestMapping("/api/carts")

public class CartRest {

    @Autowired
    private CartsCreateService cartsCreateService;

    @GetMapping("/add-to-cart")
    public Cart addToCart() {
        return cartsCreateService.createRandomCart();
    }

}

