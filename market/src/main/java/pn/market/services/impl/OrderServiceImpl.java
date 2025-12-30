package pn.market.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.additional.Paging;
import pn.market.entities.Item;
import pn.market.entities.Order;
import pn.market.repo.OrderRepo;
import pn.market.services.TService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service

public class OrderServiceImpl implements TService<Order> {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ItemServiceImpl itemService;

@Autowired
private WebClient webClient;

    @Override
    public Flux<Order> findAll() {
        return orderRepo.findAll();
    }

    @Override
    public Mono<Order> getById(Long id) {
        Mono<Order> order = orderRepo.findById(id);
        Flux<Item> items = itemService.getItemsByOrderId(id);
        return Mono.zip(order, items.collectList()).map(t -> {
            Order o = t.getT1();
            List<Item> i = t.getT2();

            o.setItems(i);
            return o;
        });
    }

    @Override
    public Mono<Paging> findAllAndPaging(Mono<Pageable> pageable) {
        return Mono.empty();
    }

    public Mono<Order> save(Order order) {
        return orderRepo.save(order);
    }

    public void buyOrder(long orderId) {
//        Mono<String> paymentInfo = getPaymentInfoFromRemote(orderId)
//                .map(s -> "OK");


       System.out.println("     BUY ORDER " + orderId);
        itemService.removeFromOrder(orderId)
                ;
        //orderRepo.deleteById(orderId).subscribe();
    }

    private Mono<String> getPaymentInfoFromRemote(long orderId) {
      return  webClient.get().uri("/remove-money/1/100")
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class));
  }

}
