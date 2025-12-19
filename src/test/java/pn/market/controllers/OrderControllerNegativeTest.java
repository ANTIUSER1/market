package pn.market.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pn.market.services.impl.ItemServiceImpl;
import pn.market.services.impl.OrderServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(OrderController.class)
@Import({OrderServiceImpl.class, ItemServiceImpl.class})
class OrderControllerNegativeTest {

    @MockitoBean
    private OrderServiceImpl orderService;

    @MockitoBean
    private ItemServiceImpl itemService;


    @Autowired
    private WebTestClient webTestClient;

    @Test
    void allOrders() {
        Mockito.when(orderService.findAll())
                .thenReturn(Flux.empty());
        webTestClient.get().uri("/orders/oo")
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void getOrderById() {
        Mockito.when(orderService.getById(1000L)).thenReturn(Mono.empty());
        webTestClient.get().uri("/orders/oo{id}", 100L)
                .exchange()
                .expectStatus().is4xxClientError();
    }
}