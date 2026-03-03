package pn.market.vitroBack.servicies.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.data.UserData;
import pn.market.market_entities.forWEB.Cart;
import pn.market.market_entities.forWEB.CartItems;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroBack.repo.CartItemsRepo;
import pn.market.vitroBack.repo.CartRepo;
import pn.market.vitroBack.repo.ItemRepo;
import pn.market.vitroBack.repo.UserDataRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@SpringBootTest
class CartControlUtilityServiceNegativeTest {


    Mono<UserData> userDataMono;
    @MockitoBean
    private ItemRepo itemRepo;
    @MockitoBean
    private CartRepo cartRepo;
    @MockitoBean
    private CartItemsRepo cartItemsRepo;
    @MockitoBean
    private UserDataRepo userDataRepo;
    private Mono<Item> itemMono;
    private Flux<CartItems> cartItemsFlux;
    private Mono<CartItems> cartItemsMono;
    private Flux<Cart> cartFlux;
    private Mono<Cart> cartMono;

    private Mono<Long> longMono;
    private Item i;
    private UserData u;
    private Cart c;
    private CartItems ci;

    @BeforeEach
    void init() {
        i = new Item();
        itemMono = Mono.just(i);
        u = new UserData();
        c = new Cart();
        ci = new CartItems();
        cartItemsMono = Mono.just(ci);
        cartMono = Mono.just(c);
        userDataMono = Mono.just(u);

        longMono = Mono.just(1L);
        cartItemsFlux = Flux.fromIterable(new ArrayList<>());
        cartFlux = Flux.fromIterable(new ArrayList<>());
    }

    @Test
    void findItemById() {
        Mockito.when(itemRepo.findById(1L))
                .thenReturn(itemMono);
        Assertions.assertNotEquals(Mono.empty(), itemRepo.findById(1L));
    }

    @Test
    void saveItem() {
        Mockito.when(itemRepo.save(i))
                .thenReturn(itemMono);
        Assertions.assertNotEquals(Mono.empty(), itemRepo.save(i));
    }

    @Test
    void findCartItemsByCartId() {
        Mockito.when(cartItemsRepo.findByCartId(1L))
                .thenReturn(cartItemsFlux);
        Assertions.assertNotEquals(Flux.empty(), cartItemsRepo.findByCartId(1L));
    }

    @Test
    void findAllCarts() {
        Mockito.when(cartRepo.findAll())
                .thenReturn(cartFlux);
        Assertions.assertNotEquals(Flux.empty(), cartRepo.findAll());
    }

    @Test
    void findUserById() {
        Mockito.when(userDataRepo.findById(1L))
                .thenReturn(userDataMono);
        Assertions.assertNotEquals(Mono.empty(), userDataRepo.findById(1L));
    }

    @Test
    void saveUser() {
        Mockito.when(userDataRepo.save(u))
                .thenReturn(userDataMono);
        Assertions.assertNotEquals(Mono.empty(), userDataRepo.save(u));
    }

    @Test
    void findCartById() {
        Mockito.when(cartRepo.findById(1L))
                .thenReturn(cartMono);
        Assertions.assertNotEquals(Mono.empty(), cartRepo.findById(1L));
    }

    @Test
    void saveCart() {
        Mockito.when(cartRepo.save(c))
                .thenReturn(cartMono);
        Assertions.assertNotEquals(Mono.empty(), cartRepo.save(c));
    }

    @Test
    void saveCartItems() {
        Mockito.when(cartItemsRepo.save(ci))
                .thenReturn(cartItemsMono);
        Assertions.assertNotEquals(Mono.empty(), cartItemsRepo.save(ci));
    }

    @Test
    void countOfCartAndItemId() {
        Mockito.when(cartItemsRepo.countOfCartAndItemId(1L, 1L))
                .thenReturn(longMono);
        Assertions.assertNotEquals(Mono.empty(), cartItemsRepo.countOfCartAndItemId(1L, 1L));
    }


}