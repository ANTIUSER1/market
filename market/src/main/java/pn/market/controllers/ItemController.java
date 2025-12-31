package pn.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import pn.market.additional.Paging;
import pn.market.entities.Item;
import pn.market.services.ModelService;
import pn.market.services.impl.CartServiceImpl;
import pn.market.services.impl.ItemServiceImpl;
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
    private DatabaseClient databaseClient;

    @GetMapping
    public Mono<Rendering> itemsIndex(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize,
            @RequestParam(value = "search", required = false, defaultValue = " ") String search,
            @RequestParam(value = "sorted", required = false, defaultValue = "ALPHA") String sorted
    ) {
        Flux<Item> itemFlux = itemService.findAll();
        Mono<Pageable> pageableMono = modelService.createPageble(page, pageSize, sorted);
        Mono<Paging> pageMono = itemService.findAllAndPaging(pageableMono);
        Mono<Rendering> r = Mono.just(Rendering.view("items")
                .modelAttribute("items", itemFlux)
                .modelAttribute("paging", pageMono)
                .modelAttribute("page", pageableMono)
                .build());
        return r;
    }

    @GetMapping("/items")
    public Mono<Rendering> items(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize,
            @RequestParam(value = "search", required = false, defaultValue = " ") String search,
            @RequestParam(value = "sorted", required = false, defaultValue = "ALPHA") String sorted
    ) {
        Flux<Item> itemFlux = itemService.findAll();
        Mono<Pageable> pageableMono = modelService.createPageble(page, pageSize, sorted);
        Mono<Paging> pageMono = itemService.findAllAndPaging(pageableMono);
        Mono<Rendering> r = Mono.just(Rendering.view("items")
                .modelAttribute("items", itemFlux)
                .modelAttribute("paging", pageMono)
                .modelAttribute("page", pageableMono)
                .build());
        return r;
    }


    @GetMapping("/items/{id}")
    public Mono<Rendering> itemById(
            @PathVariable("id") Long id,
            @RequestParam(value = "action", required = false) String action
    ) {
        Mono<Item> itemMono = cartService.placeItemToCart(id, action);
        Mono<Rendering> r = Mono.just(Rendering.view("item")
                .modelAttribute("item", itemMono)
                .modelAttribute("action", action)
                .build());
        return r;
    }

    @GetMapping("/all/{id}")
    public Mono<Rendering> allItems(@PathVariable Long id) {
        Mono<Item> itemFlux = itemService.findById(id);

        Rendering r = Rendering.view("all")
                .modelAttribute("items", itemFlux)
                .build();
        return Mono.just(r);


    }

}
