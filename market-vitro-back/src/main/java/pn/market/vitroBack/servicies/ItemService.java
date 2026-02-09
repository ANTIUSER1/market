package pn.market.vitroBack.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroBack.repo.ItemRepo;
import reactor.core.publisher.Flux;

@Service
public class ItemService {

    @Autowired
    private ItemRepo itemRepo;

    public Flux<Item> allItems() {
        return itemRepo.findAll();
    }
}
