package pn.market.vitroFront.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private WebClient webClient;

    @Autowired
    private LoginService loginService;


    @GetMapping("/{cartId}")
    public Mono<Rendering> itemsList(@PathVariable("cartId") long cartId) {
        Long userId = loginService.getUserData().getId();
        System.out.println("  CCC  CART " + cartId + "   USER_DATA ID " + userId);


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
        Mono<Cart> cartMono = webClient.get()
                .uri(VITRO_CART_API + "/create/" + userId + "/" + itemId)
                .retrieve().bodyToMono(Cart.class);
//        return Mono.just("redirect:/cart/" + cid);
        return cartMono.map(cm -> "redirect:/cart/" + cm.getId());
    }

    @GetMapping("/items")
    public Mono<String> addItem(
            @RequestParam(value = "itemId", required = true) Long itemId,
            @RequestParam(value = "action", required = true) String action,
            @RequestParam(value = "src", required = true) long src,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize,
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "sorted", required = false, defaultValue = "ALPHA") String sorted

    ) {

        String res = "";
        search = search.trim();
        String additionalParams = "search=" + search +
                "&sorted=" + sorted + "&page=" + page + "&pageSize=" + pageSize + "&src=" + src;


        System.out.println("-------RETRIEVE ITEM BY ID--------------- " + itemId);
        itemService.updateCartInfo(itemId, action);


        if (src > 0) res = "redirect:/cart/" + src;
        else if (src == 0) res = "redirect:/?" + additionalParams;
        else if (src == -1) res = "redirect:/items?" + additionalParams;
        else res = "redirect:/items/" + itemId;
        return Mono.just(res);
    }


//    @GetMapping("/items")
//    public Mono<String> addItem(
////    public Mono<Rendering> addItem(
//            @RequestParam(value = "itemId", required = true) Long itemId,
//            @RequestParam(value = "action", required = true) String action,
//            @RequestParam(value = "src", required = true) long src,
//            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
//            @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize,
//            @RequestParam(value = "search", required = false, defaultValue = "") String search,
//            @RequestParam(value = "sorted", required = false, defaultValue = "ALPHA") String sorted
//
//    ) {
//        search = search.trim();
//        String additionalParams = "search=" + search +
//                "&sorted=" + sorted + "&page=" + page + "&pageSize=" + pageSize + "&src=" + src;
//        Mono<Item> itemMono = cartService.placeItemToCart(itemId, action);
//
//        /*
//        Flux<Item> itemsFlux = itemService.getItemsByCartDataFromMonoToFlux(itemMono);
//        Mono<Long> total = itemService.getTotalSum(itemsFlux);
//        Mono<Long> cartId = itemMono.map(Item::getCartId);
//        itemsFlux.subscribe();
//        total.subscribe();
//        cartId.subscribe();
//        if (src > 0) return "redirect:/cart/" + src;
//        else if (src == 0) return "redirect:/?" + additionalParams;
//        else if (src == -1) return "redirect:/items?" + additionalParams;
//        else return "redirect:/items/" + itemId;
//        */
//        return Mono.just("tmp");
//    }

/*
    @GetMapping("/items")
    public String addItem(
            @RequestParam(value = "itemId", required = true) Long itemId,
            @RequestParam(value = "action", required = true) String action,
            @RequestParam(value = "src", required = true) long src,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize,
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "sorted", required = false, defaultValue = "ALPHA") String sorted

    ) {
        System.out.println("     CART/ITEMS request");
        search = search.trim();
        String additionalParams = "search=" + search +
                "&sorted=" + sorted + "&page=" + page + "&pageSize=" + pageSize + "&src=" + src;
        Mono<Item> itemMono = cartService.placeItemToCart(itemId, action);
        System.out.println("++++++IM  +++  ");


//        Flux<Item> itemsFlux =
//                itemMono.map(im -> {
//                            System.out.println(" ************IM  \n " + im);
//                            itemService.getItemsByCartDataFromMonoToFlux(im).subscribe();
//
//                        }
//                );

        //Flux<Item> itemsFlux = itemService.getItemsByCartDataFromMonoToFlux(itemMono);

        Mono<Long> total = itemService.getTotalSum(itemsFlux);
        Mono<Long> cartId = itemMono.map(Item::getCartId);
        itemsFlux.subscribe();
        total.subscribe();
        cartId.subscribe();



        if (src > 0) return "redirect:/cart/" + src;
        else if (src == 0) return "redirect:/?" + additionalParams;
        else if (src == -1) return "redirect:/items?" + additionalParams;
        else return "redirect:/items/" + itemId;
    }


    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private ItemServiceImpl itemService;


    @GetMapping("/items")
    public String addItem(
            @RequestParam(value = "itemId", required = true) Long itemId,
            @RequestParam(value = "action", required = true) String action,
            @RequestParam(value = "src", required = true) long src,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize,
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "sorted", required = false, defaultValue = "ALPHA") String sorted

    ) {
        search = search.trim();
        String additionalParams = "search=" + search +
                "&sorted=" + sorted + "&page=" + page + "&pageSize=" + pageSize + "&src=" + src;
        Mono<Item> itemMono = cartService.placeItemToCart(itemId, action);
        Flux<Item> itemsFlux = itemService.getItemsByCartDataFromMonoToFlux(itemMono);
        Mono<Long> total = itemService.getTotalSum(itemsFlux);
        Mono<Long> cartId = itemMono.map(Item::getCartId);
        itemsFlux.subscribe();
        total.subscribe();
        cartId.subscribe();
        if (src > 0) return "redirect:/cart/" + src;
        else if (src == 0) return "redirect:/?" + additionalParams;
        else if (src == -1) return "redirect:/items?" + additionalParams;
        else return "redirect:/items/" + itemId;
    }


    @GetMapping("/{cartId}")
    public Mono<Rendering> itemsList(
            @PathVariable("cartId") long cartId) {
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

     */
}
