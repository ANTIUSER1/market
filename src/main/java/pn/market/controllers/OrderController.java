package pn.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import pn.market.entities.Order;
import pn.market.services.impl.ItemServiceImpl;
import pn.market.services.impl.OrderServiceImpl;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("/orders")

public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ItemServiceImpl itemService;


    @GetMapping
    public Mono<Rendering> allOrders() {
        Mono<List<Order>> orders = orderService.findAll()
                .map(od -> {
                    itemService.getItemsByOrderId(od.getId())
                            .subscribe(u -> od.addItem(u));
                    return od;
                })
                .collectList();
        Mono<Rendering> r =
                Mono.just(Rendering.view("orders")
                        .modelAttribute("orderData", orders)
                        .build());
        return r;
    }

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
