package pn.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.services.PaymentSupplierImpl;
import pn.market.services.impl.OrderServiceImpl;
import pn.market.services.impl.PaymentsServiceImpl;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentsServiceImpl paymentService;
    @Autowired
    private PaymentSupplierImpl  paymentSupplier;
     @Autowired
    RedisTemplate<String, String> redisTemplate;


    @GetMapping
    public Mono<String> getPayment() {
        return
                paymentService.sendPaymentInfo(
                PaymentsServiceImpl.PAYMENT_KEY_NAME+":p",
                ()->   paymentSupplier.get()
        );
    }
}
