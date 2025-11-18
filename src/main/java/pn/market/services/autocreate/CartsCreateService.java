package pn.market.services.autocreate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.entities.Cart;
import pn.market.entities.Item;

import java.util.List;

@Service
public class CartsCreateService {

    @Autowired
    private ItemsCreateService itemsCreateService;

    public Cart createRandomCart() {
        Cart cart = new Cart();
        List<Item> items = itemsCreateService.autoCreate();
        for (Item item : items) {
            if (Math.random() > 0.7) {
                cart.getCartItems().add(item);
            }
        }
        return cart;
    }
}
