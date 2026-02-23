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

import java.util.Optional;

@Service
public class CartControlUtilityService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemsRepo cartItemsRepo;

    @Autowired
    private UserDataRepo userDataRepo;

    public Mono<Item> findItemById(Long itemId) {
        return itemRepo.findById(itemId);
    }

    public Mono<Item> saveItem(Item i) {
        return         itemRepo.save(i);
    }

    public Mono<UserData> findCartById(Long userId) {
        return userDataRepo.findById(userId);
    }
    public Flux<CartItems> findCartItems(Long cartId) {
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

    public Mono<Long> countOfCartAndItemIdId(Long itemId, Long cartId) {
        return cartItemsRepo.countOfCartAndItemIdId(itemId, cartId);
    }


}
