package market.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pn.market.market_entities.forWEB.Item;
import pn.market.services.ModelService;
import pn.market.services.impl.CartServiceImpl;
import pn.market.services.impl.ItemServiceImpl;
import pn.market.services.impl.ModelServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


@WebFluxTest(ItemController.class)
@AutoConfigureWebFlux
@Import({ItemServiceImpl.class, ModelServiceImpl.class})
class ItemControllerNegativeTest {


    @MockitoBean
    private ItemServiceImpl itemService;
    @MockitoBean
    private ModelService modelService;
    @MockitoBean
    private CartServiceImpl cartService;
    @MockitoBean
    private DatabaseClient databaseClient;


    @Autowired
    private WebTestClient webTestClient;

    private List<Item> items;

    private Item item;

    @BeforeEach
    void setUp() {
        items = new ArrayList<>();
        item = new Item();
        item.setPrice(100L);
        item.setId(100L);
        item.setTitle("test");
        item.setDescription("test-d");
        item.setImgPath("p");
        item.setCount(100);
        items.add(item);
    }

    @Test
    void itemsIndex() throws Exception {
        Mockito.when(itemService.findAll()).thenReturn(Flux.empty());
        Mockito.when(modelService.createPageble(0, 2, "sorted"))
                .thenReturn(Mono.empty());
        Mono<Pageable> pageableMono = modelService.createPageble(0, 2, "sorted");
        Mockito.when(itemService.findAllAndPaging(pageableMono))
                .thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/a")
                .exchange()
                .expectStatus().is4xxClientError();
    }


    @Test
    void items() throws Exception {
        Mockito.when(itemService.findAll()).thenReturn(Flux.empty());
        Mockito.when(modelService.createPageble(0, 2, "sorted"))
                .thenReturn(Mono.empty());
        Mono<Pageable> pageableMono = modelService.createPageble(0, 2, "sorted");
        Mockito.when(itemService.findAllAndPaging(pageableMono))
                .thenReturn(Mono.empty());
        webTestClient.get()
                .uri("/items/json")
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void itemById() throws Exception {
        Mockito.when(cartService.placeItemToCart(2, "2")).thenReturn(Mono.empty());
        webTestClient.get()
                .uri("/items/h", 1000)
                .exchange()
                .expectStatus().is4xxClientError();

    }
}