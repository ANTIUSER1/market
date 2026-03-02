package pn.market.vitroBack.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.forWEB.Cart;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroBack.servicies.impl.CartServiceImpl;
import reactor.core.publisher.Mono;

@SpringBootTest
class CartRestTest {

    @MockitoBean
    private CartServiceImpl cartService;

    private Mono<Item> itemMono;
    private Mono<Cart> cartMono;
    private Mono<Long> longMono;

    @BeforeEach
    void init() {
        itemMono = Mono.just(new Item());
        cartMono = Mono.just(new Cart());
        longMono = Mono.just(100L);
    }

    @Test
    void createCartOfUser() {
        Mockito.when(cartService.createOrUseCartOfUser(1L, 1L))
                .thenReturn(cartMono);
        Assertions.assertEquals(cartMono, cartService.createOrUseCartOfUser(1L, 1L));
    }

    @Test
    void getTotalSumOfCart() {
        Mockito.when(cartService.getTotalSumOfCart(1L))
                .thenReturn(longMono);
        Assertions.assertEquals(longMono, cartService.getTotalSumOfCart(1L));
    }

    @Test
    void removeCartOfUser() {
        Mockito.when(cartService.removeFromCartOfUser(1L, 1L))
                .thenReturn(itemMono);
        Assertions.assertEquals(itemMono, cartService.removeFromCartOfUser(1L, 1L));
    }
}