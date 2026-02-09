package pn.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import pn.market.entities.Order;
import pn.market.services.impl.ItemServiceImpl;
import pn.market.services.impl.OrderServiceImpl;
import pn.market.services.impl.PaymentSupplierImpl;
import pn.market.services.impl.PaymentsServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/orders")


public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private PaymentsServiceImpl paymentService;
    @Autowired
    private PaymentSupplierImpl paymentSupplier;

    @GetMapping
    public Mono<Rendering> allOrders() {
        Flux<Order> orders = orderService.findAll()
                .map(od -> {
                    itemService.getItemsByOrderId(od.getId())
                            .subscribe(u -> {
                                od.addItem(u);
                            });
                    return od;
                });
        Mono<Rendering> r =
                Mono.just(Rendering.view("orders")
                        .modelAttribute("orderData", orders)
                        .build());
        return r;
    }

    @GetMapping("/{orderId}")
    public Mono<Rendering> getOrderById(
            @PathVariable("orderId") Long orderId,
            @RequestParam(value = "newOrder", defaultValue = "true") boolean newOrder
    ) {
        paymentSupplier = paymentSupplier.setUserId(1L);
        paymentSupplier = paymentSupplier.setOrderId(orderId);
        paymentService.sendPaymentInfo(
                PaymentsServiceImpl.PAYMENT_KEY_NAME,
                () -> paymentSupplier.get()
        );

        Mono<Order> order = orderService.getById(orderId)
                .map(od -> {
                    itemService.getItemsByOrderId(od.getId())
                            .subscribe(u -> {
                                od.addItem(u);
                                u.setCartId(null);
                            });
                    return od;
                });
        Mono<Rendering> r =
                Mono.just(Rendering.view("order")
                        .modelAttribute("orderData", order)
                        .modelAttribute("newOrder", newOrder)
                        .build());

        return r;
    }

    @PostMapping("/buy/{orderId}")
    public String buyOrder(@PathVariable("orderId") Long orderId) {
        orderService.buyOrder(orderId);
        return "redirect:/orders";
    }
}
