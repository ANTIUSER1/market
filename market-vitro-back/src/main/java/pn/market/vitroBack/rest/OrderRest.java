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
    @GetMapping("/order-by-user-uid/{uid}")
    public Mono<Order> getByUserId(@PathVariable("uid") Long uid) {
        return orderService.getByUserId(uid);
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

    @GetMapping("/create/{userId}/{itemId}")
    public Mono<Order> createWithUser(
            @PathVariable("itemId") Long itemId,
            @PathVariable("userId") Long userId) {

        Mono<Order> orderMono =orderService.createOrUseCartOfUser(userId,itemId);

                //orderService.create(userId, itemId);

        return orderMono;
    }


    @GetMapping("/show/{userId}/{orderId}")
    public Mono<Order> showOderOfUser(
            @PathVariable("orderId") Long orderId,
            @PathVariable("userId") Long userId) {
        return orderService.showOderOfUser(userId, orderId);
    }


    @GetMapping("/buy/{userId}/{orderId}")
    public void buyOrderOfUser(
            @PathVariable("orderId") Long orderId,
            @PathVariable("userId") Long userId) {
        System.out.println(".....RUN....BUY ORDER " + orderId + "  OF  USER :  " + userId);
        orderService.buyOrderOfUser(userId, orderId);
    }


}
