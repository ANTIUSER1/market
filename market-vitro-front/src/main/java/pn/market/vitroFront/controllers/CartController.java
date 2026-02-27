package pn.market.vitroFront.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.result.view.Rendering;
import pn.market.market_entities.forWEB.Cart;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroFront.servicies.CartServiceImpl;
import pn.market.vitroFront.servicies.ItemServiceImpl;
import pn.market.vitroFront.servicies.LoginService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static pn.market.vitroFront.config.AuthPaths.VITRO_CART_API;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    @Qualifier("BACK")
    private WebClient webClient;

    @Autowired
    private LoginService loginService;

    @GetMapping
    public Mono<String> cartIndex() {
        return Mono.just("/cart");
    }

    //************* add roles ***
    @GetMapping("/{cartId}")
    public Mono<Rendering> itemsList(@PathVariable("cartId") long cartId) {
        Long userId = loginService.getUserData().getId();
        Flux<Item> itemsFlux = itemService.getItemsByCartDataFromMonoToFlux(cartId);
        Mono<Long> total = itemService.getTotalSum(cartId);
        //  itemsFlux.subscribe(ii -> System.out.println("       III ID " + ii.getId()));
        total.subscribe(t -> System.out.println("    TOTAL " + t));
        Mono<Rendering> r =
                Mono.just(Rendering.view("cart")
                        .modelAttribute("items", itemsFlux)
                        .modelAttribute("total", total)
                        .build());
        return r;
    }

    //************* add roles ***
    @GetMapping("/item-of-user")
    public Mono<Rendering> itemsOfUser() {
        Long userId = loginService.getUserData().getId();
        System.out.println("   USER_DATA ID " + userId);
        Flux<Item> itemsFlux = itemService.itemOfUser(userId);
        Mono<Long> total = itemService.getTotalOfSum(userId);
        total.subscribe(t -> System.out.println("    USER-TOTAL-SUM " + t));
        Mono<Rendering> r =
                Mono.just(Rendering.view("cart")
                        .modelAttribute("items", itemsFlux)
                        .modelAttribute("total", total)
                        .build());
        return r;
    }


    //**********  ADD ROLES *************
    @GetMapping("/add-item-to-cart-of-user/{itemId}")
    public Mono<String> additemsList(
            @PathVariable("itemId") long itemId
    ) {
        Long userId = loginService.getUserData().getId();
        System.out.println("   add-item-to-cart-of-user  " + userId);
        Mono<Cart> cartMono = webClient.get()
                .uri(VITRO_CART_API + "/create/" + userId + "/" + itemId)
                .retrieve().bodyToMono(Cart.class);
        cartMono.subscribe(cm -> System.out.println("redirect:/cart/" + cm.getId()));
        return cartMono.map(cm -> {
            return "redirect:/cart/" + cm.getId();
        });
    }

}
