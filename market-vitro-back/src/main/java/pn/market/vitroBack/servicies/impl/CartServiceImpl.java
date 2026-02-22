package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pn.market.market_entities.Paging;
import pn.market.market_entities.TService;
import pn.market.market_entities.data.UserData;
import pn.market.market_entities.forWEB.Cart;
import pn.market.market_entities.forWEB.CartItems;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartServiceImpl implements TService<Cart> {


//    @Autowired
//    private CartRepo cartRepo;

//    @Autowired
//    private CartItemsRepo cartItemsRepo;

//    @Autowired
//    private ItemRepo itemRepo;

//    @Autowired
//    private UserDataRepo userDataRepo;

    @Autowired
    private CartUtilityService cartUtilityService;

    @Override
    public Flux<Cart> findAll() {
        return cartUtilityService.findAllCarts();
    }

    @Override
    public Mono<Cart> getById(Long id) {
        return Mono.empty();
    }

    @Override
    public Mono<Paging> findAllAndPaging(Mono<Pageable> pageable) {
        return null;
    }
/*
    public Mono<Cart> createNewCart(Long itemId) {
        Mono<Cart> result = cartRepo.save(new Cart()).map(
                ccc -> {
                    itemRepo.findById(itemId)
                            .map(i -> {
                                return itemRepo.save(i);
                            }).flatMap(i -> i).subscribe();

                    return ccc;
                }
        );
        return result;
    }
*/

    public Mono<Cart> createOrUseCartOfUser(Long itemId, Long userId, boolean u) {
        System.out.println("   ===::createNewCartOfUser:::IID  " + itemId + "   UID " + userId);
        System.out.println("  0  FFFFF   -TH :: " + Thread.currentThread());
        Mono<Cart> cartMono = cartUtilityService.createOrTestExistCart(userId);
        Mono<UserData> userDataMono = cartUtilityService.findUserById(userId);
        Mono<Flux<CartItems>> cartItemsFlux0 = cartMono.map(c -> {
//            return cartUtilityService.findByCartId(c.getId());
            return cartUtilityService.findByCartId(c.getId());
        }).map(cv -> cv);

        Mono<Cart> cartMono0 = Mono.zip(cartMono, cartItemsFlux0)
                .map(t -> {
                    Mono<Cart> cartM = Mono.just(t.getT1());
                    Flux<CartItems> cartItemsFlux = t.getT2();
                    Flux<Item> itemFlux = cartUtilityService.cartItemsFluxToItemFlux(cartItemsFlux);
                    cartM = Mono.zip(cartM, itemFlux.collectList())
                            .map(t1 -> {
                                Cart c1 = t1.getT1();
                                List<Item> itemsList = t1.getT2();
                                c1.addItemsSet(itemsList);
                                return c1;
                            });
                    return cartM;
                }).flatMap(mm -> mm);
        cartMono0.subscribe(cc -> {
            System.out.println("\n---------------------------\n" + LocalDateTime.now() + "\n CI:\n");

            for (Item i : cc.getItems()) {
                System.out.println("   -----cartMono0----  ITEM :::  " + i);
            }
        });

        System.out.println("   --T  FFFFF   -TH -----1 :: " + Thread.currentThread());
        return cartMono;
    }

    //---------------
//    private Flux<Item> cartItemsFluxToItemFlux(Flux<CartItems> cartItemsFlux) {
//        Flux<Mono<Item>> fmit = cartItemsFlux.map(ff -> {
//            Mono<Item> ii = itemRepo.findById(ff.getItemId());
//            return ii;
//        });
//        Flux<Item> result = fmit.flatMap(fk -> fk);
//        return result;
//    }
    //----------------------

//    private Mono<List<CartItems>> getCartItems(Mono<Cart> cartMono) {
//        Mono<List<CartItems>> fcm = cartMono.map(cm -> {
//            return cartItemsRepo.findByCartId(cm.getId()).collectList();
//        }).flatMap(mm -> mm);
//        return fcm;
//    }


//    private Mono<Cart> createAndSaveCartsItemsData(Mono<Cart> cartMono, Long itemId, boolean u) {
//        return cartMono.map(c -> {
//            if (u) {
//                CartItems cartItems = new CartItems(itemId, c.getId());
//                cartItemsRepo.save(cartItems).subscribe();
//            }
//            return c;
//        });
//    }

/*
    private Mono<Cart> updateByUserData(Mono<Cart> cartMono, Mono<UserData> userDataMono) {
        return Mono.zip(cartMono, userDataMono)
                .map(t -> {
                    System.out.println("   -U-TH :: " + Thread.currentThread());
                    Cart c = t.getT1();
                    UserData u = t.getT2();
                    u.setCartId(c.getId());
                    cartUtilityService.saveUser(u).subscribe();
                    return c;
                });
    }

 */

    //-------------------------
//    private Mono<Cart> createOrTestExistCart(Long userId) {
//        return userDataRepo.findById(userId)
//                .map(u -> {
//                    System.out.println("   ----C-TH :: " + Thread.currentThread());
//                    Mono<Cart> c;
//                    if (u.getCartId() == null) {
//                        c = cartRepo.save(new Cart());
//                    } else {
//                        c = cartRepo.findById(u.getCartId());
//                    }
//                    return c;
//                }).flatMap(c -> c);
//
//    }
//-----------------
/*
    private Mono<Cart> searchCorrectCart(Long userId) {
        System.out.println("=======searchCorrectCart===== U " + userId);
        Mono<UserData> userDataMono = userDataRepo.findById(userId);
        Mono<Cart> cartMono = userDataMono.map(u -> {
            System.out.println("  USER:  " + u);
            Mono<Cart> c;
            if (u.getCartId() == null) {
                c = Mono.just(new Cart());
                c.map(ccc -> {
                    System.out.println("   NEW CCC " + ccc);
                    return ccc;
                }).subscribe();
            } else {
                c = cartRepo.findById(u.getCartId());
                c.map(ccc -> {
                    System.out.println("   OLD CCC " + ccc);
                    return ccc;
                }).subscribe();
            }
            System.out.println("     --------------CCCCart Created   " + c);
            return c;
        }).flatMap(cs -> cs);
        return cartMono;
    }

 */

    public Mono<Item> removeFromCartOfUser(Long itemId, Long userId) {
        return Mono.empty();
      /*
        System.out.println("     REMOVE ITEM " + itemId);
        Mono<Cart> cartMono = itemRepo.findById(itemId).map(
                i -> {
                    return cartRepo.findById(i.getCartId());
                }).flatMap(c -> c);
        Mono<Item> itemMono = itemRepo.findById(itemId)
                .map(i -> {
                    Integer count = i.getCount();
                    i.setCount(count - 1);
                    i.setCartId(null);
                    if (i.getCount() == 0) i.setOrderId(null);
                    return itemRepo.save(i);
                }).flatMap(ii -> ii);
        return Mono.zip(cartMono, itemMono)
                .map(t -> {
                    Item cc = t.getT2();
                    cartRepo.delete(t.getT1()).subscribe(u -> System.out.println("    CART  DELETED "));
                    return cc;
                });
        */
    }

/*
    private Mono<Cart> updateItem(Mono<Cart> ccc, Mono<Item> iii) {
        return Mono.zip(ccc, iii)
                .map(t -> {
                    Cart c0 = t.getT1();
                    Item i0 = t.getT2();
                    System.out.println("     :::: SAVE   CART....... ");
                    Mono<Cart> cs = cartRepo.save(c0)
                            .map(cx -> {
                                System.out.println("   ----CXCX-----CX-  :: C CART WILL UPDATE " + cx);

                                System.out.println("     :::: MODIFY ITEM....... ");
                                System.out.println("     :::: SAVE   ITEM....... ");
                                itemRepo.save(i0)
                                        .map(i -> {
                                            System.out.println("   --- ITEM SAVED:  " + i);
                                            return i;
                                        })
                                        .subscribe();


                                return cx;
                            });
                    return cs;
                }).flatMap(cx -> cx);
    }
    private Mono<Cart> updateItem(Cart ccc, Long itemId) {
        return itemRepo.findById(itemId)
                .map(i -> {
                    Integer count = i.getCount();
                    i.setCount(count + 1);
                    i.setCartId(ccc.getId());
                    i.setOrderId(null);
                    System.out.println("     :::: SAVE ITEM....... ");
                    itemRepo.save(i).subscribe();
                    return ccc;
                });
    }

    public Flux<Cart> getByUser(Long user) {
        return Flux.empty();
        //cartRepo.findByUserId(user);
    }
 */


}
