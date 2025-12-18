package pn.market.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.entities.Cart;
import pn.market.entities.Item;
import pn.market.repo.CartRepo;
import pn.market.repo.ItemRepo;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CartServiceImplNegativeTest {

    @MockitoBean
    private CartRepo cartRepo;
    @MockitoBean
    private ItemRepo itemRepo;
    @Autowired
    private ItemServiceImpl itemService;
    @MockitoBean
    private CartServiceImpl cartService;

    private Cart cart;
    private Item item;

    @BeforeEach
    void init() {
        cart = new Cart();
        item = new Item();
        cart.setId(1L);
    }

    @Test
    void findAllAndPagingTest() {
        when(cartService.findAllAndPaging(Mono.empty())).thenReturn(Mono.empty());
        assertEquals(Mono.empty(), cartService.findAllAndPaging(Mono.empty()));
    }

    @Test
    void createNewCartTest() {
        when(cartRepo.save(cart)).thenReturn(Mono.empty());
        when(cartService.createNewCart()).thenReturn(Mono.empty());
        assertEquals(Mono.empty(), cartService.createNewCart());
    }

    @Test
    void createCartForItemIfNotExistsTest() {
        when(itemService.findById(1L)).thenReturn(Mono.empty());
        when(cartService.createCartForItemIfNotExists(1L)).thenReturn(Mono.empty());
        assertEquals(Mono.empty(), cartService.createCartForItemIfNotExists(1L));
    }

    @Test
    void placeItemToCartTest() {
        when(itemService.findById(1L)).thenReturn(Mono.empty());
        when(cartService.placeItemToCart(1L, "1L")).thenReturn(Mono.empty());
        assertEquals(Mono.empty(), cartService.placeItemToCart(1L, "1L"));
    }
}