package pn.payment.rest;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pn.payment.services.UserDataService;
import reactor.core.publisher.Mono;

@WebFluxTest({UserDataRest.class})
@Import({UserDataService.class})
class UserDataRestTest {

    @MockitoBean
    private UserDataService userDataService;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void removeMoneyFromFirstSuccsessTest() {
        Mockito.when(userDataService.removeMoneySuccess(1L, 10L)).thenReturn(Mono.just(true));
        webTestClient.get().uri("/users/remove-money-from-first-success/10")
                .exchange()
                .expectStatus().isOk();

    }


    @Test
    void removeMoneyForOrder() {
        Mockito.when(userDataService.removeMoneyForOrder())
                .thenReturn(Mono.just(true));
        webTestClient.get().uri("/users/remove-money-for-order")
                .exchange()
                .expectStatus().isOk();
    }
}