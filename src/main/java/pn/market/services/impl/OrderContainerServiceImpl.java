package pn.market.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.entities.Order;
import pn.market.entities.OrderContainer;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderContainerServiceImpl {

    @Autowired
    private ItemServiceImpl itemService;

    public OrderContainer create(Order order){
        OrderContainer orderContainer =
                new OrderContainer();
        orderContainer.setOrder(order);
        orderContainer.setItems(itemService.getItemsByOrderId(order.getId()));

        return orderContainer;
    }

    public List<OrderContainer> createOrderContainerList(List<Order> orderList){
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
