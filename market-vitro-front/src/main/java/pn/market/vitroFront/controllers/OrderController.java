package pn.market.vitroFront.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import pn.market.market_entities.forWEB.Order;
import pn.market.vitroFront.servicies.ItemServiceImpl;
import pn.market.vitroFront.servicies.OrderServiceImpl;
import pn.market.vitroFront.servicies.PaymentSupplierImpl;
import pn.market.vitroFront.servicies.PaymentsServiceImpl;
import reactor.core.publisher.Mono;

import java.util.List;

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
        Mono<List<Order>> orders = orderService.addItemsToAll();
        //orders.subscribe(oo -> System.out.println("    OO  OO  OO  " + oo));
        Mono<Rendering> r =
                Mono.just(Rendering.view("orders")
                        .modelAttribute("orderData", orders)
                        .build());
        return r;
    }

    @GetMapping("/{orderId}/{itemId}")
    public Mono<Rendering> getOrderById(
            @PathVariable("orderId") Long orderId,
            @PathVariable("itemId") Long itemId) {
        Mono<Order> order = orderService.showCompleteOrderById(orderId, itemId);

        Mono<Rendering> r =
                Mono.just(Rendering.view("order")
                        .modelAttribute("orderData", order)
                        .modelAttribute("newOrder", false)
                        .build());
        return r;

    }


    @GetMapping("/{orderId}")
    public Mono<Rendering> getOrderById(
            @PathVariable("orderId") Long orderId
    ) {
//        long userId = 1L;
//        paymentService.configurePayments(userId, orderId);

        Mono<Order> order = orderService.showCompleteOrderById(orderId);
        Mono<Rendering> r =
                Mono.just(Rendering.view("order")

                        .modelAttribute("orderData", order)
                        .modelAttribute("newOrder", false)
                        .build());
        return r;
    }

    @GetMapping("/buy/{orderId}")
    public String buyOrder(@PathVariable("orderId") Long orderId) {
        orderService.buyOrder(orderId);
        return "redirect:/orders";
    }


}
