package pn.market.vitroFront.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.result.view.Rendering;
import pn.market.market_entities.Paging;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroFront.servicies.CartServiceImpl;
import pn.market.vitroFront.servicies.ItemServiceImpl;
import pn.market.vitroFront.servicies.ModelService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Controller
@RequestMapping("/")
public class ItemController {

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private WebClient webClient;

    @Autowired
    private String authHost;

    @GetMapping
    public Mono<Rendering> itemsIndex(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize,
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "sorted", required = false, defaultValue = "ALPHA") String sorted
    ) {
        try {
            Flux<Item> itemFlux = webClient.get()
                    .uri(authHost + "/api/vitro/items")
                    .retrieve()
                    .bodyToFlux(Item.class);
            Mono<Pageable> pageableMono = modelService.createPageble(page, pageSize, sorted);

            Mono<Paging> pageMono = itemService.findAllAndPagingWithFluxItem(pageableMono, itemFlux);

            String additionalParams = "&search=" + search + "&sorted=" + sorted + "&page="
                    + page + "&pageSize=" + pageSize + "&src=0";

            Mono<String> additional = Mono.just(additionalParams);

            Mono<Rendering> r = Mono.just(Rendering.view("items")
                    .modelAttribute("items", itemFlux)
                    .modelAttribute("additional", additional)
                    .modelAttribute("paging", pageMono)
                    .modelAttribute("page", pageableMono)
                    .build());
            return r;
        } catch (Exception e) {
            return Mono.just(Rendering.view("error")
                    .modelAttribute("errorInfo", e.getCause())
                    .build());
        }
    }

    @GetMapping("/items")
    public Mono<Rendering> items(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize,
            @RequestParam(value = "search", required = false, defaultValue = " ") String search,
            @RequestParam(value = "sorted", required = false, defaultValue = "ALPHA") String sorted,
            @RequestParam(value = "itemId", required = false, defaultValue = "0") long itemId,
            @RequestParam(value = "action", required = false, defaultValue = "NONE") String action
    ) {
        try {
            Flux<Item> itemFlux = webClient.get()
                    .uri(authHost + "/api/vitro/items")
                    .retrieve()
                    .bodyToFlux(Item.class);
            Mono<Pageable> pageableMono = modelService.createPageble(page, pageSize, sorted);

            Mono<Paging> pageMono = itemService
                    .findAllAndPagingWithFluxItem(pageableMono, itemFlux);

            String additionalParams = "&search=" + search + "&sorted=" + sorted + "&page="
                    + page + "&pageSize=" + pageSize + "&src=0";

            Mono<String> additional = Mono.just(additionalParams);

            Mono<Rendering> r = Mono.just(Rendering.view("items")
                    .modelAttribute("items", itemFlux)
                    .modelAttribute("additional", additional)
                    .modelAttribute("paging", pageMono)
                    .modelAttribute("page", pageableMono)
                    .build());
            return r;
        } catch (Exception e) {
            return Mono.just(Rendering.view("error")
                    .modelAttribute("errorInfo", e.getCause())
                    .build());
        }
    }

    @GetMapping("/items/{id}")
    public Mono<Rendering> itemById(
            @PathVariable("id") Long id,
            @RequestParam(value = "action", required = false) String action
    ) {
        Mono<Item> itemMono = cartService.placeItemToCart(id, action);

        Mono<Long> src = Mono.just(-2L);
        Mono<Rendering> r = Mono.just(Rendering.view("item")
                .modelAttribute("item", itemMono)
                .modelAttribute("action", action)
                .modelAttribute("src", src)
                .build());
        return r;
    }


}
