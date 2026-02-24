package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.market_entities.forWEB.Item;
import pn.market.market_entities.forWEB.OrderItems;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderRemoveUtilityService {


    @Autowired
    private OrderControlUtilityService orderControlUtilityService;

    public Mono<Item> removeItemFromCartOfUser(Mono<List<OrderItems>> orderItemsListMono, Mono<Item> itemMono, Long userId) {

        return Mono.zip(orderItemsListMono, itemMono)
                .map(t -> {
                    Item i = t.getT2();
                    System.out.println("***------------REMOVED ITEM " + i);
                    List<OrderItems> orderItemsList = t.getT1();
                    List<OrderItems> orderItemsListTMP = new ArrayList<>();
                    //       System.out.println("      CART-LIST-SIZE -- "+orderItemsList.size());
                    for (OrderItems cii : orderItemsList) {
                        if (i.getId() == cii.getItemId()) {
                            System.out.println("     ITIM     " + i + "    " + cii);
                            orderItemsListTMP.add(cii);
                        }
                    }
                    if (!orderItemsListTMP.isEmpty()) {
                        OrderItems ci = orderItemsListTMP.get(0);
                        System.out.println("REMOVING      " + ci + "   " + ci.getId() + "  /  " + ci.getItemId());
                        orderControlUtilityService.deleteOrderItemsById(ci.getId()).subscribe();
                        System.out.println(" CI TO REMOVR  " + ci + " \nITEM " + i);
                        Mono<Item> countMono = orderControlUtilityService.countOfOrderAndItemId(i.getId(), ci.getOrderId())
                                .map(totalCount -> {
                                    long cnt = totalCount - 1;
                                    System.out.println("  ---------    COUNT MONO ::: " + totalCount + " /" + cnt
                                            + "   CRETERIA i.getId() " + i.getId() + "   AND  ci.getCartId() " + ci.getOrderId());
                                    if (cnt > 0) {
                                        i.setCount(cnt);
                                    } else {
                                        i.setCount(0L);
                                    }
                                    orderControlUtilityService.saveItem(i).subscribe();

                                    return i;
                                });
                        countMono.subscribe();

                    }
                    return i;
                });
    }
}
