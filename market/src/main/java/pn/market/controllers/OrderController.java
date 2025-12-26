package pn.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import pn.market.entities.Order;
import pn.market.services.impl.ItemServiceImpl;
import pn.market.services.impl.OrderServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/orders")

public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ItemServiceImpl itemService;


    @GetMapping
    public Mono<Rendering> allOrders() {
        Flux<Order> orders = orderService.findAll()
                .map(od -> {
                    itemService.getItemsByOrderId(od.getId())
                            .subscribe(u -> od.addItem(u));
                    return od;
                });
        Mono<Rendering> r =
                Mono.just(Rendering.view("orders")
                        .modelAttribute("orderData", orders)
                        .build());
        return r;
    }

    @GetMapping("/{id}")
    public Mono<Rendering> getOrderById(
            @PathVariable("id") Long id,
            @RequestParam(value = "newOrder", defaultValue = "true") boolean newOrder
    ) {
        Mono<Order> order = orderService.getById(id)
                .map(od -> {
                    itemService.getItemsByOrderId(od.getId())
                            .subscribe(u -> od.addItem(u));
                    return od;
                });

        Mono<Rendering> r =
                Mono.just(Rendering.view("order")
                        .modelAttribute("orderData", order)
                        .modelAttribute("newOrder", newOrder)
                        .build());

        return r;
    }

    @GetMapping("/buy/{id}")
    public String buyOrder(@PathVariable("id") Long id) {
        orderService.buyOrder(id);
        return "redirect:/orders";
    }
}
