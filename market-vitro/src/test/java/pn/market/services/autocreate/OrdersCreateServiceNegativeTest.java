package pn.market.services.autocreate;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.forWEB.Cart;
import pn.market.market_entities.forWEB.Order;
import pn.market.repo.CartRepo;


@SpringBootTest
class OrdersCreateServiceNegativeTest {

    @MockitoBean
    private CartRepo cartRepo;

    private Order order;
    private Cart cart;

    @Autowired
    private DatabaseClient databaseClient;

    @BeforeEach
    void init() {
        order = new Order();
        cart = new Cart();
        cart.setId(100L);
    }

//    @Test
//    void createOrder() {
//        when(cartRepo.findMaxId(databaseClient)).thenReturn(Mono.just( 100L) );
//        long maxId = cartRepo.findMaxId(databaseClient).block();
//        when(cartRepo.findById(maxId)).thenReturn(Optional.of(cart));
//        assertFalse(cartRepo.findById(maxId).blockOptional().isEmpty());
//        when(cartRepo.save(cart)).thenReturn( Mono.just(cart));
//        assertNotEquals(1100, cart.getId());
//    }


}