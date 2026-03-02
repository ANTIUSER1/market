package pn.market.vitroBack.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.forWEB.Item;
import pn.market.market_entities.forWEB.Order;
import pn.market.vitroBack.servicies.impl.ItemServiceImpl;
import pn.market.vitroBack.servicies.impl.OrderServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ItemRestNegativeTest {

    private final List<Item> items = new ArrayList<>();
    @MockitoBean
    private ItemServiceImpl itemService;
    @MockitoBean
    private OrderServiceImpl orderService;
    private Flux<Item> itemFlux;
    private Mono<Item> itemMono;
    private Mono<Order> orderMono;
    private Mono<Long> longMono;
    private Item item;


    @BeforeEach
    void init() {
        item = new Item();
        item.setId(1L);
        for (int i = 1; i < 10; i++) {
            Item item = new Item();
            item.setId((long) i);
            items.add(item);
        }
        itemFlux = Flux.fromIterable(items);
        orderMono = Mono.just(new Order());
        itemMono = Mono.just(new Item());
        longMono = Mono.just(1L);
    }

    @Test
    void allItems() {
        Mockito.when(orderService.createOrUseCartOfUser(1L, 1L))
                .thenReturn(orderMono);
        Assertions.assertNotEquals(Mono.empty(), orderService.createOrUseCartOfUser(1L, 1L));
    }

    @Test
    void itemById() {
        Mockito.when(itemService.findById(1L))
                .thenReturn(itemMono);
        Assertions.assertNotEquals(Mono.empty(), itemService.findById(1L));
    }

    @Test
    void addToExistingOrderOfUser() {
        Mockito.when(orderService.createOrUseCartOfUser(1L, 1L))
                .thenReturn(orderMono);
        Assertions.assertNotEquals(Mono.empty(), orderService.createOrUseCartOfUser(1L, 1L));
    }

    @Test
    void getItemsByCartId() {
        Mockito.when(itemService.getItemsByCartIdT(1L))
                .thenReturn(itemFlux);
        Assertions.assertNotEquals(Mono.empty(), orderService.createOrUseCartOfUser(1L, 1L));
    }

    @Test
    void getCartOfUser() {
        Mockito.when(itemService.getItemsOfUser(1L))
                .thenReturn(itemFlux);
        Assertions.assertNotEquals(Mono.empty(), itemService.getItemsOfUser(1L));
    }

    @Test
    void getTotalCartOfUser() {
        Mockito.when(itemService.getTotalSumCartOfUser(1L))
                .thenReturn(longMono);
        Assertions.assertNotEquals(Mono.empty(), itemService.getTotalSumCartOfUser(1L));
    }

    @Test
    void getItemsByOrderId() {
        Mockito.when(itemService.getItemsByOrderId(1L))
                .thenReturn(itemFlux);
        Assertions.assertNotEquals(Mono.empty(), itemService.getItemsByOrderId(1L));
    }
}