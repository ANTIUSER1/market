package pn.market.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.entities.Cart;
import pn.market.entities.Order;
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
        log.info("add Order \n{}\n to db", cart);
        return cartRepo.save(cart);
    }

    @Override
    public int createList(List<Cart> cartList) {
        log.info("add OrderList \n{}\n to db", cartList);
        return   cartRepo.saveAll(cartList).size();
    }

    @Override
    public Optional<Cart> getById(Long id) {
        return cartRepo.findById(id);
    }

    @Override
    public List<Cart> getAllUnsorted() {
        return cartRepo.findAll( );
    }

    @Override
    public List<Cart> getAllDSsorted() {
        return cartRepo.getAllCartSortedDescById();
    }

    @Override
    public List<Cart> getAllASsorted() {
        return cartRepo.getAllCartSortedAscById();
    }


    @Override
    public void deleteById(Long id) {
        log.info("try remove Order with ID {} ", id);
        Optional<Cart> cartOptional=cartRepo.findById(id);
        if(cartOptional.isPresent()){
            log.info("remove Order   {} ", cartOptional.get());
            cartRepo.delete(cartOptional.get());
        }
        log.info("No kOrders to remove   ");
    }
}
