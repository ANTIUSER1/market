package pn.market.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.entities.Cart;
import pn.market.services.autocreate.CartsCreateService;

@RestController
@RequestMapping("/api/carts")
@Slf4j
public class CartRest {
    @Autowired
    private  CartsCreateService cartsCreateService;

    @GetMapping("/add-to-cart")
    public   Cart addToCart() {
        return cartsCreateService.createRandomCart();
    }

}

