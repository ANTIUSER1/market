package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Service
public class PaymentSupplierImpl implements Supplier<Mono<String>> {

    @Value("${order-key}")
    private String orderKey;

    @Autowired
    private OrderServiceImpl orderService;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    private Long orderId;
    private Long userId;

//    @Override
//    public Mono<String> get() {
//        return null;
//    }

    public PaymentSupplierImpl setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public PaymentSupplierImpl setUserId(Long userId) {
        this.userId = userId;
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
                                            .set(orderKey,
                                                    userId + ";" + orderId + ";" + order.totalSumm()
                                            );
                                    return order.toString();
                                }
                        );
        return result;
    }

}
