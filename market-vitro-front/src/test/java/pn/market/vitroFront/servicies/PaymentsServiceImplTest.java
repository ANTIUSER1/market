package pn.market.vitroFront.servicies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Mono;

@SpringBootTest
class PaymentsServiceImplTest {

    @MockitoBean
    public PaymentSupplierImpl paymentSupplier;

    @Test
    void sendPaymentInfo() {
        Mockito.when(paymentSupplier.get())
                .thenReturn(Mono.just("GETT"));
        Assertions.assertEquals(Mono.just("GETT").block(), paymentSupplier.get().block());
    }
}