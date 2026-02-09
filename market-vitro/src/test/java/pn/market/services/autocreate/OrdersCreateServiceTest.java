package pn.market.services.autocreate;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.repo.CartRepo;

@SpringBootTest
class OrdersCreateServiceTest {

    @MockitoBean
    private CartRepo cartRepo;

    private Order order;
    private Cart cart;

    @BeforeEach
    void init() {
        order = new Order();
        cart = new Cart();
        cart.setId(100L);
    }

//    @Test
//    void createOrder() {
//        when(cartRepo.findMaxId()).thenReturn(100L);
//        long maxId = cartRepo.findMaxId();
//        when(cartRepo.findById(maxId)).thenReturn(Optional.of(cart));
//        assertTrue(cartRepo.findById(maxId).isPresent());
//        when(cartRepo.save(cart)).thenReturn(cart);
//        assertEquals(100, cart.getId());
//    }


}