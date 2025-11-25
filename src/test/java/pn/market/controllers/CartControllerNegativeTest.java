package pn.market.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import pn.market.additional.ActionType;
import pn.market.services.impl.CartServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import({CartServiceImpl.class})
class CartControllerNegativeTest {


    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private MockMvc mvc;


    @Test
    void addItem() throws Exception {
        mvc.perform(post("/cart/items-0")
                .param("itemId", "1")
                .param("action", ActionType.MINUS.name()))
                .andExpect( status().is4xxClientError() );
    }
}