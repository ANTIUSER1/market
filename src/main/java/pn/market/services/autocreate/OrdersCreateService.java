package pn.market.services.autocreate;

import org.springframework.stereotype.Service;
import pn.market.entities.Item;
import pn.market.entities.Order;

@Service
public class OrdersCreateService {
    public Order createOrder(Item item) {
        if (item != null) {
            Order result = new Order();
            result.addIte(item);
            return result;
        }
        return null;
    }


}
