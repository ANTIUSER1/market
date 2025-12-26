package pn.market.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pn.market.services.PaymentSupplierImpl;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Service
public class PaymentsServiceImpl {


    public static final String PAYMENT_KEY_NAME = "paymentInfo";//= paymentKey;
    @Autowired
    public PaymentSupplierImpl paymentSupplier;

    @Cacheable(
            value =PAYMENT_KEY_NAME,               // Имя кеша и первая часть ключа
            key = "#keyName"   // Вторая часть ключа (берётся по имени из аргумента)
    )
    public Mono<String> sendPaymentInfo(String keyName, Supplier<Mono<String>> paymentInfo) {

        Mono<String> result= paymentInfo.get()
                .map(s -> {
                    System.out.println("Payment info: " + s);
                    return s;
                });
        return result;
    }

}
