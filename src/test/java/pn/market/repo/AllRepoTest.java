package pn.market.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.entities.Item;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class AllRepoTest {

    private final List<Item> items = new ArrayList<>();
    @MockitoBean
    private CartRepo cartRepo;
    @MockitoBean
    private OrderRepo orderRepo;
    @MockitoBean
    private ItemRepo itemRepo;

    @BeforeEach
    void init() {
        for (int i = 0; i < 10; i++) {
            items.add(new Item());
        }
    }

    @Test
    void findCartMaxId() {
        when(cartRepo.findMaxId()).thenReturn(1L);
        assertEquals(1, cartRepo.findMaxId());
    }

    @Test
    void findOrderMaxId() {
        when(orderRepo.findMaxId()).thenReturn(1L);
        assertEquals(1, orderRepo.findMaxId());
    }

    @Test
    void getAllItemsSortedAscById() {
        when(itemRepo.findAll()).thenReturn(items);
        assertEquals(items, itemRepo.findAll());
    }
}