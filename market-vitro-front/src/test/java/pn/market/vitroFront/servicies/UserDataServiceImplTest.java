package pn.market.vitroFront.servicies;

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

    private String auth2Host;

    @BeforeEach
    void init() {
        auth2Host = "http://localhost:8521";
    }

    @Test
    void findUser() {
        UserData ud = new UserData();
        ud.setUsername("a");

        var mockUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        var mockHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec mockResponseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        //Mockito.when(webClient.get()).thenReturn(mockUriSpec);

        Mockito.when(mockUriSpec.uri(
                        ArgumentMatchers.matches(auth2Host + VITRO_USERS_API + "/ud/b")))
                .thenReturn(mockHeadersSpec);

//    var g=    Mockito.when(mockUriSpec.uri(auth2Host + VITRO_USERS_API + "/ud/a")
//                .retrieve()
//                .bodyToMono(Mono.just(ud))
//                .thenReturn(mockHeadersSpec);
////        Mockito.when(mockUriSpec.uri(ArgumentMatchers.anyString())).thenReturn(mockHeadersSpec);
        //  Mockito.when(mockHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        Mockito.when(mockResponseSpec.bodyToMono(UserData.class))
                .thenReturn(Mono.just(ud));


    }

    @Test
    void userEntSt() {
        UserData ud = new UserData();
        ud.setUsername("a");
        WebClient.ResponseSpec mockResponseSpec = Mockito.mock(WebClient.ResponseSpec.class);
        Mockito.when(mockResponseSpec.bodyToMono(UserData.class))
                .thenReturn(Mono.just(ud));


    }

    @Test
    void addUsersSet() {
    }
}