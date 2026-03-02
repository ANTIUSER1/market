package market.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pn.market.market_entities.forWEB.Cart;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroFront.servicies.CartServiceImpl;
import pn.market.vitroFront.servicies.ItemServiceImpl;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CartServiceImplTest {

    //    @Mock
//    private CartRepo cartRepo;
//    @Mock
//    private ItemRepo itemRepo;
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
        item.setId(1L);

    }


    @Test
    void getByIdTest() {
        when(cartService.getById(1L)).thenReturn(Mono.empty());
        assertEquals(Mono.empty(), cartService.getById(1L));
    }

    @Test
    void findAllAndPagingTest() {
        when(cartService.findAllAndPaging(Mono.empty())).thenReturn(Mono.empty());
        assertEquals(Mono.empty(), cartService.findAllAndPaging(Mono.empty()));
    }
/*
    @Test
    void createEmptyNewCartTest() {
        when(cartRepo.save(cart)).thenReturn(Mono.empty());
        when(cartService.createNewCart()).thenReturn(Mono.empty());
        assertEquals(Mono.empty(), cartService.createNewCart());
    }

    @Test
    void createNewCartTest() {
        when(cartRepo.save(cart)).thenReturn(Mono.just(cart));
        when(cartService.createNewCart()).thenReturn(Mono.just(1L));
        assertEquals(Mono.just(1L).blockOptional().get(),
                cartService.createNewCart().blockOptional().get());
        Mono<Long> longMono = cartService.createNewCart();
        StepVerifier.create(longMono)
                .expectNext(1L)
                .verifyComplete();
    }


    @Test
    void createCartForItemIfNotExistsTest() {
        when(cartService.createCartForItemIfNotExists(1L)).thenReturn(Mono.just(1L));
        assertEquals(Mono.just(1L).blockOptional().get(),
                cartService.createCartForItemIfNotExists(1L).blockOptional().get());
        Mono<Long> longMono = cartService.createCartForItemIfNotExists(1L);
        StepVerifier.create(longMono)
                .expectNext(1L)
                .verifyComplete();
    }

    @Test
    void placeEmptyItemToCartTest() {
        when(cartService.placeItemToCart(1L, "1L"))
                .thenReturn(Mono.empty());
        assertEquals(Mono.empty(), cartService.placeItemToCart(1L, "1L"));
        Mono<Item> itemMono = cartService.placeItemToCart(1L, "1L");
        StepVerifier.create(itemMono)
                .verifyComplete();

    }

    @Test
    void placeItemToCartTest() {
        when(cartService.placeItemToCart(1L, "1L"))
                .thenReturn(Mono.just(item));
        assertEquals(Mono.just(item).blockOptional().get(),
                cartService.placeItemToCart(1L, "1L").blockOptional().get());
        Mono<Item> itemMono = cartService.placeItemToCart(1L, "1L");
        System.out.println(itemMono.block());
        StepVerifier.create(itemMono)
                .expectNext(item)
                .verifyComplete();

    }
*/
}