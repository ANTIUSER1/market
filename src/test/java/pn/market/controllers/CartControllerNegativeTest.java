package pn.market.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pn.market.additional.ActionType;
import pn.market.entities.Cart;
import pn.market.entities.Item;
import pn.market.services.impl.CartServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({CartController.class})
@Import({CartServiceImpl.class})
class CartControllerNegativeTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private CartServiceImpl cartService;

    private Cart cart;

    private List<Item> items;

    @BeforeEach
    void init() {
        items = new ArrayList<>();
        Item item = new Item();
        item.setPrice(100L);
        item.setId(100L);
        item.setTitle("test");
        item.setDescription("test-d");
        item.setImgPath("p");
        item.setCount(100);
        items.add(item);

        cart = new Cart(1L);

    }


    @Test
    void addItem() throws Exception {
        mvc.perform(post("/cart/items-negative")
                        .param("itemId", "1")
                        .param("action", ActionType.MINUS.name()))
                .andExpect(status().isNotFound());
    }

}