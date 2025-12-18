package pn.market.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pn.market.entities.Order;
import pn.market.repo.OrderRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
class OrderServiceImplTest {


    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderServiceImpl orderService;

    private Order order;


    @BeforeEach
    void init() {
        order = new Order();
        order.setId(1L);
    }

    @Test
    void findAllTest() {
        when(orderRepo.findAll()).thenReturn(Flux.empty());
        when(orderService.findAll()).thenReturn(Flux.empty());
        assertEquals(Flux.empty(), orderService.findAll());

    }

    @Test
    void findAllAndPagingTest() {
        when(orderService.findAllAndPaging(Mono.empty())
                .thenReturn(Mono.empty()));
        assertEquals(Mono.empty(), orderService.findAllAndPaging(Mono.empty()));
    }

    @Test
    void saveTest() {
        when(orderRepo.save(order)).thenReturn(Mono.empty());
        when(orderService.save(order)).thenReturn(Mono.empty());
        assertEquals(Mono.empty(), orderService.save(order));
    }

}