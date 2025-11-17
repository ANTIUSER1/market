package pn.market.services.autocreate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.entities.Item;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemsCreateService {

    @Autowired
    private OrdersCreateService ordersCreateService;

    public List<Item> autoCreate(int size) {

        List<Item> itemList = new ArrayList<>(size);
        for (int k = 0; k < size; k++) {
            int m = (int) (10 + 10 * Math.random());
            int m1 = (int) (10 + 20 * Math.random());
            int m2 = (int) (10 + 30 * Math.random());
            long p = (long) (10 + 3000 * Math.random());

            Item item = new Item();
            item.setImgPath("/img/" + m);
            item.setDescription("D-" + m1);
            item.setTitle("T-" + m1);
            item.setPrice(p);
            itemList.add(item);
        }
        return itemList;
    }
}
