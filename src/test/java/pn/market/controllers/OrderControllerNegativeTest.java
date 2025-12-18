package pn.market.controllers;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(OrderController.class)
class OrderControllerNegativeTest {
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
        mvc.perform(MockMvcRequestBuilders.get("/ords"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getOrderById() throws Exception {
        when(orderService.getById(1L)).thenReturn(Optional.ofNullable(order));
        assertFalse(orderService.getById(1L).isEmpty());
        mvc.perform(MockMvcRequestBuilders.get("/ordes/1")
                        .param("id", String.valueOf(1))
                        .param("newOrder", "true"))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void buyOrder() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/orders/buyyy/3")
                        .param("id", String.valueOf(1)))
                .andExpect(status().is4xxClientError());
    }

 */
}