package pn.market.vitroBack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroBack.servicies.ItemService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/vitro")
public class ItemRest {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public Flux<Item> allItems() {
        return itemService.allItems();
    }
}
