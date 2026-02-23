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


    public Mono<Cart> createOrUseCartOfUser(Long itemId, Long userId) {
        System.out.println("   ===::createNewCartOfUser:::IID  " + itemId + "   UID " + userId);
        System.out.println("  0  FFFFF   -TH :: " + Thread.currentThread());
        Mono<Cart> cartMono = cartUtilityService.createOrTestExistCart(userId);
        Mono<UserData> userDataMono = cartUtilityService.findUserById(userId);
        Mono<CartItems>  cartItemsMono=cartMono.map(c->{
      return       cartUtilityService.createAndSaveCartItems( c.getId(), itemId);
                }).flatMap(cv -> cv);;

        Mono<Flux<CartItems>> cartItemsFlux0 =cartItemsMono.map(ci->{
            return ci.getCartId();
        }).map(n->{
            return cartUtilityService.findCartItems( n );
        });
        /*
        Mono<Flux<CartItems>> cartItemsFlux0 = cartMono.map(c -> {
//            return cartUtilityService.findByCartId(c.getId());
            return cartUtilityService.findCartItems(c.getId(), itemId);
        }).map(cv -> cv);
        */
        cartMono = Mono.zip(cartMono, cartItemsFlux0)
                .map(t -> {
                    System.out.println("   ----------- IN  ZIP ::::::::::");
                    Mono<Cart> cartM = Mono.just(t.getT1());
                    Flux<CartItems> cartItemsFlux = t.getT2();
                    Flux<Item> itemFlux = cartUtilityService.cartItemsFluxToItemFlux(cartItemsFlux);
                    cartM = Mono.zip(cartM, itemFlux.collectList())
                            .map(t1 -> {
                                Cart c1 = t1.getT1();
                                List<Item> itemsList = t1.getT2();
                                System.out.println("      C-1----------   "+c1);
                                System.out.println("      ILS----------   "+itemsList.size());
                                c1.addItemsSet(itemsList);
                                return c1;
                            });
                    return cartM;
                }).flatMap(mm -> mm);

        System.out.println("   --T  FFFFF   -TH -----1 :: " + Thread.currentThread());
        return cartMono;
    }

    public Mono<Item> removeFromCartOfUser(Long itemId, Long userId) {
        return Mono.empty();
    }


}
