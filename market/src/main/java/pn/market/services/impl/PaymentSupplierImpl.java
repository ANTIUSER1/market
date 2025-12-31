package pn.market.services.impl;

import lombok.Getter;
import lombok.Setter;
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

    @Setter
    private Long orderId;
    @Setter
    private Long userId;

    @Override
    public Mono<String> get() {
        System.out.println("PaymentSupplierImpl.get");
        System.out.println("PaymentSupplierImpl orderId  "+orderId);
        System.out.println("PaymentSupplierImpl userId  "+userId);
        if (orderId == null || userId == null) {
          return Mono.empty();
        }

        Mono<String> result =
                orderService.getById(orderId)
                        .map(order -> {
                                    redisTemplate.opsForValue()
                                            .set(orderKey,
                                                  userId+";"+  orderId + ";" + order.totalSumm()
                                            );
                                    return order.toString();
                                }
                        ) ;
        return result;
    }
}
