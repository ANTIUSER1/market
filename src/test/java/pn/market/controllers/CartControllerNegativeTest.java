package pn.market.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import pn.market.additional.ActionType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerNegativeTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void addItem() throws Exception {
        mvc.perform(post("/cart/items-negative")
                        .param("itemId", "1")
                        .param("action", ActionType.MINUS.name()))
                .andExpect(status().isNotFound());
    }

}