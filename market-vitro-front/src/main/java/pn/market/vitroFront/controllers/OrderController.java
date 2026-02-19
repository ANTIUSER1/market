package pn.market.vitroFront.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import pn.market.market_entities.forWEB.Order;
import pn.market.vitroFront.servicies.LoginService;
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

//    @Autowired
//    private ItemServiceImpl itemService;

    @Autowired
    private PaymentsServiceImpl paymentService;

    @Autowired
    private PaymentSupplierImpl paymentSupplier;

    @Autowired
    private LoginService loginService;

    @GetMapping
    public Mono<Rendering> allOrders() {
        Mono<List<Order>> orders = orderService.addItemsToAll();
        Mono<Rendering> r =
                Mono.just(Rendering.view("orders")
                        .modelAttribute("orderData", orders)
                        .build());
        return r;
    }

    @GetMapping("/save/{itemId}")
    public Mono<Rendering> saveNewOrderOfUser(
            @PathVariable("itemId") Long itemId) {

        Long userId = loginService.getUserData().getId();
        System.out.println("   saveNewOrderOfUser   NEW ORDER: ITEM  " + itemId + "   USER " + userId);
        Mono<Order> order = orderService.saveNewCompleteOrderOfUserById(userId, itemId);

        Mono<Rendering> r =
                Mono.just(Rendering.view("order")
                        .modelAttribute("orderData", order)
                        .modelAttribute("newOrder", false)
                        .build());
        return r;

    }

    //************* add roles ***
    @GetMapping("/update/{itemId}")
    public Mono<Rendering> updateOrderOfUser(
            @PathVariable("itemId") Long itemId) {

        Long userId = loginService.getUserData().getId();
        System.out.println("   updateOrderOfUser   NEW ORDER: ITEM  " + itemId + "   USER " + userId);
        Mono<Order> order = orderService.updateOrder(userId, itemId);

        Mono<Rendering> r =
                Mono.just(Rendering.view("order")
                        .modelAttribute("orderData", order)
                        .modelAttribute("newOrder", false)
                        .build());
        return r;

    }


    //************* add roles ***
    //---------detect not done---
    @GetMapping("/{orderId}")
    public Mono<Rendering> getOrderById(
            @PathVariable("orderId") Long orderId
    ) {
        Long userId = loginService.getUserData().getId();
        Mono<Order> order = orderService.showOrderOfUserById(userId, orderId);
        Mono<Rendering> r =
                Mono.just(Rendering.view("order")
                        .modelAttribute("orderData", order)
                        .modelAttribute("newOrder", false)
                        .build());
        paymentService.configurePayments(userId, orderId);
        return r;
    }

    @GetMapping("/buy/{orderId}")
    public String buyOrder(@PathVariable("orderId") Long orderId) {
        orderService.buyOrder(orderId);
        return "redirect:/orders";
    }

}
