package pn.market.services.autocreate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.entities.Cart;
import pn.market.entities.Order;
import pn.market.repo.CartRepo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrdersCreateServiceNegativeTest {

    @MockitoBean
    private CartRepo cartRepo;

 private Order order;
    private Cart cart;

    @BeforeEach
    void init(){
        order = new Order();
        cart = new Cart();
        cart.setId(100L);
    }
    @Test
    void createOrder() {
        when(cartRepo.findMaxId()).thenReturn(100L);
        long maxId= cartRepo.findMaxId();
        when(cartRepo.findById(maxId)).thenReturn(Optional.of( cart ));
        assertFalse(cartRepo.findById(maxId).isEmpty());
        when(cartRepo.save(cart)).thenReturn(cart);
        assertNotEquals( 1100, cart.getId());
    }


}