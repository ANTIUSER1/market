package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private WebClient webClient;

    public  Mono<String> buyOrder(long orderId) {
       return getPaymentInfoFromRemote(orderId)
                .map(s -> "OK") ;
    }

    private Mono<String> getPaymentInfoFromRemote(long orderId) {
        System.out.println("     BUY ORDER " + orderId);
        return webClient.get()
                .uri( "/users/remove-money-for-order")
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class));
    }
}
