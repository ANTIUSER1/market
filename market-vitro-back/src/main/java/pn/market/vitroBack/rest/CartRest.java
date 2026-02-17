package pn.market.vitroBack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.market_entities.forWEB.Cart;
import pn.market.vitroBack.servicies.impl.CartServiceImpl;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/vitro/carts")
public class CartRest {

    @Autowired
    private CartServiceImpl cartService;

    @GetMapping("/create/{itemId}")
    public Mono<Cart> createCart(
            @PathVariable("itemId") Long itemId

    ) {
        return cartService.createNewCart(itemId);
//             .map(
//             ccc -> {
//                 System.out.println("  :::: CCC CCC : " + ccc.getId());
//                 itemRepo.findById(itemId)
//                         .map(i -> {
//                             i.setCartId(ccc.getId());
//                             System.out.println("    II----II " + i);
//                             return itemRepo.save(i);
//                         }).flatMap(i -> i).subscribe();
//                 return ccc;
//             }
//     );
        /*
        Cart cn = new Cart();
        Mono<Cart> cartMono = cartRepo.save(cn);
        return cartMono.map(
                ccc -> {
                    System.out.println("  :::: CCC CCC : " + ccc.getId());
                    itemRepo.findById(itemId)
                            .map(i -> {
                                i.setCartId(ccc.getId());
                                System.out.println("    II----II " + i);
                                return itemRepo.save(i);
                            }).flatMap(i -> i).subscribe();
                    return ccc;
                }
        );

         */

    }


}
