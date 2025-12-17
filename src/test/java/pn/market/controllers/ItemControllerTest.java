package pn.market.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pn.market.entities.Item;
import pn.market.services.impl.ItemServiceImpl;
import pn.market.services.impl.ModelServiceImpl;

import java.util.ArrayList;
import java.util.List;


@WebFluxTest(controllers = {ItemController.class})
//@Import({
//        ItemServiceImpl.class,
//        ModelServiceImpl.class
//
//
//})


//@WebMvcTest({ItemController.class})
@Import({ItemServiceImpl.class, ModelServiceImpl.class})
class ItemControllerTest {


    @MockitoBean
    private ItemServiceImpl itemService;

    @Autowired
    private ModelServiceImpl modelService;
//
//    @Autowired
//    private MockMvc mvc;

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
/*
        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class).consumeWith(response -> {
                    String body = response.getResponseBody();
                    assertNotNull(body);
                  assertTrue(body.contains("<form")); // Проверяем, что страница содержит форму
                });

*/


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


 */
    }


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


         */
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


 */
    }

}



