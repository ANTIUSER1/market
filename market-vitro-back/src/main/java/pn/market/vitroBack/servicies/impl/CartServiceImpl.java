package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pn.market.market_entities.Paging;
import pn.market.market_entities.TService;
import pn.market.market_entities.forWEB.Cart;
import pn.market.vitroBack.repo.CartRepo;
import pn.market.vitroBack.repo.ItemRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CartServiceImpl implements TService<Cart> {


    @Autowired
    private CartRepo cartRepo;


    @Autowired
    private ItemRepo itemRepo;


    @Override
    public Flux<Cart> findAll() {
        return cartRepo.findAll();
    }

    @Override
    public Mono<Cart> getById(Long id) {
        return Mono.empty();
    }

    @Override
    public Mono<Paging> findAllAndPaging(Mono<Pageable> pageable) {
        return null;
    }


    public Mono<Cart> createNewCart(Long itemId) {
        return cartRepo.save(new Cart()).map(
                ccc -> {
                    itemRepo.findById(itemId)
                            .map(i -> {
                                i.setCartId(ccc.getId());
                                i.setOrderId(null);
                                return itemRepo.save(i);
                            }).flatMap(i -> i).subscribe();
                    return ccc;
                }
        );
    }


    public Mono<Cart> createNewCartOfUser(Long itemId, Long userId) {
        Cart c = new Cart();
        c.setUserId(userId);

        return cartRepo.save(c).map(
                ccc -> {
                    itemRepo.findById(itemId)
                            .map(i -> {
                                i.setCartId(ccc.getId());
                                i.setOrderId(null);
                                return itemRepo.save(i);
                            }).flatMap(i -> i).subscribe();
                    return ccc;
                }
        );
    }

    public Flux<Cart> getByUser(Long user) {
        return cartRepo.findByUserId(user);
    }
}
