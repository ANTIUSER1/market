package pn.market.controllers;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
/*
    @MockitoBean
    private OrderServiceImpl orderService;

    @Autowired
    private MockMvc mvc;

    private Order order;

    @BeforeEach
    void init() {
        order = new Order();
        order.setId(1L);
    }

    @Test
    void asdOrders() throws Exception {
        when(orderService.findAllOrders()).thenReturn(new ArrayList<>());
        mvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(status().isOk());
    }

    @Test
    void getOrderById() throws Exception {
        when(orderService.getById(1L)).thenReturn(Optional.ofNullable(order));
        assertTrue(orderService.getById(1L).isPresent());
        mvc.perform(MockMvcRequestBuilders.get("/orders/1")
                        .param("id", String.valueOf(1))
                        .param("newOrder", "true"))
                .andExpect(status().isOk());

    }

    @Test
    void buyOrder() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/orders/buy/3")
                        .param("id", String.valueOf(1)))
                .andExpect(status().is3xxRedirection());
    }

 */
}