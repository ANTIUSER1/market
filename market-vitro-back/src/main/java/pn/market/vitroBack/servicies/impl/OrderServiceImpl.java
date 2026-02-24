package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.Paging;
import pn.market.market_entities.TService;
import pn.market.market_entities.forWEB.Item;
import pn.market.market_entities.forWEB.Order;
import pn.market.market_entities.forWEB.OrderItems;
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
    private OrderControlUtilityService orderControlUtilityService;
    @Autowired
    private OrderUtilityService orderUtilityService;

    @Autowired
    private UserEntityServiceImpl userService;

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
        // order.setUserId(userId);
        System.out.println("   saveWithUser   NEW ORDER: ITEM  " + itemId + "   USER " + userId);
        Mono<Order> orderMono = save(order)
                .map(o -> {
                    System.out.println("----++OOO +++ " + o);
                    itemService.findById(itemId)
                            .map(i -> {
                                System.out.println(" ++++++++ ITEM  " + itemId + "   UPDATE ");

                                System.out.println("  OOOO " + o);
                                itemService.save(i).subscribe();
                                return o;
                            }).subscribe();
                    return o;
                });
        return orderMono;
    }

    public Mono<Order> updateExisting(Long userId, Long itemId) {
        System.out.println("----------updateExisting: IteM:" + itemId + "   ::::  UserM:: " + userId);
        Mono<List<Order>> udmonoList = orderRepo.findByUserId(userId).collectList();
        Mono<Order> orderMono = udmonoList.map(
                ud -> {
                    Order o = null;
                    if (ud.size() > 0) {
                        o = ud.get(0);
                    } else {
                        o = new Order();
                    }
                    System.out.println("--------*****ORDER:::" + o);

                    return o;
                }
        );
        Mono<Item> itemMono = itemService.findById(itemId);

        orderMono = Mono.zip(orderMono, itemMono)
                .map(t -> {
                    System.out.println("  EXISTING--OOOO  UUU");
                    Order o = t.getT1();
                    Item i = t.getT2();
                    System.out.println("  EXISTING--OOOO " + o);

                    System.out.println("  START ITEM-UPDATE:  " + i);
                    System.out.println("   FINISH ITEM-UPDATE:  " + i);
                    System.out.println("   SAVE   ITEM-UPDATE:  " + i);
                    itemService.save(i).subscribe();
                    return o;
                });
        return orderMono;
    }

    public Mono<Order> showOderOfUser(Long userId, Long orderId) {
        System.out.println("   --   SHOW ORDER OF USER: --: ORDER: " + orderId + "  USER:  " + userId);
        Mono<Order> orderMono = orderRepo.findById(orderId);
        Mono<List<Item>> iListMono = itemService.getItemsByOrderId(orderId).collectList();
        orderMono = Mono.zip(orderMono, iListMono)
                .map(t -> {
                    Order o = t.getT1();
                    List<Item> itemList = t.getT2();
                    o.getItems().addAll(itemList);
                    return o;
                });
        return orderMono;
    }

    public void buyOrderOfUser(Long userId, Long orderId) {
       /*
        System.out.println("   -----PROCESS    BUY ORDER OF USER: --: ORDER: " + orderId + "  USER:  " + userId);
        itemService.getItemsByOrderId(orderId)
                .map(i -> {
                    itemService.save(i)
                            .subscribe();
                    return i;
                }).subscribe(
                        ii -> {
                            orderRepo.findById(orderId)
                                    .map(o -> {
                                        System.out.println("    EQUALS TEST " + (o.getUserId() == userId));
                                        if (o.getUserId() == userId) {
                                            try {
                                                orderRepo.delete(o).subscribe();
                                            } catch (Error e) {

                                            }
                                        }
                                        return new Order();
                                    }).subscribe();

                        }
                );
        */
    }


    public Mono<Order> createOrUseCartOfUser(Long userId, Long itemId) {

        Mono<Order> orderMono = orderUtilityService.createOrTestExistOrder(userId);
        Mono<OrderItems> orderItemsMono = orderMono.map(o -> {
            return orderUtilityService.createAndSaveOrderItems(o.getId(), itemId)
                    .map(cci -> {
                        return cci;
                    });
        }).flatMap(cv -> cv);

        Mono<Flux<OrderItems>> orderItemsFlux0 = orderItemsMono.map(oi -> {
            return oi.getOrderId();
        }).map(n -> {
            return orderControlUtilityService.findOrderItemsByOrderId(n);
        });

        orderMono = Mono.zip(orderMono, orderItemsFlux0)
                .map(t -> {
                    Mono<Order> cartM = Mono.just(t.getT1());
                    Flux<OrderItems> cartItemsFlux = t.getT2();
                    Flux<Item> itemFlux = orderUtilityService.orderItemsFluxToItemFlux(cartItemsFlux, itemId);
                    cartM = Mono.zip(cartM, itemFlux.collectList())
                            .map(t1 -> {
                                Order o1 = t1.getT1();
                                List<Item> itemsList = t1.getT2();
                                o1.addItemsSet(itemsList);
                                return o1;
                            });
                    return cartM;
                }).flatMap(mm -> mm);

        return orderMono;
                /*
                orderControlUtilityService.findUserById(userId)
                .map(u->{
                    Mono<Order> o;
                    if (u.getOrderId()  == null) {
                        System.out.println(" CREATE   NEW CART ");
                       o =orderControlUtilityService.saveOrder(new Order())
                                //cartRepo.save(new Cart())
                                .map(ooo -> {
                                    u.setCartId(ooo.getId());

                                  orderControlUtilityService.saveUser(u).subscribe();
                                    return ooo;
                                });
                    } else {
                     o = orderControlUtilityService.findOrderById(u.getCartId());
                        //cartRepo.findById(u.getCartId());
                    } return o;
                }).flatMap(v->v);

*/


        // return Mono.empty();
    }
}











