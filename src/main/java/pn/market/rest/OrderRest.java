package pn.market.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.entities.Order;
import pn.market.repo.OrderRepo;
import pn.market.services.autocreate.OrdersCreateService;

@RestController
@RequestMapping("/api/orders")

public class OrderRest {

    @Autowired
    private OrdersCreateService ordersCreateService;

    @Autowired
    private OrderRepo orderRepo;

    @GetMapping("/add-to-order")
    public Order addToCart() {
        return ordersCreateService.createOrder();
    }

    @GetMapping(value = "/buy")
    public String buy() {
        ordersCreateService.buyLastOrder();
        return "Order deleted";
    }
}

