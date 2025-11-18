package pn.market.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.entities.Cart;
import pn.market.entities.Item;
import pn.market.repo.CartRepo;
import pn.market.services.autocreate.CartsCreateService;
import pn.market.services.autocreate.ItemsCreateService;
import pn.market.services.impl.ItemServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
@Slf4j
public class CartRestController {
    @Autowired
    private  CartsCreateService cartsCreateService;

    @Autowired
    private CartRepo cartRepo;


    @GetMapping("/add-to-cart")
    public   Cart addToCart() {
        Cart cart = cartsCreateService.createRandomCart();
        return cartRepo.save(cart);
    }

}

