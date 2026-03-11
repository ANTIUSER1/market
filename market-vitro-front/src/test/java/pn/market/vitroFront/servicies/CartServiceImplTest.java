package pn.market.vitroFront.servicies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.data.UserData;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Mono;

import static pn.market.vitroFront.config.AuthPaths.VITRO_ITEM_API;
import static pn.market.vitroFront.config.AuthPaths.VITRO_USERS_API;

@SpringBootTest
class CartServiceImplTest {

    @MockitoBean
    private WebClient webClient;

    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    private WebClient.RequestHeadersSpec requestHeadersMock;
    private WebClient.RequestBodySpec requestBodyMock;
    private WebClient.ResponseSpec mockResponse;

    private String auth2Host;

    private Mono<Item> itemMono;

    @BeforeEach
    void init() {
        Item i = new Item();
        i.setId(1L);
        itemMono = Mono.just(i);

        auth2Host = "http://localhost:8521";

        requestBodyUriMock = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        requestHeadersMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        requestBodyMock = Mockito.mock(WebClient.RequestBodySpec.class);
        mockResponse = Mockito.mock(WebClient.ResponseSpec.class);
    }

    @Test
    void placeItemToCartOfUserNew() {

        Mockito.when(requestBodyUriMock.uri(
                        ArgumentMatchers.matches(auth2Host +VITRO_ITEM_API + "/2")))
                .thenReturn(requestBodyMock);

        Mockito.when(mockResponse.bodyToMono(Item.class))
                .thenReturn( itemMono);
        Assertions.assertEquals(
                mockResponse.bodyToMono(Item.class).block().getId(),
             itemMono.block().getId());
        Assertions.assertEquals(1L,
                mockResponse.bodyToMono(Item.class).block().getId()  );
    }

    @Test
    void placeItemToCartOfUserPlus() {

        Mockito.when(requestBodyUriMock.uri(
                        ArgumentMatchers.matches(auth2Host +VITRO_ITEM_API + "/create/2/2")))
                .thenReturn(requestBodyMock);

        Mockito.when(mockResponse.bodyToMono(Item.class))
                .thenReturn( itemMono);
        Assertions.assertEquals(
                mockResponse.bodyToMono(Item.class).block().getId(),
                itemMono.block().getId());
        Assertions.assertEquals(1L,
                mockResponse.bodyToMono(Item.class).block().getId()  );
    } 

    @Test
    void placeItemToCartOfUserMinus() {

        Mockito.when(requestBodyUriMock.uri(
                        ArgumentMatchers.matches(auth2Host +VITRO_ITEM_API + "/remove/2/2")))
                .thenReturn(requestBodyMock);

        Mockito.when(mockResponse.bodyToMono(Item.class))
                .thenReturn( itemMono);
        Assertions.assertEquals(
                mockResponse.bodyToMono(Item.class).block().getId(),
                itemMono.block().getId());
        Assertions.assertEquals(1L,
                mockResponse.bodyToMono(Item.class).block().getId()  );
    }

    @Test
    void itemById() {
        Mockito.when(requestBodyUriMock.uri(
                        ArgumentMatchers.matches(auth2Host +VITRO_ITEM_API + "/2")))
                .thenReturn(requestBodyMock);

        Mockito.when(mockResponse.bodyToMono(Item.class))
                .thenReturn( itemMono);
        Assertions.assertEquals(
                mockResponse.bodyToMono(Item.class).block().getId(),
                itemMono.block().getId());
        Assertions.assertEquals(1L,
                mockResponse.bodyToMono(Item.class).block().getId()  );
    }

}