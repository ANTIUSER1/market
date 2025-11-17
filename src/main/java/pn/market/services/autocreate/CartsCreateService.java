package pn.market.services.autocreate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.entities.Cart;
import pn.market.entities.Item;
import pn.market.services.impl.CartServiceImpl;
import pn.market.services.impl.ItemServiceImpl;

import java.util.List;
import java.util.Optional;

@Service
public class CartsCreateService {

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private ItemServiceImpl itemService;

    public Cart createCart(Item item) {
        if (item != null) {
            Cart result = new Cart();
            //  item.setOrder(result);
            result.addItem(item);
            return result;
        }
        return null;
    }


    public Cart addRandomIremSetToCart(long orderId) {
        Optional<Cart> cartOptional = cartService.findById(orderId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            List<Item> itemList = itemService.getAllASsorted();
            for (Item item : itemList) {
                if (Math.random() < 0.4 && !cart.getItemList().contains(item)) {
                    cart.addItem(item);
                }
            }
            return cart;
        }
        return null;
    }
}
