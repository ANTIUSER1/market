package pn.market.vitroFront.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.AutoConfigureWebFlux;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.data.UserData;
import pn.market.market_entities.forWEB.Order;
import pn.market.vitroFront.config.SecurityConfig;
import pn.market.vitroFront.config.WebClientConfig;
import pn.market.vitroFront.servicies.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@WebFluxTest({ItemController.class,
        CartServiceImpl.class,
        ModelService.class,
        LoginService.class,
        UserDataServiceImpl.class
})
@AutoConfigureWebFlux
@Import({
        CartServiceImpl.class,
        PaymentsServiceImpl.class,
        PaymentSupplierImpl.class,
        OrderController.class,
        OrderServiceImpl.class,
        ItemServiceImpl.class,
        SecurityConfig.class,
        WebClientConfig.class
})
class OrderControllerTest {

    @MockitoBean
    public PaymentSupplierImpl paymentSupplier;
    @MockitoBean
    private ItemServiceImpl itemService;
    @MockitoBean
    private CartServiceImpl cartService;
    @MockitoBean
    private PaymentsServiceImpl paymentService;
    @MockitoBean
    private LoginService loginService;

    @MockitoBean
    private OrderServiceImpl orderService;


    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private WebClient webClient;


    @Test
    @WithMockUser(username = "a", roles = "USER")
    void allOrders() {
//        var mockUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
//        var mockHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
//        WebClient.ResponseSpec mockResponseSpec = Mockito.mock(WebClient.ResponseSpec.class);
//
//        Mockito.when(webClient.get()).thenReturn(mockUriSpec);
//        Mockito.when(mockUriSpec.uri(ArgumentMatchers.anyString())).thenReturn(mockHeadersSpec);
//        Mockito.when(mockHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
//        Mockito.when(mockResponseSpec.bodyToMono(String.class))
//                .thenReturn(Mono.just("Hello World!"));
//        Mockito.when(
//                cartService.placeItemToCartOfUser(
//                        ArgumentMatchers.any(),
//                        ArgumentMatchers.eq(2L),
//                        ArgumentMatchers.eq("action")
//                )).thenReturn(Mono.empty());
        UserData ud = new UserData();
        Mockito.when(loginService.getUserData()).thenReturn(ud);
        List<Order> orders = new ArrayList<>();
        Mockito.when(orderService.addItemsToAllByUserId(1L)).thenReturn(Mono.just(orders));
        webTestClient
                .get()
                .uri("/orders/a")
                //.uri("/orders/tmp/a")
                .exchange()
                .expectStatus().isOk()
//                .expectBody(String.class)
//                .value(body ->
//                {
//                    assert body != null;
//                    Assertions.assertTrue(body.contains("Витрина магазина"));
//                })
        ;

    }

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void saveNewOrderOfUser() {
//        var mockUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
//        var mockHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
//        WebClient.ResponseSpec mockResponseSpec = Mockito.mock(WebClient.ResponseSpec.class);
//
//        Mockito.when(webClient.get()).thenReturn(mockUriSpec);
//        Mockito.when(mockUriSpec.uri(ArgumentMatchers.anyString())).thenReturn(mockHeadersSpec);
//        Mockito.when(mockHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
//        Mockito.when(mockResponseSpec.bodyToMono(String.class))
//                .thenReturn(Mono.just("Hello World!"));
//        Mockito.when(
//                cartService.placeItemToCartOfUser(
//                        ArgumentMatchers.any(),
//                        ArgumentMatchers.eq(2L),
//                        ArgumentMatchers.eq("action")
//                )).thenReturn(Mono.empty());

        Mockito.when(loginService.getUserData()).thenReturn(new UserData());
        webTestClient
                .get().uri("/orders/item-to-order/a/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body ->
                {
                    assert body != null;
                    Assertions.assertTrue(body.contains("Витрина магазина"));
                });
    }

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void getOrderById() {
//        var mockUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
//        var mockHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
//        WebClient.ResponseSpec mockResponseSpec = Mockito.mock(WebClient.ResponseSpec.class);
//
//        Mockito.when(webClient.get()).thenReturn(mockUriSpec);
//        Mockito.when(mockUriSpec.uri(ArgumentMatchers.anyString())).thenReturn(mockHeadersSpec);
//        Mockito.when(mockHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
//        Mockito.when(mockResponseSpec.bodyToMono(String.class))
//                .thenReturn(Mono.just("Hello World!"));
//        Mockito.when(
//                cartService.placeItemToCartOfUser(
//                        ArgumentMatchers.any(),
//                        ArgumentMatchers.eq(2L),
//                        ArgumentMatchers.eq("action")
//                )).thenReturn(Mono.empty());

        Mockito.when(loginService.getUserData()).thenReturn(new UserData());
        webTestClient
                .get().uri("/orders/a/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body ->
                {
                    assert body != null;
                    Assertions.assertTrue(body.contains("Витрина магазина"));
                });
    }

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void getOrderById403() {
//        var mockUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
//        var mockHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
//        WebClient.ResponseSpec mockResponseSpec = Mockito.mock(WebClient.ResponseSpec.class);
//
//        Mockito.when(webClient.get()).thenReturn(mockUriSpec);
//        Mockito.when(mockUriSpec.uri(ArgumentMatchers.anyString())).thenReturn(mockHeadersSpec);
//        Mockito.when(mockHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
//        Mockito.when(mockResponseSpec.bodyToMono(String.class))
//                .thenReturn(Mono.just("Hello World!"));
//        Mockito.when(
//                cartService.placeItemToCartOfUser(
//                        ArgumentMatchers.any(),
//                        ArgumentMatchers.eq(2L),
//                        ArgumentMatchers.eq("action")
//                )).thenReturn(Mono.empty());

        Mockito.when(loginService.getUserData()).thenReturn(new UserData());
        webTestClient
                .get().uri("/orders/b/2")
                .exchange()
                .expectStatus().is4xxClientError();
    }


}