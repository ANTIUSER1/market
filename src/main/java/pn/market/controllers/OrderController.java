package pn.market.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pn.market.entities.Order;
import pn.market.services.impl.OrderServiceImpl;

import java.util.List;
import java.util.Optional;

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


    @RequestMapping("/")
    public String getOrderById(
            Model model,
            @RequestParam("id") Long id
    ) {
        Optional<Order> order = orderService.findById(id);
        if ( order.isPresent()){
            model.addAttribute("order", order.get());
            return "order";
        }
        return "items";
    }

}
