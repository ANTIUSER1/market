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
import pn.market.vitroFront.config.SecurityConfig;
import pn.market.vitroFront.config.WebClientConfig;
import pn.market.vitroFront.servicies.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest({ItemController.class,
        CartServiceImpl.class,
        ModelService.class,
        LoginService.class,
        UserDataServiceImpl.class
})
@AutoConfigureWebFlux
@Import({
        CartServiceImpl.class,
//        PaymentsServiceImpl.class,
//        PaymentSupplierImpl.class,
//        OrderController.class,
//        OrderServiceImpl.class,
        ItemServiceImpl.class,
        SecurityConfig.class,
        WebClientConfig.class
})
class CartControllerTest {


    @MockitoBean
    private LoginService loginService;

    @MockitoBean
    private ItemServiceImpl itemService;


    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private WebClient webClient;


    @Test
    @WithMockUser(username = "a", roles = "USER")
    void itemsList() {

        Mockito.when(loginService.getUserData()).thenReturn(new UserData());
        Mockito.when(itemService.getItemsByCartDataFromMonoToFlux(2L))
                .thenReturn(Flux.empty());
        Mockito.when(itemService.getTotalSum(2L))
                .thenReturn(Mono.just(22L));

        webTestClient
                .get().uri("/cart/a/2")
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
    void itemsOfUser() {
    }

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void additemsList() {
    }
}