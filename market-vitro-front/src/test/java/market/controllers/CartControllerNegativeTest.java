package market.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pn.market.vitroFront.controllers.CartController;
import pn.market.vitroFront.servicies.CartServiceImpl;
import pn.market.vitroFront.servicies.ItemServiceImpl;


@WebFluxTest({CartController.class})
@Import({CartServiceImpl.class, ItemServiceImpl.class})
class CartControllerNegativeTest {

    @MockitoBean
    private CartServiceImpl cartService;
    @MockitoBean
    private ItemServiceImpl itemService;

    @Autowired
    private WebTestClient webTestClient;
/*
    @Test
    void addItem() {
        Mockito.when(cartService.placeItemToCart(2, "action"))
                .thenReturn(Mono.empty());
        Mono<Item> itemMono = cartService.placeItemToCart(2, "action");
        Mockito.when(itemService.getItemsByCartDataFromMonoToFlux(itemMono))
                .thenReturn(Flux.empty());
        Flux<Item> itemsFlux = itemService.getItemsByCartDataFromMonoToFlux(itemMono);
        Mockito.when(itemService.getTotalSum(itemsFlux)).thenReturn(Mono.just(100L));
        webTestClient.get().uri("/cart/items-?itemId=100&action=act")
                .exchange()
                .expectStatus().is4xxClientError();
    }
*/

    @Test
    void itemsListTest() {
        webTestClient.get().uri("/cart/items")
//        webTestClient.get().uri("/cart/items?itemId=100&action=act")
                .exchange()
                .expectStatus().is4xxClientError();
    }
}