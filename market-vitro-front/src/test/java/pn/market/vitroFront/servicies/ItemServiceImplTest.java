package pn.market.vitroFront.servicies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.data.UserData;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static pn.market.vitroFront.config.AuthPaths.VITRO_ITEM_API;
import static pn.market.vitroFront.config.AuthPaths.VITRO_USERS_API;

class ItemServiceImplTest {

    @MockitoBean
    private WebClient webClient;

    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    private WebClient.RequestHeadersSpec requestHeadersMock;
    private WebClient.RequestBodySpec requestBodyMock;
    private WebClient.ResponseSpec mockResponse;

    private String auth2Host;
    private Flux<Item> itemFlux;

    @BeforeEach
    void init() {
        auth2Host = "http://localhost:8521";

        requestBodyUriMock = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        requestHeadersMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        requestBodyMock = Mockito.mock(WebClient.RequestBodySpec.class);
        mockResponse = Mockito.mock(WebClient.ResponseSpec.class);

        itemFlux=Flux.empty();
    }
    @Test
    void itemOfUser() {

        Mockito.when(requestBodyUriMock.uri(
                        ArgumentMatchers.matches(auth2Host +
                                VITRO_ITEM_API + "/get-cart-of-user/2")))
                .thenReturn(requestBodyMock);

        Mockito.when(mockResponse.bodyToFlux(Item.class))
                .thenReturn(itemFlux);
        Assertions.assertEquals(
                mockResponse.bodyToFlux(Item.class).collectList().block(),
               itemFlux.collectList().block());
    }

    @Test
    void getTotalOfSum() {
        Mockito.when(requestBodyUriMock.uri(
                        ArgumentMatchers.matches(auth2Host +
                                VITRO_ITEM_API + "/get-total-sum-cart-of-user/2")))
                .thenReturn(requestBodyMock);

        Mockito.when(mockResponse.bodyToMono(Long.class))
                .thenReturn(Mono.just(1L));
        Assertions.assertEquals(
                mockResponse.bodyToMono(Long.class).block(), 1L);
    }
}