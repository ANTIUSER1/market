package pn.market.services.autocreate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.entities.Item;
import pn.market.repo.ItemRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemsCreateServiceTest {

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

    @Test
    void autoCreate() {
        when(itemRepo.getAllItemsSortedAscById()).thenReturn(items);
        when(itemsService.autoCreate()).thenReturn(items);
        assertEquals(2, itemsService.autoCreate().size());
    }
}