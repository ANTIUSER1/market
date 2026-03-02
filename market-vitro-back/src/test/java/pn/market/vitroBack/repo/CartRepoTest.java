package pn.market.vitroBack.repo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.forWEB.Cart;
import reactor.core.publisher.Mono;

@SpringBootTest
public class CartRepoTest {


    private Cart cart;
    private Mono<Cart> cartMono;
    @MockitoBean
    private CartRepo cartRepo;


    @BeforeEach
    void init() {
        cart = new Cart();
        cartMono = Mono.just(cart);
    }

    @Test
    void findByUserId() {
        Mockito.when(cartRepo.findById(1L))
                .thenReturn(cartMono);
        Assertions.assertEquals(cartMono, cartRepo.findById(1L));
    }
}
