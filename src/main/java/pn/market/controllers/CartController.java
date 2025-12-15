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

    @GetMapping("/is-add")
    public Mono<Rendering> addItem1(
            @RequestParam("itemId") Long itemId,
            @RequestParam("action") String action

    ) {
        Mono<Item> itemMono = cartService.placeItemToCart(itemId, action);

        Flux<Item> itemsFlux = itemMono.map(i -> {
            return itemService.getItemsByCartIdToFlux(i.getCartId());
        }).flatMapMany(f -> f);
        Mono<Long> total = itemService.getTotalSum(itemsFlux);
        Mono<Rendering> r =
                Mono.just(Rendering.view("_cart-test")
                        .modelAttribute("items", itemsFlux)
                        .modelAttribute("total", total)
                        .build());
        return r;
    }


    /*
    @GetMapping("/items")
    public String addItem(
            @RequestParam("itemId") Long itemId,
            @RequestParam("action") String action,
            Model model) {
        Cart cart = null;

        if (action != null && itemId != null) {
            if (ActionType.PLUS.name().equalsIgnoreCase(action.trim())) cart = cartService.plusItem(itemId);
            if (ActionType.MINUS.name().equals(action.trim())) cart = cartService.minusItem(itemId);

            if (cart != null) {
                List<Item> items = itemService.getItemsByCartId(cart.getId());
                long total = itemService.getTotalSum(items);
                model.addAttribute("total", total);
                model.addAttribute("items", items);
                return "cart";
            }
        }    return "items";
    }

     */
}
