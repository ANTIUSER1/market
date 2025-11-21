package pn.market.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pn.market.entities.Order;
import pn.market.services.impl.OrderServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;


    @RequestMapping
    public String asdOrders(  Model model) {
        List<Order> orderList = orderService.findAllOrders();
       System.out.println("  ORDER LIST SIZE  "+orderList.size());
       for (Order order:orderList) {
           System.out.println(order);
       }
       System.out.println("  ");
model.addAttribute("orders",orderList);
        return "orders";
    }
}
