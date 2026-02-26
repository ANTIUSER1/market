package pn.market.vitroFront.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.Paging;
import pn.market.market_entities.TService;
import pn.market.market_entities.forWEB.Item;
import pn.market.market_entities.forWEB.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static pn.market.vitroFront.config.AuthPaths.VITRO_ORDER_API;
import static pn.market.vitroFront.config.AuthPaths.VITRO_PAYMENT_API;

@Service
public class OrderServiceImpl implements TService<Order> {


    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    @Qualifier("PAYMENT")
    private WebClient webPaymentClient;
    @Autowired
    @Qualifier("BACK")
    private WebClient webBackClient;

    @Override
    public Flux<Order> findAll() {
        return webBackClient.get()
                .uri(VITRO_ORDER_API)
                .retrieve().bodyToFlux(Order.class);
    }

    @Override
    public Mono<Order> getById(Long id) {
        Mono<Order> order = webBackClient.get()
                .uri(VITRO_ORDER_API + "/" + id)
                .retrieve().bodyToMono(Order.class);
        Flux<Item> items = itemService.getItemsByOrderId(id);
        return Mono.zip(order, items.collectList()).map(t -> {
            Order o = t.getT1();
            Set<Item> i = new TreeSet<>(t.getT2());
            o.setItems(i);
            return o;
        });
    }

    @Override
    public Mono<Paging> findAllAndPaging(Mono<Pageable> pageable) {
        return Mono.empty();
    }


    private Mono<String> getPaymentInfoFromRemote(long orderId) {
        System.out.println("    +++++++++ BUY ORDER " + orderId);


        System.out.println("REQUEST TO URL  "
                + VITRO_PAYMENT_API + "/remove-money-for-order/" + orderId);
        return webPaymentClient.get().uri(VITRO_PAYMENT_API + "/remove-money-for-order/" + orderId)
                .exchangeToMono(clientResponse -> {
                    System.out.println("   EXCHANGE: " + clientResponse.headers().contentType());
                    return clientResponse.bodyToMono(String.class);
                });
    }

    public Mono<List<Order>> addItemsToAllByUserId(Long userId) {
        Mono<Order> orderMono = webBackClient.get()
                .uri(VITRO_ORDER_API + "/order-by-user-uid/" + userId)
                .retrieve().bodyToMono(Order.class);
        return orderMono.map(o -> {
            List<Order> orders = new ArrayList<>();
            orders.add(o);
            return orders;
        });
    }

    public Mono<Order> showCompleteOrderById(Long orderId) {
        Mono<Order> orderMono = this.getById(orderId)
                .map(od -> {
                    itemService.getItemsByOrderId(od.getId())
                            .subscribe(u -> {
                                od.addItem(u);
                            });
                    return od;
                });

        return orderMono;
    }

    public Mono<Order> showCompleteOrderById(Long orderId, Long itemId) {
        System.out.println("      ITEM_ID " + itemId);
        Mono<Order> orderMono = this.getById(orderId)
                .map(od -> {

                    itemService.getItemsByOrderId(od.getId())
                            .subscribe(u -> {
                                od.addItem(u);
                                System.out.println(u.getId() + "--   OOO  " + od.getItems().size());
                            });
                    return od;
                })
                .map(od -> {
                    System.out.println("   SSB ITEM ID " + itemId);
                    itemService.getById(itemId)
                            .subscribe(u -> {
                                od.addItem(u);
                                System.out.println("--   OOO--000  " + od.getItems().size());
                                System.out.println("   NEW SAVE:   \n" + u);
                                itemService.addOrder(u, orderId);
                            });
                    return od;
                });
        return orderMono;
    }

    public Mono<Order> saveNewCompleteOrderOfUserById(Long userId, Long itemId) {
        return webBackClient.get()
                .uri(VITRO_ORDER_API + "/create/" + userId + "/" + itemId)
                .retrieve().bodyToMono(Order.class);
    }

    public Mono<Order> showOrderOfUserById(Long userId, Long orderId) {
        return webBackClient.get()
                .uri(VITRO_ORDER_API + "/show/" + userId + "/" + orderId)
                .retrieve().bodyToMono(Order.class);

    }

    public void buyOrderOfUser(Long userId, Long orderId) {
        webBackClient.get()
                .uri(VITRO_ORDER_API + "/buy/" + userId + "/" + orderId)
                .retrieve().bodyToMono(Order.class).subscribe();

    }

    public Mono<Order> save(Order order) {
        return webBackClient.get()
                .uri(VITRO_ORDER_API + "/save/" + order.getId())
                .retrieve().bodyToMono(Order.class);

    }

    public Mono<Order> saveNewOrder(Long userId, Long itemId) {
        return webBackClient.get()
                .uri(VITRO_ORDER_API + "/create/" + userId + "/" + itemId)
                .retrieve().bodyToMono(Order.class);

    }

    public Mono<Order> updateOrder(Long userId, Long itemId) {
        return webBackClient.get()
                .uri(VITRO_ORDER_API + "/update/" + userId + "/" + itemId)
                .retrieve().bodyToMono(Order.class);

    }

    public void buyOrder(long orderId) {
        System.out.println("----BUY  :: " + orderId);
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
}
