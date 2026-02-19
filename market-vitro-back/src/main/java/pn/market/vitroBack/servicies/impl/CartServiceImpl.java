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
        Cart c = new Cart();
        c.setUserId(userId);
        System.out.println("  add-item-to-cart-of-useradd-item-to-cart-of-user-  " + userId);
        return cartRepo.save(c).map(
                ccc -> {
                    return updateItem(ccc, itemId);
                }
        ).flatMap(cc -> cc);
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
        return cartRepo.findByUserId(user);
    }

}
