package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.market_entities.forWEB.Item;
import pn.market.market_entities.forWEB.Order;
import pn.market.market_entities.forWEB.OrderItems;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrderUtilityService {

    @Autowired
    private OrderControlUtilityService orderControlUtilityService;
    @Autowired
    private OrderRemoveUtilityService orderRemoveUtilityService;


    public Flux<Item> orderItemsFluxToItemFlux(Flux<OrderItems> orderItemsFlux, Long itemId) {
        Mono<List<OrderItems>> cartItemsMonoList = orderItemsFlux.collectList();
        Flux<Mono<Item>> fmit = orderItemsFlux.map(ff -> {
            Mono<Item> ii = orderControlUtilityService.findItemById(ff.getItemId());

            ii = Mono.zip(ii, orderItemsFlux.collectList())
                    .map(t -> {
                                Item im = t.getT1();
                                List<OrderItems> orderItemsList = t.getT2();
                                if (!orderItemsList.isEmpty()) {
                                    OrderItems oi = orderItemsList.get(0);
                                    Mono<Long> longMono = orderControlUtilityService.countOfOrderAndItemId(im.getId(), oi.getOrderId());
                                    Mono<Item> itemMono = Mono.zip(Mono.just(im), longMono)
                                            .map(t1 -> {
                                                Item i = t1.getT1();
                                                Long count = t1.getT2();
                                                System.out.println("   ORDER PLUS COUNT VALUE " + count);
                                                i.setCount(count);
                                                orderControlUtilityService.saveItem(i).subscribe();
                                                return i;
                                            });
                                    itemMono.subscribe();
                                }
                                return im;
                            }
                    );
            return ii;
        });
        Flux<Item> result = fmit.flatMap(fk -> fk);
        return result;
    }

    public Mono<Order> createOrTestExistOrder(Long userId) {
        return orderControlUtilityService.findUserById(userId)
                .map(u -> {
                    Mono<Order> o;
                    if (u.getOrderId() == null) {
                        System.out.println(" ---------CREATE   NEW ORDER------- ");
                        o = orderControlUtilityService.saveOrder(new Order())
                                .map(ooo -> {
                                    u.setOrderId(ooo.getId());

                                    orderControlUtilityService.saveUser(u).subscribe();
                                    return ooo;
                                });
                    } else {
                        o = orderControlUtilityService.findOrderById(u.getOrderId());
                    }
                    return o;
                }).flatMap(c -> c);

    }

    public Mono<OrderItems> createAndSaveOrderItems(Long orderId, Long itemId) {
        OrderItems oi = new OrderItems();
        oi.setOrderId(orderId);
        oi.setItemId(itemId);
        return orderControlUtilityService.saveOrderItems(oi);
        //cartItemsRepo.save(ci);
    }


    public Mono<Item> removeFromOrderOfUser(Long userId, Long itemId) {
        System.out.println("           REMOVING ITEM  " + itemId + "  : USER :  " + userId);
        Mono<Order> orderMono = orderControlUtilityService.findUserById(userId)
                .map(u -> {
                    System.out.println("REQUESTED USER:");
                    Mono<Order> c = orderControlUtilityService.findOrderById(u.getCartId())
                            .map(ccc -> {
                                System.out.println("CCCC " + ccc);
                                return ccc;
                            });
                    return c;
                }).flatMap(v -> v);
        Mono<List<OrderItems>> orderItemsListMono = findOrderItemsByUserId(orderMono, userId);
        Mono<Item> itemMono = orderControlUtilityService.findItemById(itemId);

        Mono<Item> result = orderRemoveUtilityService.removeItemFromCartOfUser(orderItemsListMono, itemMono, userId);


        return result;
    }

    private Mono<List<OrderItems>> findOrderItemsByUserId(Mono<Order> orderMono, Long userId) {
        Mono<List<OrderItems>> fci = orderMono.map(o -> {
            Flux<OrderItems> f = orderControlUtilityService.findOrderItemsByOrderId(o.getId());
            return f.collectList();
        }).flatMap(v -> v);
        return fci;
    }


}











