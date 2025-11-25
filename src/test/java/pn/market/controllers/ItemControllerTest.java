package pn.market.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pn.market.entities.Item;
import pn.market.services.impl.ItemServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
//@SpringBootTest
//@AutoConfigureMockMvc
class ItemControllerTest {

    @MockitoBean
    private ItemServiceImpl itemService;

    @Autowired
    private MockMvc mvc;


    private List<Item> items;

    @BeforeEach
    void setUp() {
        items = new ArrayList<>();
        items.add(new Item());
    }

    @Test
    void itemsIndex() throws Exception {
        Pageable pageable = Pageable.ofSize(2);
        when(itemService.findAllAndPaging(pageable))
                .thenReturn(new PageImpl<>(items, pageable, 2));
        assertTrue(itemService.findAllAndPaging(pageable).getTotalPages() == 1);

        mvc.perform(MockMvcRequestBuilders.get("/")
                        .param("page", "1")
                        .param("pageSize", "10")
                        .param("search", "")
                        .param("sorted", "ALPHA")
                )
                .andExpect(status().isOk()
                );

    }

    @Test
    void items() {
    }

    @Test
    void item() {
    }
}