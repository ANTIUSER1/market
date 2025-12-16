package pn.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pn.market.services.impl.ItemServiceImpl;
import pn.market.services.impl.OrderContainerServiceImpl;
import pn.market.services.impl.OrderServiceImpl;

@Controller
@RequestMapping("/orders")

public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private OrderContainerServiceImpl orderContainerService;



    /*

    @GetMapping
    public String asdOrders(Model model) {
        List<Order> orderList = orderService.findAllOrders();
        List<OrderContainer> orderContainers = orderContainerService.createOrderContainerList(orderList);

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
            OrderContainer orderContainer = orderContainerService.create(order.get());
            model.addAttribute("orderData", orderContainer);
            return "order";
        }
        return "items";
    }

    @GetMapping("/buy/{id}")
    public String buyOrder(@PathVariable("id") Long id) {
        orderService.buyOrder(id);
        Optional<Order> order = orderService.getById(id);
        System.out.println("      RESULT ::: ORDER PRESENT ::: " + order.isPresent());
        return "redirect:/orders";
    }

     */
}
