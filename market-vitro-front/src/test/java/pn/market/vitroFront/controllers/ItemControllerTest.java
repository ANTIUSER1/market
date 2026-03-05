package pn.market.vitroFront.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroFront.config.WebClientConfig;
import pn.market.vitroFront.servicies.CartServiceImpl;
import pn.market.vitroFront.servicies.ItemServiceImpl;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
//@AutoConfigureWebFlux
@Import({
        CartServiceImpl.class,
        ItemServiceImpl.class,
        WebClientConfig.class

      //  ModelService.class,

      //  DatabaseClient.class,
  //      WebTestClient.class
})
class ItemControllerTest {

    @MockitoBean
    private ItemServiceImpl itemService;
//    @MockitoBean
//    private ModelService modelService;
//    @MockitoBean
//    private CartServiceImpl cartService;
//    @MockitoBean
//    private DatabaseClient databaseClient;

    @Autowired
    private WebTestClient webTestClient;


    private List<Item> items;

    private Item item;

    @BeforeEach
    void setUp(  ) {
        items = new ArrayList<>();
        item = new Item();
        item.setPrice(100L);
        item.setId(100L);
        item.setTitle("test");
        item.setDescription("test-d");
        item.setImgPath("p");
        item.setCount(100L);
        items.add(item);
      //webTestClient=WebTestClient.bindToController(itemService).build();
    }

    @Test
    void itemsIndex() {

/*
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
*/
    }
}