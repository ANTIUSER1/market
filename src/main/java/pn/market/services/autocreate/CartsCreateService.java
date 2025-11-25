package pn.market.services.autocreate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.entities.Cart;
import pn.market.entities.Item;
import pn.market.repo.CartRepo;

import java.util.List;

@Service
public class CartsCreateService {

    @Autowired
    private ItemsCreateService itemsCreateService;

    @Autowired
    private CartRepo cartRepo;

    public Cart createRandomCart() {
        Cart cart = new Cart();
        List<Item> items = itemsCreateService.autoCreate();
        for (Item item : items) {
            if (Math.random() > 0.7) {
                cart.getCartItems().add(item);
            }
            System.out.println(item);
        }
        cart = cartRepo.save(cart);
        return cart;
    }
}
