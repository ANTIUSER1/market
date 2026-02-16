package pn.market.vitroBack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.market_entities.forWEB.Cart;
import pn.market.vitroBack.repo.CartRepo;
import pn.market.vitroBack.repo.ItemRepo;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/vitro/carts")
public class CartRest {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ItemRepo itemRepo;

    @GetMapping("/create/{itemId}")
    public Mono<Cart> createCart(
            @PathVariable("itemId") Long itemId

    ) {
        Mono<Cart> c = Mono.just(new Cart());

        Mono<Long> maxCartId = cartRepo.findMaxId();

        Mono<Cart> res = Mono.zip(c, maxCartId)
                .map(t -> {
                    Cart cc = t.getT1();
                    Long N = t.getT2();
                    System.out.println("     NNNNNNNNN  " + N);
                    // if (N == null) N = 1L;
                    itemRepo.findById(itemId)
                            .map(i -> {
                                        i.setCartId(N);
                                        return i;
                                    }
                            ).subscribe(i -> itemRepo.save(i));
                    cc.setId(N);

                    return cartRepo.save(cc);
                }).flatMap(cc -> cc);
        return res;
    }


}
