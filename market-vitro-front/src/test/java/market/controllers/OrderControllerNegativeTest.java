package market.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pn.market.vitroFront.controllers.OrderController;
import pn.market.vitroFront.servicies.ItemServiceImpl;
import pn.market.vitroFront.servicies.OrderServiceImpl;
import pn.market.vitroFront.servicies.PaymentSupplierImpl;
import pn.market.vitroFront.servicies.PaymentsServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(OrderController.class)
@Import({OrderServiceImpl.class, ItemServiceImpl.class})
class OrderControllerNegativeTest {

    @MockitoBean
    private OrderServiceImpl orderService;

    @MockitoBean
    private ItemServiceImpl itemService;

    @MockitoBean
    private PaymentsServiceImpl paymentService;
    @MockitoBean
    private PaymentSupplierImpl paymentSupplier;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void allOrders() {
        Mockito.when(orderService.findAll())
                .thenReturn(Flux.empty());
        Mockito.when(paymentSupplier.setUserId(1L)).thenReturn(paymentSupplier);
        Mockito.when(paymentSupplier.setOrderId(1L)).thenReturn(paymentSupplier);
        Mockito.when(paymentService.sendPaymentInfo("hh", () -> Mono.empty())).thenReturn(Mono.empty());
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