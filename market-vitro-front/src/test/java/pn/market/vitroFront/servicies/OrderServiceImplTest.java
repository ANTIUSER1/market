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
import pn.market.market_entities.forWEB.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static pn.market.vitroFront.config.AuthPaths.VITRO_PAYMENT_API;
import static pn.market.vitroFront.config.AuthPaths.VITRO_USERS_API;

@SpringBootTest
class OrderServiceImplTest {

    @MockitoBean
    private WebClient webClient;

    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    private WebClient.RequestHeadersSpec requestHeadersMock;
    private WebClient.RequestBodySpec requestBodyMock;
    private WebClient.ResponseSpec mockResponse;

    private String auth2Host;
   private Order order;
   private Flux<Order> orderFlux;

    @BeforeEach
    void init() {
        auth2Host = "http://localhost:8521";
        order= new Order(1L);
        orderFlux=Flux.empty();

        requestBodyUriMock = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        requestHeadersMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        requestBodyMock = Mockito.mock(WebClient.RequestBodySpec.class);
        mockResponse = Mockito.mock(WebClient.ResponseSpec.class);
    }

    @Test
    void getById() {
        Mockito.when(requestBodyUriMock.uri(
                        ArgumentMatchers.matches(auth2Host +
                                VITRO_PAYMENT_API + "/1")))
                .thenReturn(requestBodyMock);

        Mockito.when(mockResponse.bodyToMono(Order.class))
                .thenReturn(Mono.just(order));
        Assertions.assertEquals(
                mockResponse.bodyToMono(Order.class).block().getId() ,
                order.getId());
    }

    @Test
    void findAll() {
        Mockito.when(requestBodyUriMock.uri(
                        ArgumentMatchers.matches(auth2Host +
                                VITRO_PAYMENT_API )))
                .thenReturn(requestBodyMock);

        Mockito.when(mockResponse.bodyToFlux(Order.class))
                .thenReturn(orderFlux);
        Assertions.assertEquals(
                mockResponse.bodyToFlux(Order.class).collectList().block() ,
                orderFlux.collectList().block());
    }

    @Test
    void getPaymentInfoFromRemote() {
        Mockito.when(requestBodyUriMock.uri(
                        ArgumentMatchers.matches(auth2Host +
                                VITRO_PAYMENT_API + "/remove-money-for-order" )))
                .thenReturn(requestBodyMock);

        Mockito.when(mockResponse.bodyToMono(String.class))
                .thenReturn( Mono.just("uu"));
        Assertions.assertEquals(
                mockResponse.bodyToMono(String.class).block() ,
                "uu");

    }


}