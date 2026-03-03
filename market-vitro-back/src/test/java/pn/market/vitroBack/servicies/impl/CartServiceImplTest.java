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
class CartServiceImplTest {

    @Autowired
    private CartUtilityService cartUtilityService;

    @Autowired
    private CartControlUtilityService cartControlUtilityService;

    private Flux<Cart> cartFlux;
    private Mono<Cart> cartMono;
//    private   Mono<Item> itemMono;
//    private  Mono<Long>  longMono;

    @BeforeEach
    void init() {
        cartFlux = Flux.fromIterable(new ArrayList<>());
        cartMono = Mono.just(new Cart());
    }

    @Test
    void findAll() {
        Mockito.when(cartControlUtilityService.findAllCarts())
                .thenReturn(cartFlux);
        Assertions.assertEquals(cartFlux, cartControlUtilityService.findAllCarts());
    }


    @Test
    void createOrUseCartOfUser() {
        Mockito.when(cartUtilityService.createOrTestExistCart(1L))
                .thenReturn(cartMono);
        Assertions.assertEquals(cartMono, cartUtilityService.createOrTestExistCart(1L));

    }


}