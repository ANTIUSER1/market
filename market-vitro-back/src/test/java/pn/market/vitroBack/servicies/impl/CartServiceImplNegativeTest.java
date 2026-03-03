package pn.market.vitroBack.servicies.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pn.market.market_entities.forWEB.Cart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@SpringBootTest
class CartServiceImplNegativeTest {

    @Autowired
    private CartUtilityService cartUtilityService;

    @Autowired
    private CartControlUtilityService cartControlUtilityService;

    private Flux<Cart> cartFlux;
    private Mono<Cart> cartMono;

    @BeforeEach
    void init() {
        cartFlux = Flux.fromIterable(new ArrayList<>());
        cartMono = Mono.just(new Cart());
    }

    @Test
    void findAll() {
        Mockito.when(cartControlUtilityService.findAllCarts())
                .thenReturn(cartFlux);
        Assertions.assertNotEquals(Flux.empty(), cartControlUtilityService.findAllCarts());
    }


    @Test
    void createOrUseCartOfUser() {
        Mockito.when(cartUtilityService.createOrTestExistCart(1L))
                .thenReturn(cartMono);
        Assertions.assertNotEquals(Mono.empty(), cartUtilityService.createOrTestExistCart(1L));

    }


}