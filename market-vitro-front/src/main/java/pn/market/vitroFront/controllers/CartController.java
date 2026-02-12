package pn.market.vitroFront.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")

public class CartController {

    /*
    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private ItemServiceImpl itemService;


    @GetMapping("/items")
    public String addItem(
//    public Mono<Rendering> addItem(
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
