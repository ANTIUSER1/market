package pn.market.vitroBack.repo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.forWEB.OrderItems;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrderItemsRepoTest {

    private Flux<OrderItems> orderItemsFlux;
private  Mono<Long> longMono;
    @MockitoBean
    private OrderItemsRepo orderItemsRepo;


    @BeforeEach
    void init() {
        OrderItems orderItems = new OrderItems();
        orderItems.setItemId(1L);
        List<OrderItems> orderItems1 = new ArrayList<>();
        orderItems1.add(orderItems);
        orderItemsFlux = Flux.fromIterable(orderItems1);
        longMono=Mono.just(1L);
    }

    @Test
    void findByOrderId() {
        Mockito.when(orderItemsRepo.findByOrderId(1L))
                .thenReturn(orderItemsFlux);
        Assertions.assertEquals(orderItemsFlux, orderItemsRepo.findByOrderId(1L));
    }


    @Test
    void findByItemId() {
        Mockito.when(orderItemsRepo.findByItemId(1L))
                .thenReturn(orderItemsFlux);
        Assertions.assertEquals(orderItemsFlux, orderItemsRepo.findByItemId(1L));
    }

    @Test
    void countOfOrderAndItemId() {
        Mockito.when(orderItemsRepo.countOfOrderAndItemId(1L, 1L))
                .thenReturn(Mono.just(1L));
        Assertions.assertEquals(longMono, orderItemsRepo.countOfOrderAndItemId(1L, 1L));
    }

}
