package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.market_entities.forWEB.CartItems;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartRemoveUtilityService {

    @Autowired
    private CartControlUtilityService cartControlUtilityService;

    public Mono<Item> removeItemFromCartOfUser(Mono<List<CartItems>> cartItemsListMono, Mono<Item> itemMono, Long userId) {
        return Mono.zip(cartItemsListMono, itemMono)
                .map(t -> {
                    Item i = t.getT2();
                    List<CartItems> cartItemsList = t.getT1();
                    List<CartItems> cartItemsListTMP = new ArrayList<>();

                    for (CartItems cii : cartItemsList) {
                        if (i.getId() == cii.getItemId()) {
                            cartItemsListTMP.add(cii);
                        }
                    }

                    if (!cartItemsListTMP.isEmpty()) {
                        CartItems ci = cartItemsListTMP.get(0);
                        cartControlUtilityService.deleteCartItemsById(ci.getId()).subscribe();

                        Mono<Item> countMono = cartControlUtilityService.countOfCartAndItemId(i.getId(), ci.getCartId())
                                .map(totalCount -> {
                                    long cnt = totalCount - 1;
                                    System.out.println("  ---------    COUNT MONO ::: " + totalCount + " /" + cnt + "   CRETERIA i.getId() " + i.getId() + "   AND  ci.getCartId() " + ci.getCartId());
                                    if (cnt > 0) {
                                        i.setCount(cnt);
                                    } else {
                                        i.setCount(0L);
                                    }
                                    cartControlUtilityService.saveItem(i).subscribe();
                                    return i;
                                });
                        countMono.subscribe();
                    }
                    return i;
                });
    }
}
