package pn.payment.rest;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import pn.payment.services.PersonService;
import reactor.core.publisher.Mono;

@WebFluxTest({PersonRest.class})
@Import({PersonService.class})
class PersonRestTest {

    @MockitoBean
    private PersonService personService;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void removeMoneyFromFirstSuccsessTest() {
        Mockito.when(personService.removeMoneySuccess(1L, 10L)).thenReturn(Mono.just(true));
        webTestClient.get().uri("/remove-money-from-first-success/10")
                .exchange()
                .expectStatus().isOk();

    }
}