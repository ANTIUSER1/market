package pn.market.vitroFront.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Flux;

import static pn.market.vitroFront.config.AuthPaths.VITRO_ITEM_API;

@RestController
@RequestMapping("/api")
public class ItemsRestInfo {

    @Autowired
    @Qualifier("BACK")
    private WebClient webClient;

    @GetMapping("/items")
    public Flux<Item> allItems() {
        return webClient.get()
                .uri(VITRO_ITEM_API)
                .retrieve()
                .bodyToFlux(Item.class);
    }


}
