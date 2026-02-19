package pn.market.vitroBack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.market_entities.forWEB.Order;
import pn.market.vitroBack.servicies.impl.ItemServiceImpl;
import pn.market.vitroBack.servicies.impl.OrderServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/vitro/order")
public class OrderRest {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ItemServiceImpl itemService;

    @GetMapping
    public Flux<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Order> getById(@PathVariable("id") Long id) {
        return orderService.getById(id);
    }


    @GetMapping("/save/{orderId}")
    public Mono<Order> save(
            @PathVariable("orderId") Long orderId) {
        return orderService.getById(orderId)
                .map(o -> orderService.save(o)).flatMap(o -> o);

    }

    @GetMapping("/save/{userId}/{itemId}")
    public Mono<Order> saveWithUser(
            @PathVariable("itemId") Long itemId,
            @PathVariable("userId") Long userId) {
        Order order = new Order();
        order.setUserId(userId);
        System.out.println("   saveWithUser   NEW ORDER: ITEM  " + itemId + "   USER " + userId);

        Mono<Order> orderMono = orderService.save(order)
                .map(o -> {
                    System.out.println("----++OOO +++ " + o);
                    itemService.findById(itemId)
                            .map(i -> {
                                System.out.println(" ++++++++ ITEM  " + itemId + "   UPDATE ");

                                i.setCartId(null);
                                i.setCount(0);
                                i.setOrderId(o.getId());
                                System.out.println("  OOOO " + o);
                                itemService.save(i).subscribe();
                                return o;
                            }).subscribe();
                    return o;
                });


        return orderMono;
        /*
        return orderService.getById(orderId)
                .map(oo -> {
                    oo.setUserId(userId);
                    return orderService.save(oo);
                }).flatMap(o -> o);

         */
    }
}
