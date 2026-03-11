package pn.market.vitroFront.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Service
public class PaymentsServiceImpl {

    public static final String PAYMENT_KEY_NAME = "paymentInfo";//= paymentKey;

    @Autowired
    public PaymentSupplierImpl paymentSupplier;

    @Cacheable(
            value = PAYMENT_KEY_NAME,               // Имя кеша и первая часть ключа
            key = "#keyName"   // Вторая часть ключа (берётся по имени из аргумента)
    )
    public Mono<String> sendPaymentInfo(String keyName, Supplier<Mono<String>> paymentInfo) {
        Mono<String> res = paymentInfo.get()
                .map(s -> {
                    return s;
                });
        res.subscribe();
        return res;
    }

    public void configurePayments(Long userId, Long orderId) {
        paymentSupplier = paymentSupplier.setUserId(userId);
        paymentSupplier = paymentSupplier.setOrderId(orderId);
        paymentSupplier = paymentSupplier.setLocalTime(LocalDateTime.now());
        this.sendPaymentInfo(
                PaymentsServiceImpl.PAYMENT_KEY_NAME,
                () -> paymentSupplier.get()
        );
    }

}
