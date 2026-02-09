package pn.market.services.autocreate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.market_entities.forWEB.Cart;
import pn.market.market_entities.forWEB.Item;
import pn.market.repo.CartRepo;

import java.util.List;

@Service
public class CartsCreateService {

    private static final double RANDOM_VALUE = 0.7;

    @Autowired
    private ItemsCreateService itemsCreateService;

    @Autowired
    private CartRepo cartRepo;

    public Cart createRandomCart() {
        Cart cart = new Cart();
        List<Item> items = itemsCreateService.autoCreate();
        for (Item item : items) {
            if (Math.random() > RANDOM_VALUE) {
                //    cart.getCartItems().add(item);
            }
        }
        cart = new Cart();
        //cartRepo.save(cart);
        return cart;
    }
}
