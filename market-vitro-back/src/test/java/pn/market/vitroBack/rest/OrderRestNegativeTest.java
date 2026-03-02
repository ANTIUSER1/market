package pn.market.vitroBack.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.forWEB.Order;
import pn.market.vitroBack.servicies.impl.OrderServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@SpringBootTest
class OrderRestNegativeTest {

    @MockitoBean
    private OrderServiceImpl orderService;

    private Mono<Order> orderMono;
    private Flux<Order> orderFlux;


    @BeforeEach
    void init() {
        orderMono = Mono.just(new Order());
        orderFlux = Flux.fromIterable(new ArrayList<>());
    }

    @Test
    void findAll() {
        Mockito.when(orderService.findAll())
                .thenReturn(orderFlux);
        Assertions.assertNotEquals(Flux.empty(), orderService.findAll());
    }

    @Test
    void getByUserId() {
        Mockito.when(orderService.getByUserId(1L))
                .thenReturn(orderMono);
        Assertions.assertNotEquals(Mono.empty(), orderService.getByUserId(1L));
    }

    @Test
    void getById() {
        Mockito.when(orderService.getById(1L))
                .thenReturn(orderMono);
        Assertions.assertNotEquals(Mono.empty(), orderService.getById(1L));
    }


    @Test
    void save() {
        Order order = new Order();
        Mockito.when(orderService.save(order))
                .thenReturn(orderMono);
        Assertions.assertNotEquals(Mono.empty(), orderService.save(order));
    }

    @Test
    void createWithUser() {
        Mockito.when(orderService.createOrUseCartOfUser(1L, 1L))
                .thenReturn(orderMono);
        Assertions.assertNotEquals(Mono.empty(), orderService.createOrUseCartOfUser(1L, 1L));
    }

}