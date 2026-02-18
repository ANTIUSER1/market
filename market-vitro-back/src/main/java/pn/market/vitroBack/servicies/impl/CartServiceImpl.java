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

    //"     *****   CCC-  "+c
    public Mono<Cart> createNewCart(Long itemId) {
        Mono<Cart> result = cartRepo.save(new Cart()).map(
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
        return result;
    }


    public Mono<Cart> createNewCartOfUser(Long itemId, Long userId) {
        Cart c = new Cart();
        c.setUserId(userId);

        Mono<Cart> result = cartRepo.save(c).map(
                ccc -> {
                    itemRepo.findById(itemId)
                            .map(i -> {
                                Integer count = i.getCount();
                                i.setCount(count + 1);
                                i.setCartId(ccc.getId());
                                i.setOrderId(null);
                                return itemRepo.save(i);
                            }).flatMap(i -> i).subscribe();
                    return ccc;
                }
        );
        result.subscribe(cn -> System.out.println("     *****   CCC-  " + cn));
        return result;
    }

    public Flux<Cart> getByUser(Long user) {
        return cartRepo.findByUserId(user);
    }
}
