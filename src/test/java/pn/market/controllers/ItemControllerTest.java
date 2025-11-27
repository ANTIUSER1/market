package pn.market.controllers;

import org.hibernate.annotations.AttributeAccessor;
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
import java.util.Optional;

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
private  Item item;

    @BeforeEach
    void setUp() {
        items = new ArrayList<>();
        items.add(new Item());
        item=new Item();
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
    void items() throws Exception {

    }

    @Test
    void item() throws Exception {
        Pageable pageable = Pageable.ofSize(2);
        when(itemService.getById(1L)).thenReturn(Optional.ofNullable(item));
        assertTrue(itemService.getById(1L).isPresent());
     //  Item item1=itemService.getById(1L).get();
        mvc.perform(MockMvcRequestBuilders.get("/items/{id}", 1)
                        .param("id", "1")
                        .param("action", "false")
                )
                .andExpect(status().isOk());
    }
}