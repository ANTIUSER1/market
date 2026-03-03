package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pn.market.market_entities.Paging;
import pn.market.market_entities.TService;
import pn.market.market_entities.forWEB.Item;
import pn.market.market_entities.forWEB.Order;
import pn.market.market_entities.forWEB.OrderItems;
import pn.market.vitroBack.repo.OrderRepo;
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
    private ItemServiceImpl itemService;

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

    public Mono<Order> create(Long userId, Long itemId) {
        Order order = new Order();
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

    public Mono<Order> showOderOfUser(Long userId, Long orderId) {
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
        this.getByUserId(userId)
                .map(o -> {
                    System.out.println("    ORDER TO PAY " + o.getId());
                    System.out.println("            CONTAINS ITEM ");
                    for (Item i : o.getItems()) {
                        System.out.println(" ITEM: " + i);
                    }
                    System.out.println("   TOTAL SUM: " + o.getTotalSumm());

                    return o;
                }).subscribe();
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
    }

    public Mono<Order> getByUserId(Long uid) {
        Mono<Order> orderMono = orderRepo.findByUserId(uid);
        Flux<Item> itemFlux = itemService.getItemsOfUser(uid);
        orderMono = Mono.zip(orderMono, itemFlux.collectList())
                .map(t -> {
                    Order o = t.getT1();
                    List<Item> itemList = t.getT2();
                    o.getItems().addAll(itemList);

                    return o;
                });
        return orderMono;
    }
}











