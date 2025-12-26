package pn.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import pn.market.services.impl.OrderServiceImpl;
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


    @Override
    public Mono<String> get() {
        Mono<String> result =
                orderService.getById(200L)
                        .map(order -> {
                                    redisTemplate.opsForValue()
                                            .set(orderKey, order.totalSumm() + "");
                                    return order.toString();
                                }
                        );
        return result;
    }
}
