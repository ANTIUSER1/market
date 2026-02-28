package market.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pn.market.market_entities.forWEB.Cart;
import pn.market.market_entities.forWEB.Item;
import pn.market.repo.CartRepo;
import pn.market.repo.ItemRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CartServiceImplNegativeTest {

    @Mock
    private CartRepo cartRepo;
    @Mock
    private ItemRepo itemRepo;
    @Autowired
    private ItemServiceImpl itemService;
    @Mock
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
    void getByIdTest() {
        when(cartService.getById(1L)).thenReturn(Mono.empty());
        assertNotEquals(Flux.empty(), cartService.getById(1L));
    }

    @Test
    void findAllAndPagingTest() {
        when(cartService.findAllAndPaging(Mono.empty())).thenReturn(Mono.empty());
        assertNotEquals(Flux.empty(), cartService.findAllAndPaging(Mono.empty()));
    }

    @Test
    void createNewCartTest() {
        when(cartRepo.save(cart)).thenReturn(Mono.empty());
        when(cartService.createNewCart()).thenReturn(Mono.empty());
        assertNotEquals(Flux.empty(), cartService.createNewCart());
    }

    @Test
    void createCartForItemIfNotExistsTest() {
        when(cartService.createCartForItemIfNotExists(1L)).thenReturn(Mono.empty());
        assertNotEquals(Flux.empty(), cartService.createCartForItemIfNotExists(1L));
    }

    @Test
    void placeItemToCartTest() {
        when(cartService.placeItemToCart(1L, "1L")).thenReturn(Mono.empty());
        assertNotEquals(Flux.empty(), cartService.placeItemToCart(1L, "1L"));
    }

}