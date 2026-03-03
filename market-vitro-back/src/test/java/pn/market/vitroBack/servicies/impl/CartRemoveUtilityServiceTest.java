package pn.market.vitroBack.servicies.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.forWEB.CartItems;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest
class CartRemoveUtilityServiceTest {

    @MockitoBean
    private CartControlUtilityService cartControlUtilityService;

    private Mono<Void> voidMono;
    private Mono<List<CartItems>> cartItemsListMono;
    private Mono<Item> itemMono;
    private Long userId;

    @BeforeEach
    void init() {
        voidMono = Mono.empty();
    }

    @Test
    void removeItemFromCartOfUserYes() {
        Mockito.when(cartControlUtilityService.deleteCartItemsById(1L))
                .thenReturn(voidMono);
        Assertions.assertEquals(voidMono, cartControlUtilityService.deleteCartItemsById(1L));
    }

    @Test
    void removeItemFromCartOfUserNot() {
        Mockito.when(cartControlUtilityService.deleteCartItemsById(1L))
                .thenReturn(voidMono);
        Assertions.assertNotEquals(Mono.just(1L), cartControlUtilityService.deleteCartItemsById(1L));
    }
}