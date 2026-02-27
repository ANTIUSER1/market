package pn.market.vitroFront.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Service
public class PaymentSupplierImpl implements Supplier<Mono<String>> {

    @Value("${payment.data.order-key}")
    private String orderKey;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private Long orderId;

    private Long userId;

    private LocalDateTime localTime;

    public PaymentSupplierImpl setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public PaymentSupplierImpl setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public PaymentSupplierImpl setLocalTime(LocalDateTime localTime) {
        if (this.localTime == null)
            this.localTime = localTime;
        return this;
    }

    @Override
    public Mono<String> get() {
        if (orderId == null || userId == null) {
            return Mono.empty();
        }
        Mono<String> result =
                orderService.getById(orderId)
                        .map(order -> {
                                    redisTemplate.opsForValue()
                                            .set(
                                                    orderKey,
                                                    userId + ";" + orderId + ";" + order.getTotalSumm() + ";" + localTime
                                            );
                                    return order.toString();
                                }
                        );
        return result;
    }

}
