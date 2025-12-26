package pn.market.services.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pn.market.services.PaymentSupplierImpl;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Service
public class PaymentsServiceImpl {


    @Autowired
    public PaymentSupplierImpl paymentSupplier;
    public static   final String PAYMENT_KEY_NAME ="paymentInfo";//= paymentKey;




    @Cacheable(
            value = PAYMENT_KEY_NAME ,               // Имя кеша и первая часть ключа
            key = "#keyName"   // Вторая часть ключа (берётся по имени из аргумента)
    )
    public Mono<String>  sendPaymentInfo(String keyName,   Supplier<Mono<String>> paymentInfo){
return paymentInfo.get();
    }

}
