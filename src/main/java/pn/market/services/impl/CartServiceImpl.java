package pn.market.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import pn.market.additional.Paging;
import pn.market.entities.Cart;
import pn.market.entities.Item;
import pn.market.repo.CartRepo;
import pn.market.repo.ItemRepo;
import pn.market.services.TService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class CartServiceImpl implements TService<Cart> {

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ItemServiceImpl itemService;

    @Override
    public Flux<Cart> findAll() {
        return null;
    }

    @Override
    public Optional<Cart> getById(Long id) {
        return Optional.empty();
        //cartRepo.findById(id);
    }

    @Override
    public Mono<Paging> findAllAndPaging(Mono<Pageable> pageable) {
//    public   Mono<Page<Cart>> findAllAndPaging( Mono<Pageable>  pageable) {
        return null;
        //cartRepo.findAll(pageable);
    }


    public Mono<Cart> getLastCartId() {
        return cartRepo.findMaxId(databaseClient)
                .map(id -> cartRepo.findById(id))
                .flatMap(c -> c);
    }

    /*
        public Optional<Cart> getLast() {
            long cId = cartRepo.findMaxId(databaseClient).block();
            System.out.println("getLast   : cId = " + cId + "\n");
            Optional<Cart> cartOptional = cartRepo.findById(cId).blockOptional();
            if (cartOptional.isPresent()) {
                return cartOptional;
            }
            return Optional.empty();
        }
      */
/*
    public Mono<Cart> addItem(Long itemId) {
        Mono<Cart> cartMono = itemRepo.findById(itemId)
                .map(i -> {
                    Mono<Cart> result = Mono.just(new Cart(-1L));
                    if (i.getCartId() == null) {
                        result = cartRepo.save(new Cart());
                    } else {
                        result = getLastCartId();
                    }
                    return result;
                }).flatMap(c -> c);
        Mono<Cart> result = Mono.zip(
                itemRepo.findById(itemId), cartMono
        ).map(t -> {

            Item i = t.getT1();
            Cart c = t.getT2();
            i.setCartId(c.getId());
            i.plusCount(c.getId());
            itemRepo.save(i);
            return Mono.just(c);
        }).flatMap(c -> c);
        return result;
    }

 */
/*
    public Mono<Cart> removeItem(Long itemId) {
        Mono<Cart> cartMono = itemRepo.findById(itemId)
                .map(i -> {
                    Mono<Cart> result = Mono.just(new Cart(-1L));
                    result = getLastCartId();

                    return result;
                }).flatMap(c -> c);
        Mono<Cart> result = Mono.zip(
                itemRepo.findById(itemId), cartMono
        ).map(t -> {

            Item i = t.getT1();
            Cart c = t.getT2();
            i.setCartId(null);
            i.minusCount();
            itemRepo.save(i);
            return Mono.just(c);
        }).flatMap(c -> c);
        return result;
    }
*/
/*
    public Cart plusItem(Long itemId) {
        System.out.println("plusItem   : itemId = " + itemId + "\n");
        Optional<Item> itemOptional = itemRepo.findById(itemId).blockOptional();
        System.out.println("plusItem   : itemOptional = " + itemOptional.get() + "\n");
        Optional<Cart> cartOptional = getLast();
        System.out.println("plusItem   : cartOptional present = " + cartOptional.isPresent() + "\n");
        System.out.println("plusItem   : cartOptional present = " + cartOptional.get() + "\n");
        Cart cart = null;
        if (itemOptional.isPresent() && cartOptional.isPresent()) {
            Item item = itemOptional.get();
            cart = cartOptional.get();
            itemService.plus(item, cart.getId());
            return cart;
        } else {
        }
        return cart;
    }
*/
/*
    public Cart minusItem(Long itemId) {
        System.out.println("minusItem    : itemId = " + itemId + "\n");
        Optional<Item> itemOptional = itemRepo.findById(itemId).blockOptional();

        Optional<Cart> cartOptional = getLast();
        System.out.println("minusItem    : cartOptional = " + cartOptional + "\n");
        System.out.println("minusItem    : (itemOptional.isPresent() && cartOptional.isPresent()) = "
                + (itemOptional.isPresent() && cartOptional.isPresent()) + "\n");
        Cart cart = null;
        if (itemOptional.isPresent() && cartOptional.isPresent()) {
            Item item = itemOptional.get();
            System.out.println("minusItem    : item = " + item + "\n");
            System.out.println("minusItem    : item.getCartId() = " + item.getCartId() + "\n");
            if (item.getCartId() != null) {
                cart = cartOptional.get();
                System.out.println("minusItem     : item = " + item + "\n");
                //    item.setCartId(null);
                itemService.minus(item);
                itemRepo.save(item).block();
//                cartRepo.save(cart).block();
                System.out.println("minusItem  UPDATE   : item = " + item + "\n");
                return cart;
            }
        }
        return cart;
    }
*/
    public Mono<Long> createNewCart() {
        return cartRepo.save(new Cart()).map(cart -> cart.getId());
    }

    public Mono<Long> createCartForItemIfNotExists(long itemId) {
        Mono<Long> cartIdMono = itemService.findById(itemId)
                .map(i -> {
                    if (i.getCartId() == null) {
                        return this.createNewCart();
                    } else return Mono.just(i.getCartId());
                }).flatMap(i -> i);
        return cartIdMono;
    }

    public Mono<Item> placeItemToCart(long itemId, String action) {

        Mono<Item> itemMono = Mono.zip(
                        itemService.findById(itemId),
                        this.createCartForItemIfNotExists(itemId)
                )
                .map(t -> {
                    Item i = t.getT1();
                    long cartId = t.getT2();
                    Mono<Item> mi = null;
                    if (action != null) {
                        mi = itemService.addToCart(i, cartId, action);
                    }
                    if (mi != null) return mi;
                    else return Mono.just(i);
                }).flatMap(i -> i);
        return itemMono;
    }
}
