package pn.market.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pn.market.entities.Order;
import pn.market.entities.OrderContainer;
import pn.market.services.impl.ItemServiceImpl;
import pn.market.services.impl.OrderServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ItemServiceImpl itemService;

    @GetMapping
    public String asdOrders(Model model) {
        List<Order> orderList = orderService.findAllOrders();
        List<OrderContainer> orderContainers = new ArrayList<>();
        for (Order order : orderList) {
            OrderContainer orderContainer =
                    new OrderContainer();
            orderContainer.setOrder(order);
            orderContainer.setItems(itemService.getItemsByOrderId(order.getId()));
            orderContainers.add(orderContainer);
        }
        model.addAttribute("orderData", orderContainers);
        return "orders";
    }

    @GetMapping("/{id}")
    public String getOrderById(
            Model model,
            @PathVariable("id") Long id,
            @RequestParam(value = "newOrder") boolean newOrder
    ) {
        Optional<Order> order = orderService.getById(id);
        if (order.isPresent()) {
         //   model.addAttribute("order", order.get());
            return "order";
        }
        return "items";
    }

    @PostMapping("/buy/{id}")
    public String buyOrder(@PathVariable("id") Long id) {
        orderService.buyOrder(id);
        return "redirect:/orders/{id}?newOrder=true";
    }
}
