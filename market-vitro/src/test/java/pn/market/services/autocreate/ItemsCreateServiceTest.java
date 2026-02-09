package pn.market.services.autocreate;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.forWEB.Item;
import pn.market.repo.ItemRepo;

import java.util.ArrayList;
import java.util.List;

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


}