package pn.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import pn.market.entities.Item;
import pn.market.services.impl.CartServiceImpl;
import pn.market.services.impl.ItemServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/cart")

public class CartController {

    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private ItemServiceImpl itemService;


    @GetMapping("/items")
    public Mono<Rendering> addItem(
            @RequestParam("itemId") Long itemId,
            @RequestParam("action") String action

    ) {
        Mono<Item> itemMono = cartService.placeItemToCart(itemId, action);
        Flux<Item> itemsFlux = itemService.getItemsByCartDataFromMonoToFlux(itemMono);
        Mono<Long> total = itemService.getTotalSum(itemsFlux);
        Mono<Rendering> r =
                Mono.just(Rendering.view("cart")
                        .modelAttribute("items", itemsFlux)
                        .modelAttribute("total", total)
                        .build());
        return r;
    }

}
