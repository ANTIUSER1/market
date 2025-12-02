package pn.market.controllers;


import org.assertj.core.matcher.AssertionMatcher;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pn.market.additional.Paging;
import pn.market.entities.Item;
import pn.market.services.impl.ItemServiceImpl;
import pn.market.services.impl.ModelServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({ItemController.class})
@Import({ItemServiceImpl.class, ModelServiceImpl.class})
class ItemControllerNegativeTest {

    @MockitoBean
    private ItemServiceImpl itemService;

    @Autowired
    private ModelServiceImpl modelService;

    @Autowired
    private MockMvc mvc;

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
        mvc.perform(MockMvcRequestBuilders.get("/negative")
                        .param("page", "0")
                        .param("pageSize", "2")
                        .param("search", "")
                        .param("sorted", "ALPHA"))
                .andExpect(status().isNotFound());


    }


    @Test
    void items() throws Exception {

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
        mvc.perform(MockMvcRequestBuilders.get("/items-negative")
                        .param("page", "0")
                        .param("pageSize", "2")
                        .param("search", "")
                        .param("sorted", "ALPHA"))
                .andExpect(status().isNotFound());
    }

    @Test
    void item() throws Exception {

        Pageable pageable = Pageable.ofSize(2);
        when(itemService.getById(1L)).thenReturn(Optional.ofNullable(item));
        assertTrue(itemService.getById(1L).isPresent());
        mvc.perform(MockMvcRequestBuilders.get("/items-negative/{id}", 1111)
                        .param("id", "1111")
                        .param("action", "false")
                )
                .andExpect(status().isNotFound());

    }

}



