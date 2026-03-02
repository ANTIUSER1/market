package pn.market.vitroBack.repo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class ItemRepoNegativeTest {


    private final List<Item> items = new ArrayList<>();
    private Item item;
    private Flux<Item> itemsFlux = null;
    private Pageable pageable;
    @MockitoBean
    private CartRepo cartRepo;
    @MockitoBean
    private OrderRepo orderRepo;
    @MockitoBean
    private ItemRepo itemRepo;

    @Autowired
    private DatabaseClient databaseClient;

    @BeforeEach
    void init() {
        item = new Item();
        item.setId(1L);
        for (int i = 1; i < 10; i++) {
            Item item = new Item();
            item.setId((long) i);
            items.add(item);
        }
        itemsFlux = Flux.fromIterable(new ArrayList<>());

    }

    @Test
    void findItemsByUserId() {
        Mockito.when(itemRepo.findItemsByUserId(1L))
                .thenReturn(itemsFlux);
        Assertions.assertNotEquals(Flux.empty(), itemRepo.findItemsByUserId(1L));
    }

    @Test
    void findItemsByOrderId() {
        Mockito.when(itemRepo.findItemsByOrderId(1L))
                .thenReturn(itemsFlux);
        Assertions.assertNotEquals(Flux.empty(), itemRepo.findItemsByOrderId(1L));
    }


    @Test
    void findItemsByCartId() {
        Mockito.when(itemRepo.findItemsByCartId(1L))
                .thenReturn(itemsFlux);
        Assertions.assertNotEquals(Flux.empty(), itemRepo.findItemsByCartId(1L));
    }

}