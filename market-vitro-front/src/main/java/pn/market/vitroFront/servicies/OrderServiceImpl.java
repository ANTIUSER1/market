package pn.market.vitroFront.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.Paging;
import pn.market.market_entities.TService;
import pn.market.market_entities.forWEB.Item;
import pn.market.market_entities.forWEB.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static pn.market.vitroFront.config.AuthPaths.VITRO_ORDER_API;

@Service
public class OrderServiceImpl implements TService<Order> {


    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private WebClient webClient;

    @Override
    public Flux<Order> findAll() {
        return webClient.get()
                .uri(VITRO_ORDER_API)
                .retrieve().bodyToFlux(Order.class);
    }

    @Override
    public Mono<Order> getById(Long id) {
        Mono<Order> order = webClient.get()
                .uri(VITRO_ORDER_API + "/" + id)
                .retrieve().bodyToMono(Order.class);

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
        return webClient.put()
                .uri(VITRO_ORDER_API)
                .bodyValue(order)
                .retrieve().bodyToMono(Order.class);

    }

    public void buyOrder(long orderId) {
        getPaymentInfoFromRemote(orderId)
                .map(s -> {
                    System.out.println("    ----SSSSSSS--- " + s);
                    System.out.println("    ----SSSSSSS--- " + s);
                    System.out.println("    ----SSSSSSS--- " + s);
                    System.out.println("    ----SSSSSSS--- " + s);
                    return s;
                })
                .map(s -> "OK").subscribe();


        System.out.println("     BUY ORDER " + orderId);
        itemService.removeFromOrder(orderId);
    }

    private Mono<String> getPaymentInfoFromRemote(long orderId) {
        System.out.println("     BUY ORDER " + orderId);
        return webClient.get().uri("/users/remove-money-for-order")
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class));
    }

    public Mono<List<Order>> addItemsToAll() {
        Flux<Order> orderFlux = findAll();
        Mono<List<Order>> orderMonList = orderFlux.collectList();

        Flux<Item> itemFlux = orderFlux.map(o -> {
            Flux<Item> itm = itemService.getItemsByOrderId(o.getId());
            return itm;
        }).flatMap(i -> i);
        Mono<List<Item>> itemMonList = itemFlux.collectList();

        orderMonList = Mono.zip(orderFlux.collectList(), itemFlux.collectList())
                .map(t -> {
                    List<Order> ol = t.getT1();
                    List<Item> il = t.getT2();
                    for (Order o : ol) {
                        for (Item i : il)
                            if (i.getOrderId() == o.getId()) {
                                o.addItem(i);
                            }
                    }

                    System.out.println("        OOOOO!  " + ol);

                    return Mono.just(ol);

                }).flatMap(oo -> oo);

        return orderMonList;
    }
}
