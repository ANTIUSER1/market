package pn.market.controllers;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import pn.market.services.impl.CartServiceImpl;


@WebMvcTest({CartController.class})
@Import({CartServiceImpl.class})
class CartControllerTest {
/*
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private CartServiceImpl cartService;

    private Cart cart;

    private List<Item> items;

    @BeforeEach
    void init() {
        items = new ArrayList<>();
        Item item = new Item();
        item.setPrice(100L);
        item.setId(100L);
        item.setTitle("test");
        item.setDescription("test-d");
        item.setImgPath("p");
        item.setCount(100);
        items.add(item);

        cart = new Cart(1L);

    }


    @Test
    void addItem() throws Exception {
        when(cartService.plusItem(1L)).thenReturn(cart);
        mvc.perform(post("/cart/items")
                        .param("itemId", "1")
                        .param("action", ActionType.MINUS.name()))
                .andExpect(status().isOk());
    }

 */
}