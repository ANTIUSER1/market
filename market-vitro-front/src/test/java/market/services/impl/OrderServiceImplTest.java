package market.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pn.market.market_entities.forWEB.Order;
import pn.market.vitroFront.servicies.OrderServiceImpl;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class OrderServiceImplTest {


    @Autowired
    private OrderServiceImpl orderService;

    private Order order;
    private List<Order> orders;

    @BeforeEach
    void init() {
        order = new Order(100L);
        orders = new ArrayList<>();
        orders.add(order);
    }
/*
    @Test
    void findAllEmptyTest() {
        when(orderRepo.findAll()).thenReturn(Flux.empty());
        when(orderService.findAll()).thenReturn(Flux.empty());
        assertNotNull(orderService.findAll().blockFirst());

    }

    @Test
    void findAllTest() {
        when(orderRepo.findAll()).thenReturn(Flux.empty());
        when(orderService.findAll())
                .thenReturn(Flux.fromIterable(orders));
        assertNotNull(orderService.findAll().blockFirst());
        Flux<Order> orderFlux = orderService.findAll();
        StepVerifier.create(orderFlux)
                .expectNext(order)
                .verifyComplete();


    }

    @Test
    void findAllAndPagingTest() {
        when(orderService.findAllAndPaging(Mono.empty())
                .thenReturn(Mono.empty()));
        assertNotNull(orderService.findAllAndPaging(Mono.empty()));
    }

    @Test
    void saveTest() {
        when(orderRepo.save(order)).thenReturn(Mono.empty());
        when(orderService.save(order)).thenReturn(Mono.empty());
        assertNotNull(orderService.save(order));
    }

 */

}