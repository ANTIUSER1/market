package pn.market.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.entities.Cart;
import pn.market.entities.Item;
import pn.market.repo.CartRepo;
import pn.market.repo.ItemRepo;

@SpringBootTest
class CartServiceImplNegativeTest {

    @MockitoBean
    private CartRepo cartRepo;
    @MockitoBean
    private ItemRepo itemRepo;
    @MockitoBean
    private CartServiceImpl cartService;

    private Cart cart;
    private Item item;

    @BeforeEach
    void init() {
        cart = new Cart();
        item = new Item();
        cart.setId(1L);
    }

//    @Test
//    void getById() {
//        when(cartRepo.findById(1L)).thenReturn(Optional.of(cart));
//        when(cartService.getById(1L)).thenReturn(Optional.of(cart));
//        assertTrue(cartService.getById(1L).isPresent());
//        assertNotEquals(cart.getId(), cartService.getById(1L).get().getId() + 1L);
//    }
//
//    @Test
//    void findAllAndPaging() {
//        when(itemRepo.findAll()).thenReturn(new ArrayList<>());
//        Pageable pageable = PageRequest.of(0, 5);
//        Page<Cart> page = Page.empty(pageable);
//        when(cartService.findAllAndPaging(pageable)).thenReturn(page);
//        assertNotEquals(2, cartService.findAllAndPaging(pageable).getTotalPages());
//    }

//    @Test
//    void getLast() {
//        when(cartRepo.findMaxId()).thenReturn(100L);
//        when(cartService.getLast()).thenReturn(Optional.ofNullable(cart));
//        assertFalse(cartService.getLast().isEmpty());
//    }
//
//    @Test
//    void plusItem() {
//        when(itemRepo.findById(1L)).thenReturn(Optional.of(item));
//        when(cartService.plusItem(1L)).thenReturn(cart);
//        assertNull(cartService.plusItem(5L));
//    }

//    @Test
//    void minusItem() {
//        when(itemRepo.findById(1L)).thenReturn(Optional.of(item));
//        when(cartService.minusItem(1L)).thenReturn(cart);
//        assertNull(cartService.minusItem(5L));
//    }
}