package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.market_entities.data.UserData;
import pn.market.market_entities.forWEB.Item;
import pn.market.market_entities.forWEB.Order;
import pn.market.market_entities.forWEB.OrderItems;
import pn.market.vitroBack.repo.ItemRepo;
import pn.market.vitroBack.repo.OrderItemsRepo;
import pn.market.vitroBack.repo.OrderRepo;
import pn.market.vitroBack.repo.UserDataRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderControlUtilityService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemsRepo orderItemsRepo;

    @Autowired
    private UserDataRepo userDataRepo;

    public Mono<Item> findItemById(Long itemId) {
        return itemRepo.findById(itemId);
    }

    public Mono<Item> saveItem(Item i) {
        return itemRepo.save(i);
    }

    public Flux<OrderItems> findOrderItemsByOrderId(Long orderId) {
        return orderItemsRepo.findByOrderId(orderId);
    }

    public Mono<UserData> findUserById(Long userId) {
        return userDataRepo.findById(userId);
    }

    public Mono<UserData> saveUser(UserData u) {
        return userDataRepo.save(u);
    }

    public Mono<Order> findOrderById(Long orderId) {
        return orderRepo.findById(orderId);
    }

    public Mono<Order> saveOrder(Order o) {
        return orderRepo.save(o);
    }

    public Mono<OrderItems> saveOrderItems(OrderItems oi) {
        return orderItemsRepo.save(oi);
    }

    public Mono<Long> countOfOrderAndItemId(Long itemId, Long orderId) {
        return orderItemsRepo.countOfOrderAndItemId(itemId, orderId);
    }

}
