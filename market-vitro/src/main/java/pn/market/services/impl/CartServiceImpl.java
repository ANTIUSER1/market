package pn.market.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pn.market.market_entities.Paging;
import pn.market.market_entities.forWEB.Cart;
import pn.market.market_entities.forWEB.Item;
import pn.market.repo.CartRepo;
import pn.market.services.TService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CartServiceImpl implements TService<Cart> {


    @Autowired
    private CartRepo cartRepo;


    @Autowired
    private ItemServiceImpl itemService;

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


    public Mono<Long> createNewCart() {
        return cartRepo.save(new Cart()).map(cart -> cart.getId());
    }

    public Mono<Long> createCartForItemIfNotExists(long itemId) {
        Mono<Long> cartIdMono = itemService.findById(itemId)
                .map(i -> {
                    if (i.getCartId() == null) {
                        return this.createNewCart();
                    } else return Mono.just(i.getCartId());
                }).flatMap(ci -> ci);
        return cartIdMono;
    }

    public Mono<Item> placeItemToCart(long itemId, String action) {
        Mono<Item> itemMono = Mono.zip(
                        itemService.findById(itemId),
                        this.createCartForItemIfNotExists(itemId)

                )
                .map(t -> {

                    Item i = t.getT1();
                    Long cartId = t.getT2();


                    if (action != null) {
                        return itemService.addToCart(i, cartId, action);
                    }
                    return Mono.just(i);
                })
                .flatMap(i -> i);
        return itemMono;
    }
}
