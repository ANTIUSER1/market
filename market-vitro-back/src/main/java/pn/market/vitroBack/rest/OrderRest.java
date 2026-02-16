package pn.market.vitroBack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pn.market.market_entities.forWEB.Order;
import pn.market.vitroBack.servicies.impl.OrderServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/order/items")
public class OrderRest {

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping
    public Flux<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Order> getById(@PathVariable("id") Long id) {
        return orderService.getById(id);
    }


    @PutMapping
    public Mono<Order> save(
            @RequestBody Order order) {
        return orderService.save(order);
    }
}
