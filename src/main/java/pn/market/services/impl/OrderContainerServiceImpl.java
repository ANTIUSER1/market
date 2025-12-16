package pn.market.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.entities.Order;
import pn.market.entities.OrderContainer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderContainerServiceImpl {

    @Autowired
    private ItemServiceImpl itemService;


    public Mono<OrderContainer> create(Order order) {
        OrderContainer orderContainer =
                new OrderContainer();
        orderContainer.setOrder(order);
        orderContainer.setItems(itemService.getItemsByOrderId(order.getId()));
        System.out.println("        OC  ITEMS  \n  " + orderContainer.getOrder().getId());
        System.out.println("        OC  ITEMS  \n  " + orderContainer.getItems());
        return Mono.just(orderContainer);
    }

    public Flux<OrderContainer> createOrderContainerFlux(Flux<Order> orderFlux) {
        List<OrderContainer> orderContainers = new ArrayList<>();

        return null;
    }

    public List<OrderContainer> createOrderContainerList(List<Order> orderList) {
        List<OrderContainer> orderContainers = new ArrayList<>();
        for (Order order : orderList) {
            OrderContainer orderContainer =
                    new OrderContainer();
            orderContainer.setOrder(order);
            orderContainer.setItems(itemService.getItemsByOrderId(order.getId()));
            orderContainers.add(orderContainer);
        }
        return orderContainers;
    }
}
