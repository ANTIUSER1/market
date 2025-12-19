package pn.market.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pn.market.entities.Item;
import pn.market.services.ModelService;
import pn.market.services.impl.CartServiceImpl;
import pn.market.services.impl.ItemServiceImpl;
import pn.market.services.impl.ModelServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@WebFluxTest(ItemController.class)
@AutoConfigureWebFlux
@Import({ItemServiceImpl.class, ModelServiceImpl.class})
class ItemControllerTest {


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
                .uri("/")
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
    void items() throws Exception {
        Mockito.when(itemService.findAll()).thenReturn(Flux.empty());
        Mockito.when(modelService.createPageble(0, 2, "sorted"))
                .thenReturn(Mono.empty());
        Mono<Pageable> pageableMono = modelService.createPageble(0, 2, "sorted");
        Mockito.when(itemService.findAllAndPaging(pageableMono))
                .thenReturn(Mono.empty());
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
    void itemById() throws Exception {
        Mockito.when(cartService.placeItemToCart(2, "2")).thenReturn(Mono.empty());
        webTestClient.get()
                .uri("/items/{id}", 2)
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


/*


    @Test
    void items() throws Exception {

        /*
        Pageable pageable =
                PageRequest.of(0,
                        2,
                        Sort.Direction.ASC,
                        "title");

        when(itemService.findAllAndPaging(any())).thenReturn(new PageImpl<>(items, pageable, 2));

        var expected = new Paging(2, 0, true, false);
        Matcher<Paging> pagingMatcher = new AssertionMatcher<>() {
            @Override
            public void assertion(Paging actual) throws AssertionError {
                Assertions.assertEquals(expected.getPageSize(), actual.getPageSize());
            }
        };
        mvc.perform(MockMvcRequestBuilders.get("/")
                        .param("page", "0")
                        .param("pageSize", "2")
                        .param("search", "")
                        .param("sorted", "ALPHA"))
                .andExpect(model().attribute("items", items))
                .andExpect(model().attribute("page", 0))
                .andExpect(model().attribute("paging", pagingMatcher))
                .andExpect(view().name("items"))
                .andExpect(status().isOk());


    }

    @Test
    void item() throws Exception {
/*
        Pageable pageable = Pageable.ofSize(2);
        when(itemService.getById(1L)).thenReturn(Optional.ofNullable(item));
        assertTrue(itemService.getById(1L).isPresent());
        mvc.perform(MockMvcRequestBuilders.get("/items/{id}", 1)
                        .param("id", "1")
                        .param("action", "false")
                )
                .andExpect(status().isOk());


    }

 */
    //}
}



