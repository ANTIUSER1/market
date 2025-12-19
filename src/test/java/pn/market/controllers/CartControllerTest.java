package pn.market.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pn.market.entities.Item;
import pn.market.services.impl.CartServiceImpl;
import pn.market.services.impl.ItemServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@WebFluxTest({CartController.class})
@Import({CartServiceImpl.class, ItemServiceImpl.class})
class CartControllerTest {

    @MockitoBean
    private CartServiceImpl cartService;
    @MockitoBean
    private ItemServiceImpl itemService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void addItem() {
        Mockito.when(cartService.placeItemToCart(2, "action"))
                .thenReturn(Mono.empty());
        Mono<Item> itemMono = cartService.placeItemToCart(2, "action");
        Mockito.when(itemService.getItemsByCartDataFromMonoToFlux(itemMono))
                .thenReturn(Flux.empty());
        Flux<Item> itemsFlux = itemService.getItemsByCartDataFromMonoToFlux(itemMono);
        Mockito.when(itemService.getTotalSum(itemsFlux)).thenReturn(Mono.just(100L));
        webTestClient.get().uri("/cart/items?itemId=100&action=act")
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