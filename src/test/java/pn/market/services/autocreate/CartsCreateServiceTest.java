package pn.market.services.autocreate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.entities.Cart;
import pn.market.entities.Item;
import pn.market.repo.CartRepo;
import pn.market.repo.ItemRepo;
import pn.market.services.impl.CartServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CartsCreateServiceTest {

@MockitoBean
private ItemsCreateService itemsCreateService;

    @MockitoBean
    private CartRepo cartRepo;
    @MockitoBean
    private ItemRepo itemRepo;
    @MockitoBean
    private CartServiceImpl cartService;

 private Cart cart;


    @BeforeEach
    void init() {
        cart = new Cart();
        cart.setId(1L);
    }

    @Test
    void createRandomCart() {

        when(itemsCreateService.autoCreate()).thenReturn( new ArrayList<Item>());

    }
}