package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.market_entities.forWEB.Cart;
import pn.market.market_entities.forWEB.CartItems;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CartUtilityService {

    @Autowired
    private CartControlUtilityService cartControlUtilityService;
    @Autowired
    private CartRemoveUtilityService cartRemoveUtilityService;





    public Flux<Item> cartItemsFluxToItemFlux(Flux<CartItems> cartItemsFlux, Long itemId) {
        Mono<List<CartItems>> cartItemsMonoList =cartItemsFlux .collectList();
         Flux<Mono<Item>> fmit = cartItemsFlux.map(ff -> {
            Mono<Item> ii = cartControlUtilityService.findItemById(ff.getItemId());

            ii = Mono.zip(ii, cartItemsFlux.collectList())
                    .map(t -> {
                                Item im = t.getT1();
                                 List<CartItems> cartItemsList = t.getT2();
                                 if (!cartItemsList.isEmpty()) {
                                    CartItems ci = cartItemsList.get(0);
                                    Mono<Long> longMono = cartControlUtilityService.countOfCartAndItemIdId(im.getId(), ci.getCartId());
                                    Mono<Item> itemMono = Mono.zip(Mono.just(im), longMono)
                                            .map(t1 -> {
                                                Item i = t1.getT1();
                                                Long count = t1.getT2();
                                                System.out.println("    PLUS COUNT VALUE "+count);
                                                i.setCount(count);
                                                cartControlUtilityService.saveItem(i).subscribe();
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

    public Mono<Cart> createOrTestExistCart(Long userId) {
        return cartControlUtilityService.findUserById(userId)
                 .map(u -> {
                     Mono<Cart> c;
                    if (u.getCartId() == null) {
                        System.out.println(" CREATE   NEW CART ");
                        c =cartControlUtilityService.saveCart(new Cart())
                                //cartRepo.save(new Cart())
                                .map(ccc -> {
                                    u.setCartId(ccc.getId());

                                     cartControlUtilityService.saveUser(u).subscribe();
                                    return ccc;
                                });
                    } else {
                     c = cartControlUtilityService.findCartById(u.getCartId());
                             //cartRepo.findById(u.getCartId());
                    }

                    return c;
                }).flatMap(c -> c);

    }

    public Mono<CartItems> createAndSaveCartItems(Long cartId, Long itemId){
        CartItems ci = new CartItems();
        ci.setCartId(cartId);
        ci.setItemId(itemId);
        return    cartControlUtilityService.saveCartItems(ci)  ;
                //cartItemsRepo.save(ci);
    }


    public Mono<Item> removeFromCartOfUser(Long userId, Long itemId) {
        System.out.println("           REMOVING ITEM  "+itemId +"  : USER :  "+userId);
       Mono<Cart> cartMono= cartControlUtilityService.findUserById(userId)
                .map(u->{
                    System.out.println("REQUESTED USER:");
                    Mono<Cart> c=cartControlUtilityService.findCartById(u.getCartId())
                            .map(ccc->{
                                System.out.println("CCCC "+ccc);
                                return ccc;
                            });
                    return  c;
                }).flatMap(v->v);
        Mono<List<CartItems>> cartItemsListMono=findCartItemsByUserId(cartMono,userId);
        Mono<Item> itemMono=cartControlUtilityService.findItemById(itemId);

        Mono<Item> result = cartRemoveUtilityService.removeItemFromCartOfUser(cartItemsListMono, itemMono,userId);



        return result;
    }

    private Mono<List<CartItems>> findCartItemsByUserId( Mono<Cart> cartMono, Long userId) {
        Mono<List<CartItems>> fci=cartMono.map(c-> {
            Flux<CartItems> f = cartControlUtilityService.findCartItemsByCartId(c.getId()  );
            return f.collectList();
        } ).flatMap(v->v);
        return fci;
    }



}











