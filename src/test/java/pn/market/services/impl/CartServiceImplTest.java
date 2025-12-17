package pn.market.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pn.market.entities.Cart;
import pn.market.entities.Item;
import pn.market.repo.CartRepo;
import pn.market.repo.ItemRepo;

@SpringBootTest
class CartServiceImplTest {

    @Mock
    private CartRepo cartRepo;
    @Mock
    private ItemRepo itemRepo;
    @Mock
    private CartServiceImpl cartService;

    private Cart cart;
    private Item item;

    @BeforeEach
    void init() {
        cart = new Cart();
        item = new Item();
        cart.setId(1L);
    }
/*
    @Test
    void getById() {
        when(cartRepo.findById(1L)).thenReturn(Optional.of(cart));
        when(cartService.getById(1L)).thenReturn(Optional.of(cart));
        assertTrue(cartService.getById(1L).isPresent());
        assertEquals(cart.getId(), cartService.getById(1L).get().getId());
    }

    @Test
    void findAllAndPaging() {
        when(itemRepo.findAll()).thenReturn(new ArrayList<>());
        Pageable pageable = PageRequest.of(0, 5);
        Page<Cart> page = Page.empty(pageable);
        when(cartService.findAllAndPaging(pageable)).thenReturn(page);
        assertEquals(0, cartService.findAllAndPaging(pageable).getTotalPages());
    }

    @Test
    void getLast() {
        when(cartRepo.findMaxId()).thenReturn(100L);
        when(cartService.getLast()).thenReturn(Optional.ofNullable(cart));
        assertTrue(cartService.getLast().isPresent());
    }

    @Test
    void plusItem() {
        when(itemRepo.findById(1L)).thenReturn(Optional.of(item));
        when(cartService.plusItem(1L)).thenReturn(cart);
        assertNotNull(cartService.plusItem(1L));
    }

    @Test
    void minusItem() {
        when(itemRepo.findById(1L)).thenReturn(Optional.of(item));
        when(cartService.minusItem(1L)).thenReturn(cart);
        assertNotNull(cartService.minusItem(1L));
    }

 */
}