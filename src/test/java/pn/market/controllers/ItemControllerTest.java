package pn.market.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pn.market.entities.Item;
import pn.market.services.ModelService;
import pn.market.services.impl.ItemServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest({ItemController.class})
@Import({ItemServiceImpl.class, ModelService.class})
//@SpringBootTest
//@AutoConfigureMockMvc
class ItemControllerTest {

    @MockitoBean
    private ItemServiceImpl itemService;

    @MockitoBean
    private ModelService modelService;

    @Autowired
    private MockMvc mvc;

    private List<Item> items;


    @BeforeEach
    void setUp() {
        items = new ArrayList<>();
        Item item = new Item();
        item.setPrice(100L);
        item.setId(1L);
        item.setTitle("test");
        item.setDescription("test-d");
        item.setImgPath("p");
        item.setCount(100);
        items.add(item);

    }

    @Test
    void itemsIndex() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/")
                        .param("page", "0")
                        .param("pageSize", "2")
                        .param("search", "")
                        .param("sorted", "ALPHA")
                ).andExpect(view().name("items"))
                .andExpect(status().isOk()
                );
    }

    @Test
    void items() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/items")
                        .param("page", "0")
                        .param("pageSize", "2")
                        .param("search", "")
                        .param("sorted", "ALPHA")
                )
                .andExpect(view().name("items"))
                .andExpect(status().isOk()
                )
        ;
    }

    @Test
    void item() throws Exception {
        Pageable pageable = Pageable.ofSize(2);
        when(itemService.getById(1L)).thenReturn(Optional.of(items.get(0)));
        assertTrue(itemService.getById(1L).isPresent());
        mvc.perform(MockMvcRequestBuilders.get("/items/{id}", 1)
                        .param("id", "1")
                        .param("action", "false")
                )
                .andExpect(status().isOk());

    }

}