package pn.market.vitroBack.servicies.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pn.market.market_entities.forWEB.Cart;
import pn.market.market_entities.forWEB.CartItems;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroBack.repo.CartItemsRepo;
import pn.market.vitroBack.repo.CartRepo;
import pn.market.vitroBack.repo.ItemRepo;
import pn.market.vitroBack.repo.UserDataRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
class CartControlUtilityServiceTest {


    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemsRepo cartItemsRepo;

    @Autowired
    private UserDataRepo userDataRepo;

    private Mono<Item> itemMono;
    private Flux<CartItems> cartItemsFlux;
    private Flux<Cart> cartFlux;
    private Mono<Cart> cartMono;
    private Mono<Void> voidMono;
    private Mono<Long> longMono;


    @Test
    void findItemById() {
    }

    @Test
    void saveItem() {
    }

    @Test
    void findCartItemsByCartId() {
    }

    @Test
    void findAllCarts() {
    }

    @Test
    void findUserById() {
    }

    @Test
    void saveUser() {
    }

    @Test
    void findCartById() {
    }

    @Test
    void saveCart() {
    }

    @Test
    void saveCartItems() {
    }

    @Test
    void countOfCartAndItemId() {
    }

    @Test
    void deleteCartItemsById() {
    }

    @Test
    void getTotalSumOfCart() {
    }
}