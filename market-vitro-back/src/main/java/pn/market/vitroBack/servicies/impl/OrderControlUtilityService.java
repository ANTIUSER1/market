package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.market_entities.data.UserData;
import pn.market.market_entities.forWEB.*;
import pn.market.vitroBack.repo.*;
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
        return         itemRepo.save(i);
    }


    public Flux<OrderItems> findOrderItemsByOrderId(Long orderId) {
        return orderItemsRepo.findByOrderId(orderId);
    }

    public Flux<OrderItems> findOrderItemsByItemId(Long itemId) {
        return orderItemsRepo.findByItemId(itemId);
    }

    public Flux<Order> findAllOrders() {
        return orderRepo.findAll();
    }

    public Mono<UserData> findUserById(Long userId) {
        return userDataRepo.findById(userId);
    }

    public Mono<UserData> saveUser(UserData u) {
        return userDataRepo.save(u);
    }

    public Mono<Order> findOrderById(Long orderId){
        return orderRepo.findById(orderId);

   }
    public Mono<Order> saveCOrder(Order o){
        return orderRepo.save(o);
    }

    public Mono<OrderItems> saveOrderItems(OrderItems oi) {
        return orderItemsRepo.save(oi);
    }

    public Mono<Long> countOfOrderAndItemIdId(Long itemId, Long orderId) {
        return orderItemsRepo.countOfOrderAndItemId(itemId, orderId);
    }


    public Mono<Void> deleteOrderItemsById(Long ciID) {
      return   orderItemsRepo.deleteById(ciID);
    }
}
