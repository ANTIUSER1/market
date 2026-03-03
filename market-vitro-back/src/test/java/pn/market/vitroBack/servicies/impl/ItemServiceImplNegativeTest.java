package pn.market.vitroBack.servicies.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroBack.repo.ItemRepo;
import pn.market.vitroBack.repo.OrderRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@SpringBootTest
class ItemServiceImplNegativeTest {


    Item i;
    @MockitoBean
    private FileServiceImpl fileService;
    @MockitoBean
    private ItemRepo itemRepo;
    @MockitoBean
    private OrderRepo orderRepo;
    private Mono<Item> itemMono;
    private Flux<Item> itemFlux;

    @BeforeEach
    void init() {
        i = new Item();
        itemFlux = Flux.fromIterable(new ArrayList<>());
        itemMono = Mono.just(i);
    }

    @Test
    void findAll() {
        Mockito.when(itemRepo.findAll())
                .thenReturn(itemFlux);
        Assertions.assertNotEquals(Mono.empty(), itemRepo.findAll());
    }

    @Test
    void getById() {
        Mockito.when(itemRepo.findById(1L))
                .thenReturn(itemMono);
        Assertions.assertNotEquals(Mono.empty(), itemRepo.findById(1L));
    }

    @Test
    void getItemsByOrderId() {
        Mockito.when(itemRepo.findItemsByOrderId(1L))
                .thenReturn(itemFlux);
        Assertions.assertNotEquals(Mono.empty(), itemRepo.findItemsByOrderId(1L));
    }

    @Test
    void plusForMono() {
        Mockito.when(itemRepo.save(i))
                .thenReturn(itemMono);
        Assertions.assertNotEquals(Mono.empty(), itemRepo.save(i));
    }


}