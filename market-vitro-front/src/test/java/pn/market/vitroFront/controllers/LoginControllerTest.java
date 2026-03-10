package pn.market.vitroFront.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.AutoConfigureWebFlux;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import pn.market.vitroFront.config.SecurityConfig;
import pn.market.vitroFront.config.WebClientConfig;
import pn.market.vitroFront.servicies.*;

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest({ItemController.class,
        CartServiceImpl.class,
        ModelService.class,
        LoginService.class,
        UserDataServiceImpl.class
})
@AutoConfigureWebFlux
@Import({
        CartServiceImpl.class,
        ItemServiceImpl.class,
        SecurityConfig.class,
        LoginController.class,
        WebClientConfig.class
})
class LoginControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void login() {
        webTestClient.get()
                .uri("/login")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .consumeWith(response -> {
                    String body = response.getResponseBody();
                    assertNotNull(body);
                    assertTrue(body.contains("<html")); // Проверяем, что страница содержит форму
                });

    }
}