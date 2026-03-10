package pn.market.vitroFront.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Value("${oauth.data.host}")
    private String auth2Host;

    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
   // @Qualifier("BACK")
    private WebClient webClient;

    @Autowired
    private LoginService loginService;

    @GetMapping
    public Mono<String> cartIndex() {
        return Mono.just("/cart");
    }


    //************* add roles ***
    @PreAuthorize("#username ==  authentication.principal.username ")
    @GetMapping("/{username}/{cartId}")
    public Mono<Rendering> itemsList(       @PathVariable("username") String username,
                                            @PathVariable("cartId") long cartId) {

  Long userId = loginService.getUserData().getId();
        Flux<Item> itemsFlux = itemService.getItemsByCartDataFromMonoToFlux(cartId);
        Mono<Long> total = itemService.getTotalSum(cartId);
       total.subscribe();
        Mono<Rendering> r =
                Mono.just(Rendering.view("cart")
                        .modelAttribute("items", itemsFlux)
                        .modelAttribute("total", total)
                        .build());
        return r;
    }

    //************* add roles ***
    @PreAuthorize("#username ==  authentication.principal.username ")
    @GetMapping("/item-of-user/{username}")
    public Mono<Rendering> itemsOfUser(       @PathVariable("username") String username
                                              ) {
        Long userId = loginService.getUserData().getId();
         Flux<Item> itemsFlux = itemService.itemOfUser(userId);
        Mono<Long> total = itemService.getTotalOfSum(userId);
        total.subscribe( );
        Mono<Rendering> r =
                Mono.just(Rendering.view("cart")
                        .modelAttribute("items", itemsFlux)
                        .modelAttribute("total", total)
                        .build());
        return r;
    }


    //**********  ADD ROLES *************
    @PreAuthorize("#username ==  authentication.principal.username ")
    @GetMapping("/add-item-to-cart-of-user/{username}/{itemId}")
    public Mono<String> additemsList(
            @PathVariable("username") String username,
            @PathVariable("itemId") long itemId
    ) {
        Long userId = loginService.getUserData().getId();
         Mono<Cart> cartMono = webClient.get()
                .uri(auth2Host+VITRO_CART_API + "/create/" + userId + "/" + itemId)
                .retrieve().bodyToMono(Cart.class);
        cartMono.subscribe( );
        return cartMono.map(cm -> {
            return "redirect:/cart/" + cm.getId();
        });
    }

}
