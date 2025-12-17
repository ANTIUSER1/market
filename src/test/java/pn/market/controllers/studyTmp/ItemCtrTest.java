package pn.market.controllers.studyTmp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import pn.market.controllers.studyTestTMP.ItmCtrl;

@WebFluxTest({ItmCtrl.class})
public class ItemCtrTest {

    @Autowired
    private WebTestClient webTestClient;

//    @MockitoBean
//private ItemServiceImpl itemService;


    @Test
    void testShowCreateUserForm() {
        webTestClient.get()
                .uri("/o")
                //  .attribute()
                .exchange()
                .expectStatus().isOk();
        //   .expectHeader().contentType(MediaType.TEXT_HTML)
//                .expectBody(String.class).consumeWith(response -> {
//                    String body = response.getResponseBody();
//                    assertNotNull(body);
//                    assertTrue(body.contains("<form")); // Проверяем, что страница содержит форму
//                });
    }
}
