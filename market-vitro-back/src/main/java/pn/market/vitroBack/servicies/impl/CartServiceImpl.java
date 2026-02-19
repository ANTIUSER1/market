package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pn.market.market_entities.Paging;
import pn.market.market_entities.TService;
import pn.market.market_entities.forWEB.Cart;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroBack.repo.CartRepo;
import pn.market.vitroBack.repo.ItemRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CartServiceImpl implements TService<Cart> {


    @Autowired
    private CartRepo cartRepo;


    @Autowired
    private ItemRepo itemRepo;


    @Override
    public Flux<Cart> findAll() {
        return cartRepo.findAll();
    }

    @Override
    public Mono<Cart> getById(Long id) {
        return Mono.empty();
    }

    @Override
    public Mono<Paging> findAllAndPaging(Mono<Pageable> pageable) {
        return null;
    }

    //"     *****   CCC-  "+c
    public Mono<Cart> createNewCart(Long itemId) {
        Mono<Cart> result = cartRepo.save(new Cart()).map(
                ccc -> {
                    itemRepo.findById(itemId)
                            .map(i -> {
                                i.setCartId(ccc.getId());
                                i.setOrderId(null);
                                return itemRepo.save(i);
                            }).flatMap(i -> i).subscribe();

                    return ccc;
                }
        );
        return result;
    }

    public Mono<Cart> createNewCartOfUser(Long itemId, Long userId) {
        Mono<Cart> cartMono = itemRepo.findById(itemId)
                .map(i -> {

                    if (i.getCartId() == null) {
                        Cart c = new Cart();
                        c.setUserId(userId);
                        System.out.println("++*** NEW C " + c);
                        return Mono.just(c);
                    }
                    Mono<Cart> c = cartRepo.findById(i.getCartId())
                            .map(cc0 -> {
                                cc0.setUserId(userId);
                                System.out.println(":::::::+++++:: CC0 " + cc0);
                                return cc0;
                            });
                    return c;
                }).flatMap(c0 -> c0);
        System.out.println("  add-item-to-cart-of-useradd-item-to-cart-of-user-  " + userId);

        Mono<Item> itemMono = itemRepo.findById(itemId);
        cartMono = updateItem(cartMono, itemMono);
        return cartMono;
    }

    public Mono<Item> removeFromCartOfUser(Long itemId, Long userId) {
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
                    i.setOrderId(null);
                    return itemRepo.save(i);
                }).flatMap(ii -> ii);
        return Mono.zip(cartMono, itemMono)
                .map(t -> {
                    Item cc = t.getT2();
                    cartRepo.delete(t.getT1()).subscribe(u -> System.out.println("    CART  DELETED "));
                    return cc;
                });
    }

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
                                Integer count = i0.getCount();
                                i0.setCount(count + 1);
                                i0.setCartId(cx.getId());
                                i0.setOrderId(null);
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
/*
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

 */

    public Flux<Cart> getByUser(Long user) {
        return cartRepo.findByUserId(user);
    }

}
