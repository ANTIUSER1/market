package pn.market.vitroBack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroBack.servicies.____TMPItemService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/vitro/items")
public class ItemRest {
    @Autowired
    private ____TMPItemService TMPItemService;

    @GetMapping
    public Flux<Item> allItems() {
        return TMPItemService.allItems();
    }

}
