package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pn.market.market_entities.Paging;
import pn.market.market_entities.TService;
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

    @Autowired
    private CartControlUtilityService cartControlUtilityService;


    @Override
    public Flux<Cart> findAll() {
        return cartControlUtilityService.findAllCarts();
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
        Mono<Cart> cartMono = cartUtilityService.createOrTestExistCart(userId);
       // Mono<UserData> userDataMono = cartUtilityService.findUserById(userId);
        Mono<CartItems>  cartItemsMono=cartMono.map(c->{
      return       cartUtilityService.createAndSaveCartItems( c.getId(), itemId)
              .map(cci->{
                  return cci;
              });
                }).flatMap(cv -> cv);

        Mono<Flux<CartItems>> cartItemsFlux0 =cartItemsMono.map(ci->{
            return ci.getCartId();
        }).map(n->{
            return cartControlUtilityService.findCartItemsByCartId( n );
        });

        cartMono = Mono.zip(cartMono, cartItemsFlux0)
                .map(t -> {
                    Mono<Cart> cartM = Mono.just(t.getT1());
                    Flux<CartItems> cartItemsFlux = t.getT2();
                    Flux<Item> itemFlux = cartUtilityService.cartItemsFluxToItemFlux(cartItemsFlux, itemId);
                    cartM = Mono.zip(cartM, itemFlux.collectList())
                            .map(t1 -> {
                                Cart c1 = t1.getT1();
                                List<Item> itemsList = t1.getT2();
                                c1.addItemsSet(itemsList);
                                return c1;
                            });
                    return cartM;
                }).flatMap(mm -> mm);
        return cartMono;
    }

    public Mono<Item> removeFromCartOfUser(Long userId, Long itemId) {
        Mono<Item> itemMono=  cartUtilityService.removeFromCartOfUser(userId, itemId);
        return itemMono;
    }


}
