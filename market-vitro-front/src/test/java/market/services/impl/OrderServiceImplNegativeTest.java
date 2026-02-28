package market.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import pn.market.market_entities.forWEB.Order;
import pn.market.repo.OrderRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class OrderServiceImplNegativeTest {


    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderServiceImpl orderService;

    private Order order;
    private Pageable pageable;

    @BeforeEach
    void init() {
        order = new Order();
        order.setId(1L);
        pageable = Pageable.unpaged();
    }

    @Test
    void findAllTest() {
        when(orderRepo.findAll()).thenReturn(Flux.empty());
        when(orderService.findAll()).thenReturn(Flux.just(List.of(order)));
        assertNotEquals(Flux.fromIterable(List.of()),
                orderService.findAll().collectList().blockOptional().get().size());
    }

    @Test
    void findAllAndPagingTest() {
        when(orderService.findAllAndPaging(Mono.empty())
                .thenReturn(pageable));
        assertNotEquals(2L,
                orderService.findAllAndPaging(Mono.empty()));
    }

    @Test
    void saveTest() {
        when(orderRepo.save(order)).thenReturn(Mono.empty());
        when(orderService.save(order)).thenReturn(Mono.empty());
        assertNotEquals(Flux.fromIterable(List.of()), orderService.save(order));
    }

}