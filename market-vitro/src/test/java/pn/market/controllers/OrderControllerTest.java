package pn.market.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pn.market.market_entities.forWEB.Order;
import pn.market.services.impl.ItemServiceImpl;
import pn.market.services.impl.OrderServiceImpl;
import pn.market.services.impl.PaymentSupplierImpl;
import pn.market.services.impl.PaymentsServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebFluxTest(OrderController.class)
@Import({OrderServiceImpl.class, ItemServiceImpl.class})
class OrderControllerTest {

    @MockitoBean
    private OrderServiceImpl orderService;

    @MockitoBean
    private PaymentsServiceImpl paymentService;
    @MockitoBean
    private PaymentSupplierImpl paymentSupplier;
    @MockitoBean
    private ItemServiceImpl itemService;


    @Autowired
    private WebTestClient webTestClient;

    @Test
    void allOrders() {
        Mockito.when(orderService.findAll())
                .thenReturn(Flux.empty());
        webTestClient.get().uri("/orders")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .consumeWith(response -> {
                    String body = response.getResponseBody();
                    assertNotNull(body);
                    assertTrue(body.contains("<html")); // Проверяем, что страница содержит форму
                });
    }

    @Test
    void getOrderById() {
        Mockito.when(orderService.getById(1000L)).thenReturn(Mono.just(new Order(100L)));
        Mockito.when(paymentSupplier.setUserId(1L)).thenReturn(paymentSupplier);
        Mockito.when(paymentSupplier.setOrderId(1L)).thenReturn(paymentSupplier);
        Mockito.when(paymentService.sendPaymentInfo("hh", () -> Mono.empty())).thenReturn(Mono.empty());
        webTestClient.get().uri("/orders/100?newOrder=true")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .consumeWith(response -> {
                    String body = response.getResponseBody();
                    assertNotNull(body);
                    assertTrue(body.contains("<html")); // Проверяем, что страница содержит форму
                });
    }
}