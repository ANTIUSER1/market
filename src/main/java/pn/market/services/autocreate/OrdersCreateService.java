package pn.market.services.autocreate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.entities.Cart;
import pn.market.entities.Item;
import pn.market.entities.Order;
import pn.market.services.impl.CartServiceImpl;
import pn.market.services.impl.ItemServiceImpl;
import pn.market.services.impl.OrderServiceImpl;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersCreateService {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private ItemServiceImpl itemService;

    public Order createOrder(Item item) {
        if (item != null) {
            Order result = new Order();
            //  item.setOrder(result);
            result.addItem(item);
            return result;
        }
        return null;
    }


    public Order addRandomIremSetToOrder(long orderId) {
        Optional<Order> orderOptional = orderService.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<Item> itemList = itemService.getAllASsorted();
            for (Item item : itemList) {
                if (Math.random() < 0.4 && !order.getItemList().contains(item)) {
                    order.addItem(item);
                }
            }
            return order;
        }
        return null;
    }

    public Order addRandomIremSetToOrderFromCart(long cartId) {
//        Optional<Cart> cartOptional = cartService.findById(cartId);
//        if (cartOptional.isPresent()) {
//            Order result = new Order();
//            result.addItemList(cartOptional.get().getItemList());
//            return result;
//        }
        return null;
    }
}
