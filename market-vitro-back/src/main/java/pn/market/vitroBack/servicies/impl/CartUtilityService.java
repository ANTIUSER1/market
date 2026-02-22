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

@Service
public class CartUtilityService {


    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemsRepo cartItemsRepo;

    @Autowired
    private UserDataRepo userDataRepo;


    public Flux<Item> cartItemsFluxToItemFlux(Flux<CartItems> cartItemsFlux) {
        Flux<Mono<Item>> fmit = cartItemsFlux.map(ff -> {
            Mono<Item> ii = itemRepo.findById(ff.getItemId());
            return ii;
        });
        Flux<Item> result = fmit.flatMap(fk -> fk);
        return result;
    }

    public Mono<Cart> createOrTestExistCart(Long userId) {
        return userDataRepo.findById(userId)
                .map(u -> {
                    System.out.println("   ----C-TH :: " + Thread.currentThread());
                    Mono<Cart> c;
                    if (u.getCartId() == null) {
                        c = cartRepo.save(new Cart());
                    } else {
                        c = cartRepo.findById(u.getCartId());
                    }
                    return c;
                }).flatMap(c -> c);

    }

    public Mono<Cart> createAndSaveCartsItemsData(Mono<Cart> cartMono, Long itemId, boolean u) {
        return cartMono.map(c -> {
            if (u) {
                CartItems cartItems = new CartItems(itemId, c.getId());
                cartItemsRepo.save(cartItems).subscribe();
            }
            return c;
        });
    }

    public Flux<CartItems> findByCartId(Long cartId) {
        return cartItemsRepo.findByCartId(cartId);
    }

    public Flux<Cart> findAllCarts() {
        return cartRepo.findAll();
    }

    public Mono<UserData> findUserById(Long userId) {
        return userDataRepo.findById(userId);
    }

    public Mono<UserData> saveUser(UserData u) {
        return userDataRepo.save(u);
    }

    public Flux<CartItems> findByCartAndItemIdId(Long itemId, Long cartId) {
        return cartItemsRepo.findByCartAndItemIdId(itemId, cartId);
    }
}
