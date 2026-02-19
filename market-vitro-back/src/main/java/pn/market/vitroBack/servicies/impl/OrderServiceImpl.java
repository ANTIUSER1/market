package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.Paging;
import pn.market.market_entities.TService;
import pn.market.market_entities.forWEB.Item;
import pn.market.market_entities.forWEB.Order;
import pn.market.vitroBack.repo.OrderRepo;
import pn.market.vitroBack.repo.UserDataRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Service

public class OrderServiceImpl implements TService<Order> {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserDataRepo userDataRepo;

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
            List<Item> i = new ArrayList<>(t.getT2());

            o.setItems(new TreeSet<>(i));
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
        getPaymentInfoFromRemote(orderId)
                .map(s -> "OK").subscribe();
        itemService.removeFromOrder(orderId);
    }

    private Mono<String> getPaymentInfoFromRemote(long orderId) {
        System.out.println("     BUY ORDER " + orderId);
        return webClient.get().uri("/users/remove-money-for-order")
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class));
    }

    public Mono<Order> creare(Long userId, Long itemId) {
        Order order = new Order();
        order.setUserId(userId);
        System.out.println("   saveWithUser   NEW ORDER: ITEM  " + itemId + "   USER " + userId);
        Mono<Order> orderMono = save(order)
                .map(o -> {
                    System.out.println("----++OOO +++ " + o);
                    itemService.findById(itemId)
                            .map(i -> {
                                System.out.println(" ++++++++ ITEM  " + itemId + "   UPDATE ");

                                i.setCartId(null);
                                i.setCount(0);
                                i.setOrderId(o.getId());
                                System.out.println("  OOOO " + o);
                                itemService.save(i).subscribe();
                                return o;
                            }).subscribe();
                    return o;
                });
        return orderMono;
    }

    public Mono<Order> saveExisting(Long userId, Long itemId) {
        Mono<List<Order>> udmonoList = orderRepo.findByUserId(userId).collectList();
        Mono<Order> orderMono = udmonoList.map(
                ud -> {
                    Order o = null;
                    if (ud.size() > 0) {
                        o = ud.get(0);
                    } else {
                        o = new Order();
                    }
                    return o;
                }
        );
        Mono<Item> itemMono = itemService.findById(itemId);

        orderMono = Mono.zip(orderMono, itemMono)
                .map(t -> {
                    Order o = t.getT1();
                    Item i = t.getT2();

                    i.setCartId(null);
                    i.setCount(0);
                    i.setOrderId(o.getId());
                    System.out.println("  EXISTING--OOOO " + o);
                    itemService.save(i).subscribe();
                    return o;
                });
        return orderMono;
    }
}
