package pn.market.services.autocreate;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.entities.Item;
import pn.market.repo.ItemRepo;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ItemsCreateServiceNegativeTest {

    @MockitoBean
    private ItemRepo itemRepo;
    @MockitoBean
    private ItemsCreateService itemsService;
    private List<Item> items;


    @BeforeEach
    void init() {
        items = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            items.add(new Item());
        }
    }

//   @Test
//    void autoCreate() {
//        when(itemRepo.getAllItemsSortedAscById()).thenReturn(items);
//        when(itemsService.autoCreate()).thenReturn(items);
//        assertNotEquals(20, itemsService.autoCreate().size());
//    }
}