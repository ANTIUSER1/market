package market.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroFront.controllers.CartController;
import pn.market.vitroFront.servicies.CartServiceImpl;
import pn.market.vitroFront.servicies.ItemServiceImpl;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pn.market.vitroFront.config.AuthPaths.VITRO_ITEM_API;


@WebFluxTest({CartController.class})
@Import({CartServiceImpl.class, ItemServiceImpl.class})
class CartControllerTest {

    @MockitoBean
    private CartServiceImpl cartService;
    @MockitoBean
    private ItemServiceImpl itemService;

    @Autowired
    private WebTestClient webTestClient;

//    @Test
//    void addItem() {
//        Mockito.when(cartService.placeItemToCart(2, "action"))
//                .thenReturn(Mono.empty());
//        Mono<Item> itemMono = cartService.placeItemToCart(2, "action");
//        Mockito.when(itemService.getItemsByCartDataFromMonoToFlux(itemMono))
//                .thenReturn(Flux.empty());
//        Flux<Item> itemsFlux = itemService.getItemsByCartDataFromMonoToFlux(itemMono);
//        Mockito.when(itemService.getTotalSum(itemsFlux)).thenReturn(Mono.just(100L));
//        webTestClient.get().uri("/cart/items")
////        webTestClient.get().uri("/cart/items?itemId=100&action=act")
//                .exchange()
//                .expectStatus().is4xxClientError();
//    }

    @Test
    void itemById() {
        Mockito.when(cartService.itemById(2))
                .thenReturn(Mono.just(new Item()));
        webTestClient.get()
                .uri(VITRO_ITEM_API + "/2")
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