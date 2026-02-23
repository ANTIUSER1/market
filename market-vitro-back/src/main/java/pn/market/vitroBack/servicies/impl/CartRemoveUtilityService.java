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
                    System.out.println("***------------REMOVED ITEM " + i);
                    List<CartItems> cartItemsList = t.getT1();
                    List<CartItems> cartItemsListTMP = new ArrayList<>();
                    //       System.out.println("      CART-LIST-SIZE -- "+cartItemsList.size());
                    for (CartItems cii : cartItemsList) {
                        if (i.getId() == cii.getItemId()) {
                            System.out.println("     ITIM     " + i + "    " + cii);
                            cartItemsListTMP.add(cii);
                        }
                    }
                    if (!cartItemsListTMP.isEmpty()) {
                        CartItems ci = cartItemsListTMP.get(0);
                        System.out.println("REMOVING      " + ci + "   " + ci.getId() + "  /  " + ci.getItemId());
                        cartControlUtilityService.deleteCartItemsById(ci.getId()).subscribe();
                        System.out.println(" CI TO REMOVR  " + ci + " \nITEM " + i);
                        Mono<Item>  countMono= cartControlUtilityService.countOfCartAndItemIdId(i.getId(), ci.getCartId())
                                .map(totalCount->{
                                     long cnt=totalCount-1;
                                    System .out.println("  ---------    COUNT MONO ::: "+totalCount+" /"+cnt+"   CRETERIA i.getId() "+i.getId() +"   AND  ci.getCartId() "+ ci.getCartId());
                                if(cnt>0)    {
                                    i.setCount(cnt);}
                                else {
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
