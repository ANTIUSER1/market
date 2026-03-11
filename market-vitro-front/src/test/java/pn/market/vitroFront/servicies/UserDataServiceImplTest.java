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
import reactor.core.publisher.Mono;

import static pn.market.vitroFront.config.AuthPaths.VITRO_USERS_API;

@SpringBootTest
class UserDataServiceImplTest {


    @MockitoBean
    private WebClient webClient;

    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    private WebClient.RequestHeadersSpec requestHeadersMock;
    private WebClient.RequestBodySpec requestBodyMock;
    private WebClient.ResponseSpec mockResponse;

    private String auth2Host;


    @BeforeEach
    void init() {
        auth2Host = "http://localhost:8521";

        requestBodyUriMock = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        requestHeadersMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        requestBodyMock = Mockito.mock(WebClient.RequestBodySpec.class);
        mockResponse = Mockito.mock(WebClient.ResponseSpec.class);
    }

    @Test
    void findUser() {
        UserData ud = new UserData();
        ud.setUsername("a");
        Mockito.when(requestBodyUriMock.uri(
                        ArgumentMatchers.matches(auth2Host + VITRO_USERS_API + "/ud/b")))
                .thenReturn(requestBodyMock);

        Mockito.when(mockResponse.bodyToMono(UserData.class))
                .thenReturn(Mono.just(ud));
        Assertions.assertEquals(
                mockResponse.bodyToMono(UserData.class).block().getUsername(),
                ud.getUsername());
    }

}