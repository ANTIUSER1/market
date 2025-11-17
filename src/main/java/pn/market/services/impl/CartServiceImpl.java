package pn.market.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.entities.Cart;
import pn.market.repo.CartRepo;
import pn.market.services.TService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartServiceImpl implements TService<Cart> {
    @Autowired
    private CartRepo cartRepo;

    @Override
    public Cart create(Cart cart) {
        log.info("add item \n{}\n to db", cart);
        return cartRepo.save(cart);
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepo.findById(id);
    }

    @Override
    public int createList(List<Cart> cartList) {
        log.info("add itemList \n{}\n to db", cartList);
        return cartRepo.saveAll(cartList).size();
    }

    @Override
    public Optional<Cart> getById(Long id) {
        return cartRepo.findById(id);
    }

    @Override
    public List<Cart> getAllUnsorted() {
        return cartRepo.findAll();
    }

    @Override
    public List<Cart> getAllDSsorted() {
        return cartRepo.getAllCartsSortedDescById();
    }


    @Override
    public List<Cart> getAllASsorted() {
      return cartRepo.getAllCartsSortedAscById();
    }


    @Override
    public void deleteById(Long id) {
        log.info("try remove item with ID {} ", id);
        Optional<Cart> itemOptional = cartRepo.findById(id);
        if (itemOptional.isPresent()) {
            log.info("remove item   {} ", itemOptional.get());
            cartRepo.delete(itemOptional.get());
        }
        log.info("No kitems to remove   ");
    }
}
