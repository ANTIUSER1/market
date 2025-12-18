package pn.market.repo;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@SpringBootTest
class AllRepoTest {

    private final List<Item> items = new ArrayList<>();
    private Flux<Item> itemsFlux = null;

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
        for (int i = 1; i < 10; i++) {
            Item item = new Item();
            item.setId((long) i);
            items.add(item);
        }
        itemsFlux = Flux.fromIterable(items);
    }

    @Test
    void findCartMaxId() {
        when(cartRepo.findMaxId(databaseClient)).thenReturn(Mono.just(1L));
        assertEquals(Mono.just(1L).blockOptional().get(), cartRepo.findMaxId(databaseClient).blockOptional().get());
    }


}