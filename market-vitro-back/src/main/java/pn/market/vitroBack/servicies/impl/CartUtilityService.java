package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.market_entities.data.UserData;
import pn.market.market_entities.forWEB.Cart;
import pn.market.market_entities.forWEB.CartItems;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroBack.repo.CartItemsRepo;
import pn.market.vitroBack.repo.CartRepo;
import pn.market.vitroBack.repo.ItemRepo;
import pn.market.vitroBack.repo.UserDataRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class CartUtilityService {

    @Autowired
    private CartControlUtilityService cartControlUtilityService;


    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemsRepo cartItemsRepo;

    @Autowired
    private UserDataRepo userDataRepo;


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
        return userDataRepo.findById(userId)
                .map(u -> {                     Mono<Cart> c;
                    if (u.getCartId() == null) {
                        System.out.println(" CREATE   NEW CART ");
                        c = cartRepo.save(new Cart())
                                .map(ccc -> {
                                    u.setCartId(ccc.getId());
                                    userDataRepo.save(u).subscribe();
                                    return ccc;
                                });
                    } else {
                        c = cartRepo.findById(u.getCartId());
                    }

                    return c;
                }).flatMap(c -> c);

    }

    public Mono<CartItems> createAndSaveCartItems(Long cartId, Long itemId){
        CartItems ci = new CartItems();
        ci.setCartId(cartId);
        ci.setItemId(itemId);
        return        cartItemsRepo.save(ci);
    }

//    public Mono<Cart> createAndSaveCartsItemsData(Mono<Cart> cartMono, Long itemId, boolean u) {
//        return cartMono.map(c -> {
//            if (u) {
//                CartItems cartItems = new CartItems(itemId, c.getId());
//                cartItemsRepo.save(cartItems).subscribe();
//            }
//            return c;
//        });
//    }
//    public Flux<CartItems> findCartItems(Long cartId ) {
//       return cartItemsRepo.findByCartId(cartId);
//    }
//
//    public Flux<Cart> findAllCarts() {
//        return cartRepo.findAll();
//    }
//
//    public Mono<UserData> findUserById(Long userId) {
//        return userDataRepo.findById(userId);
//    }
//
//    public Mono<UserData> saveUser(UserData u) {
//        return userDataRepo.save(u);
//    }
//
//    public Mono<Long> countOfCartAndItemIdId(Long itemId, Long cartId) {
//        return cartItemsRepo.countOfCartAndItemIdId(itemId, cartId);
//    }
}
