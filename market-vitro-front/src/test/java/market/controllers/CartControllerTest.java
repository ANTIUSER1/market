package market.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pn.market.market_entities.forWEB.Item;
import pn.market.services.impl.CartServiceImpl;
import pn.market.services.impl.ItemServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


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
        webTestClient.get().uri("/cart/items")
//        webTestClient.get().uri("/cart/items?itemId=100&action=act")
                .exchange()
                .expectStatus().is4xxClientError();
    }


}