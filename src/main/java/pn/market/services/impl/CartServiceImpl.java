package pn.market.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pn.market.entities.Cart;
import pn.market.entities.Item;
import pn.market.repo.CartRepo;
import pn.market.repo.ItemRepo;
import pn.market.services.TService;

import java.util.Optional;

@Service
@Slf4j
public class CartServiceImpl implements TService<Cart> {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ItemServiceImpl itemService;

    @Override
    public Optional<Cart> getById(Long id) {
        return cartRepo.findById(id);
    }

    @Override
    public Page<Cart> findAllAndPaging(Pageable pageable) {
        return cartRepo.findAll(pageable);
    }

    public Optional<Cart> getLast() {
        long cId = cartRepo.findMaxId();
        Optional<Cart> cartOptional = cartRepo.findById(cId);
        if (cartOptional.isPresent()) {
            return cartOptional;
        }
        return Optional.empty();
    }

    public Cart plusItem(Long itemId) {
        Optional<Item> itemOptional = itemRepo.findById(itemId);
        Optional<Cart> cartOptional = getLast();
        if (itemOptional.isPresent() && cartOptional.isPresent()) {
            Item item = itemOptional.get();
            Cart cart = cartOptional.get();
            cart.plusItem(item);
            itemService.plus(item);
            if (!cart.isiTtemInCart(item)) {
                cart.plusItem(item);
            }
            return cartRepo.save(cart);
        } else
            return null;
    }

    public Cart minusItem(Long itemId) {
        Optional<Item> itemOptional = itemRepo.findById(itemId);
        Optional<Cart> cartOptional = getLast();
        if (itemOptional.isPresent() && cartOptional.isPresent()) {
            Item item = itemOptional.get();
            Cart cart = cartOptional.get();
            cart.plusItem(item);
            itemService.minus(item);
            if (!cart.isiTtemInCart(item)) {
                cart.minusItem(item);
            }
            return cartRepo.save(cart);
        } else
            return null;
    }

}
