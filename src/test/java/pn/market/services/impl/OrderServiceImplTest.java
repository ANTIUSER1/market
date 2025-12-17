package pn.market.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pn.market.entities.Item;
import pn.market.entities.Order;
import pn.market.repo.ItemRepo;
import pn.market.repo.OrderRepo;

@SpringBootTest
class OrderServiceImplTest {


    @Mock
    private OrderRepo orderRepo;
    @Mock
    private ItemRepo itemRepo;
    @Mock
    private OrderServiceImpl orderService;

    private Order order;
    private Item item;


    @BeforeEach
    void init() {
        order = new Order();
        item = new Item();
        order.setId(1L);
    }

/*
    @Test
    void getById() {
        when(orderRepo.findById(1L)).thenReturn(Optional.of(order));
        when(orderService.getById(1L)).thenReturn(Optional.of(order));
        assertTrue(orderService.getById(1L).isPresent());
        assertNotEquals(order.getId(), orderService.getById(1L).get().getId() + 1L);
    }

    @Test
    void findAllAndPaging() {
        when(itemRepo.findAll()).thenReturn(new ArrayList<>());
        Pageable pageable = PageRequest.of(0, 5);
        Page<Order> page = Page.empty(pageable);
        when(orderService.findAllAndPaging(pageable)).thenReturn(page);
        assertEquals(0, orderService.findAllAndPaging(pageable).getTotalPages());
    }

    @Test
    void findAllOrders() {
        when(orderRepo.findAll()).thenReturn(new ArrayList<>());
        when(orderService.findAllOrders()).thenReturn(new ArrayList<>());
        assertTrue(orderService.findAllOrders().isEmpty());
        assertEquals(0, orderService.findAllOrders().size());
    }

 */


}