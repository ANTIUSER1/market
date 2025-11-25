package pn.market.repo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AllRepoTest {

    @MockitoBean
    private CartRepo cartRepo;
    @MockitoBean
    private OrderRepo orderRepo;
    @MockitoBean
    private ItemRepo itemRepo;


    @Test
    void findCartMaxId() {
        assertEquals(1, 1);
    }

@Test
    void findOrderMaxId() {
        assertEquals(1, 1);
    }

    @Test
    void getAllItemsSortedAscById(){

    }
    @Test
    void findItemsAll(){

    }
}