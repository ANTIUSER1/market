package pn.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import pn.market.entities.Cart;
import pn.market.entities.Item;
import pn.market.services.impl.CartServiceImpl;
import pn.market.services.impl.ItemServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("/cart")

public class CartController {

    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private ItemServiceImpl itemService;




    @GetMapping("/items")
    public String addItem(
//    public Mono<Rendering> addItem(
            @RequestParam(value = "itemId", required = true) Long itemId,
            @RequestParam(value = "action", required = true) String action,
@RequestParam(value = "src", required = true) long src

    ) {
        Mono<Item> itemMono = cartService.placeItemToCart(itemId, action);
        Flux<Item> itemsFlux = itemService.getItemsByCartDataFromMonoToFlux(itemMono);
        Mono<Long> total = itemService.getTotalSum(itemsFlux);
        Mono<Long> cartId = itemMono.map(Item::getCartId) ;
        itemsFlux.subscribe();
        total.subscribe();
        cartId.subscribe();
        return  "redirect:/cart/"+src;
    }

    @GetMapping("/{cartId}")
    public Mono<Rendering> itemsList(
            @PathVariable("cartId") long cartId  ) {
        itemService.getItemsByCartDataFromMonoToFlux(cartId);
  Flux<Item> itemsFlux = itemService.getItemsByCartDataFromMonoToFlux(cartId);
        Mono<Long> total = itemService.getTotalSum(itemsFlux);
        Mono<Rendering> r =
                Mono.just(Rendering.view("cart")
                        .modelAttribute("items", itemsFlux)
                        .modelAttribute("total", total)
                        .build());
        return r;
    }




}
