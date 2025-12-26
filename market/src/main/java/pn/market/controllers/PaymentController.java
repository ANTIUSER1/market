package pn.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.services.PaymentSupplierImpl;
import pn.market.services.impl.PaymentsServiceImpl;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    private PaymentsServiceImpl paymentService;
    @Autowired
    private PaymentSupplierImpl paymentSupplier;

    @GetMapping
    public Mono<String> getPayment() {
        System.out.println("get-mapping at " + (System.currentTimeMillis() / 1000));

        return paymentService.sendPaymentInfo(
                "PAYMENT_",
                () -> paymentSupplier.get()
        );
    }
}
