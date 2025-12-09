package pn.market.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import pn.market.entities.Cart;
import pn.market.entities.Item;
import pn.market.repo.CartRepo;
import pn.market.repo.ItemRepo;
import pn.market.services.TService;

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
    public Optional<Cart> getById(Long id) {
        return Optional.empty();
        //cartRepo.findById(id);
    }

    @Override
    public Page<Cart> findAllAndPaging(Pageable pageable) {
        return null;
        //cartRepo.findAll(pageable);
    }

    public Optional<Cart> getLast() {
        long cId = cartRepo.findMaxId(databaseClient).block();
        System.out.println("getLast   : cId = " + cId + "\n");
        Optional<Cart> cartOptional = cartRepo.findById(cId).blockOptional();
        if (cartOptional.isPresent()) {
            return cartOptional;
        }
        return Optional.empty();
    }

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
}
