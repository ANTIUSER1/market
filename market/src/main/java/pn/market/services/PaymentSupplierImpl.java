package pn.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.services.impl.OrderServiceImpl;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;
@Service
public class PaymentSupplierImpl implements Supplier<Mono<String> >{

    @Autowired
    private OrderServiceImpl orderService;

    @Override
    public Mono<String> get() {
        Mono<String> result =
        orderService.getById(200L)
                .map(order -> order.toString()
                );

        return result;
    }
}
