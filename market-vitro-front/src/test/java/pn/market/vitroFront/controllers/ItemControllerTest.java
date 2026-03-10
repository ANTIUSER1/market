package pn.market.vitroFront.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.AutoConfigureWebFlux;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.data.UserData;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroFront.config.SecurityConfig;
import pn.market.vitroFront.config.WebClientConfig;
import pn.market.vitroFront.servicies.*;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebFluxTest({ItemController.class,
        CartServiceImpl.class,
        ModelService.class,
        LoginService.class,
        UserDataServiceImpl.class
})
@AutoConfigureWebFlux
@Import({
        CartServiceImpl.class,
        ItemServiceImpl.class,
        SecurityConfig.class,
        WebClientConfig.class
})
class ItemControllerTest {

    @MockitoBean
    private ItemServiceImpl itemService;

    @MockitoBean
    private CartServiceImpl cartService;

    @MockitoBean
    private LoginService loginService;


    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private WebClient webClient;


    private Item item;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setPrice(100L);
        item.setId(100L);
        item.setTitle("test");
        item.setDescription("test-d");
        item.setImgPath("p");
        item.setCount(100L);
    }

    @Test
    void itemsIndex() {

        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .consumeWith(response -> {
                    String body = response.getResponseBody();
                    Assertions.assertNotNull(body);
                    Assertions.assertTrue(body.contains("<html")); // Проверяем, что страница содержит форму
                });

    }


    @Test
    void items() throws Exception {
        webTestClient.get()
                .uri("/items")
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
    @WithMockUser(username = "user", roles = "USER")
    void itemById() {
        var mockUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        var mockHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec mockResponseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        Mockito.when(webClient.get()).thenReturn(mockUriSpec);
        Mockito.when(mockUriSpec.uri(ArgumentMatchers.anyString())).thenReturn(mockHeadersSpec);
        Mockito.when(mockHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        Mockito.when(mockResponseSpec.bodyToMono(String.class))
                .thenReturn(Mono.just("Hello World!"));
        Mockito.when(
                cartService.placeItemToCartOfUser(
                        ArgumentMatchers.any(),
                        ArgumentMatchers.eq(2L),
                        ArgumentMatchers.eq("action")
                )).thenReturn(Mono.just(item));

        Mockito.when(loginService.getUserData()).thenReturn(new UserData());

        webTestClient
                .get().uri("/items/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body ->
                {
                    assert body != null;
                    Assertions.assertTrue(body.contains("Витрина магазина"));
                });
    }

}