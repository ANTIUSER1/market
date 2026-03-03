package pn.market.vitroBack.servicies.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.data.UserData;
import pn.market.market_entities.forWEB.Item;
import pn.market.market_entities.forWEB.Order;
import pn.market.market_entities.forWEB.OrderItems;
import pn.market.vitroBack.repo.ItemRepo;
import pn.market.vitroBack.repo.OrderItemsRepo;
import pn.market.vitroBack.repo.OrderRepo;
import pn.market.vitroBack.repo.UserDataRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@SpringBootTest
class OrderControlUtilityServiceTest {

    @MockitoBean
    private ItemRepo itemRepo;

    @MockitoBean
    private OrderRepo orderRepo;

    @MockitoBean
    private OrderItemsRepo orderItemsRepo;

    @MockitoBean
    private UserDataRepo userDataRepo;

    private Mono<Item> itemMono;
    private Flux<Item> itemFlux;
    private Flux<OrderItems> orderItemsFlux;
    private Mono<OrderItems> orderItemsMono;
    private Mono<UserData> userDataMono;
    private Mono<Order> orderMono;
    private Mono<Long> longMono;
    private Item i;
    private UserData u;

    @BeforeEach
    void init() {
        i = new Item();
        u = new UserData();
        itemFlux = Flux.fromIterable(new ArrayList<>());
        itemMono = Mono.just(i);
        orderItemsFlux = Flux.fromIterable(new ArrayList<>());
        orderItemsMono = Mono.just(new OrderItems());
        userDataMono = Mono.just(new UserData());
        orderMono = Mono.just(new Order());
        longMono = Mono.just(1L);
    }


    @Test
    void findItemById() {
        Mockito.when(itemRepo.findById(1L))
                .thenReturn(itemMono);
        Assertions.assertEquals(itemMono, itemRepo.findById(1L));
    }

    @Test
    void saveItem() {
        Mockito.when(itemRepo.save(i))
                .thenReturn(itemMono);
        Assertions.assertEquals(itemMono, itemRepo.save(i));
    }


    @Test
    void findOrderItemsByOrderId() {
        Mockito.when(orderItemsRepo.findByOrderId(1L))
                .thenReturn(orderItemsFlux);
        Assertions.assertEquals(orderItemsFlux, orderItemsRepo.findByOrderId(1L));
    }

    @Test
    void findAllOrders() {
        Mockito.when(userDataRepo.findById(1L))
                .thenReturn(userDataMono);
        Assertions.assertEquals(userDataMono, userDataRepo.findById(1L));
    }

    @Test
    void findUserById() {
        Mockito.when(userDataRepo.findById(1L))
                .thenReturn(userDataMono);
        Assertions.assertEquals(userDataMono, userDataRepo.findById(1L));
    }

    @Test
    void saveUser() {
        Mockito.when(userDataRepo.save(u))
                .thenReturn(userDataMono);
        Assertions.assertEquals(userDataMono, userDataRepo.save(u));
    }

}