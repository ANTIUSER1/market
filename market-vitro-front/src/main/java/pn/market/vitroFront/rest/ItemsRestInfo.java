package pn.market.vitroFront.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ItemsRestInfo {

    @Autowired
    WebClient webClient;


    @GetMapping("/items")
    public Flux<Item> allItems() {
        return webClient.get()
                .uri("http://localhost:8521/api/vitro/items")
                .retrieve()
                .bodyToFlux(Item.class);
    }

    @GetMapping("/plus-to-cart/{cartId}/{action}")
    public Mono<Item> plusToCart(
            @PathVariable("cartId") long cartId,
            @PathVariable("action") String action

    ) {
        return webClient.put()
                .uri("http://localhost:8521/api/vitro/items/add-cart/"
                        + cartId + "/" + action)
                //  .bodyValue(i)
                .retrieve().bodyToMono(Item.class);

    }
}
