package pn.market.vitroBack.repo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.forWEB.Order;
import reactor.core.publisher.Mono;

@SpringBootTest
public class OrderRepoTest {

    private Order order;
    private Mono<Order> orderMono;
    @MockitoBean
    private OrderRepo orderRepo;


    @BeforeEach
    void init() {
        order = new Order(1L);
        orderMono = Mono.just(order);
    }

    @Test
    void findByUserId() {
        Mockito.when(orderRepo.findById(1L))
                .thenReturn(orderMono);
        Assertions.assertEquals(orderMono, orderRepo.findById(1L));
    }
}
