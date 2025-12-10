package pn.market.services.autocreate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.entities.Cart;
import pn.market.entities.Item;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CartsCreateServiceTest {

    @MockitoBean
    private ItemsCreateService itemsCreateService;


    @MockitoBean
    private CartsCreateService cartService;

    private Cart cart;
    private List<Item> items;

    @BeforeEach
    void init() {
        cart = new Cart();
        cart.setId(1L);
        items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            items.add(new Item());
        }
    }

    @Test
    void createRandomCart() {

        when(itemsCreateService.autoCreate()).thenReturn(items);
        when(cartService.createRandomCart()).thenReturn(cart);
        assertEquals(1L, (long) cart.getId());
        assertEquals(3, items.size());
    }
}