package pn.market.vitroFront.servicies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Mono;

@SpringBootTest
class CartServiceImplTest {

    @MockitoBean
    private WebClient webClient;
    private Mono<Item> itemMono;

    @BeforeEach
    void init() {
        Item i = new Item();
        i.setId(1L);
        itemMono = Mono.just(i);
    }

    @Test
    void placeItemToCartOfUser() {
        Assertions.assertEquals(1L, itemMono.blockOptional().get().getId());
    }

    @Test
    void itemById() {
    }

}