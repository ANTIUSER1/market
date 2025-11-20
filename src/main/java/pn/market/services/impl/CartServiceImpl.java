package pn.market.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Optional<Cart> getById(Long id) {
        return cartRepo.findById(id);
    }



    @Override
    public Page<Cart> findAllAndPaging(Pageable pageable) {
        return cartRepo.findAll(pageable);
    }
}
