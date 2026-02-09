package pn.market.services.autocreate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.market_entities.forWEB.Item;
import pn.market.repo.ItemRepo;

import java.util.List;

@Service
public class ItemsCreateService {

    @Autowired
    private ItemRepo itemRepo;

    public List<Item> autoCreate() {
        return List.of();
        //itemRepo.getAllItemsSortedAscById();

    }
}
