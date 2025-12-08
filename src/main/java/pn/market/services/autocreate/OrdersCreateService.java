package pn.market.services.autocreate;

//import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.entities.Cart;
import pn.market.entities.Item;
import pn.market.entities.Order;
import pn.market.repo.CartRepo;
import pn.market.repo.OrderRepo;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrdersCreateService {

    @Autowired
    CartRepo cartRepo;

    @Autowired
    OrderRepo orderRepo;

    public Order createOrder() {
        long maxCartId = -1000;// cartRepo.findMaxId();
        if (maxCartId < 1) {
            return null;
        }
        Order order = new Order();
        Optional<Cart> cartOptional = Optional.empty();// cartRepo.findById(maxCartId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
//            for (int k = 0; k < cart.getCartItems().size(); k++) {
//                Item item = cart.getCartItems().remove(k);
//                order.addItem(item);
//            }
//            order = orderRepo.save(order);
            cartRepo.save(cart);
            return order;
        }
        return null;
    }

    public void buyLastOrder() {
        long maxOrderId =-1000;// orderRepo.findMaxId();
        Optional<Order> orderOptional =Optional.empty();// orderRepo.findById(maxOrderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setItems(new ArrayList<>());
            orderRepo.save(order);
            orderRepo.delete(order);
        }
    }
}
