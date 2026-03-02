package market.repo;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.r2dbc.core.DatabaseClient;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class AllRepoTest {

    private final List<Item> items = new ArrayList<>();
    private Flux<Item> itemsFlux = null;
    private Pageable pageable;


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
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 0;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
    }
/*
    @Test
    void findCartMaxId() {
        when(cartRepo.findMaxId(databaseClient)).thenReturn(Mono.just(1L));
        assertEquals(Mono.just(1L).blockOptional().get(), cartRepo.findMaxId(databaseClient).blockOptional().get());
    }

    @Test
    void findByCartIdTest() {
        when(itemRepo.findByCartId(1L)).thenReturn(itemsFlux);
        assertEquals(itemsFlux, itemRepo.findByCartId(1L));
    }

    @Test
    void findByOrderId() {
        when(itemRepo.findByCartId(1L)).thenReturn(itemsFlux);
        assertEquals(itemsFlux, itemRepo.findByCartId(1L));
    }

    @Test
    void findAllBy() {
        when(itemRepo.findAllBy(pageable)).thenReturn(itemsFlux);
        assertEquals(itemsFlux, itemRepo.findAllBy(pageable));
    }

 */
}