package pn.market.services.impl;

import lombok.Getter;
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

@Getter
private Long orderId;

    @Override
    public Mono<String> get() {
        if (orderId == null) {orderId=1L;}
        Mono<String> result =
                orderService.getById(200L)
                        .map(order -> {
                                    redisTemplate.opsForValue()
                                            .set(orderKey,
                                                    orderId+";"+   order.totalSumm()
                                            );
                                    return order.toString();
                                }
                        );
        return result;
    }
}
