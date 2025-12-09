package pn.market.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.entities.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @Autowired
    private DatabaseClient databaseClient;

    @BeforeEach
    void init() {
        for (int i = 0; i < 10; i++) {
            items.add(new Item());
        }
    }

    @Test
    void findCartMaxId() {
        when(cartRepo.findMaxId(databaseClient)).thenReturn(Mono.just( 1L));
        assertEquals(1, cartRepo.findMaxId(databaseClient));
    }


    @Test
    void getAllItemsSortedAscById() {
        when(itemRepo.findAll()).thenReturn(Flux.just( new Item(), new Item()) );
        assertEquals(items.size(), itemRepo.findAll().collectList().blockOptional().get().size());
    }
}