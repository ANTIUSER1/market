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

    public Mono<Item> removeFromCartOfUser(Long itemId, Long userId) {
        return Mono.empty();
    }


}
