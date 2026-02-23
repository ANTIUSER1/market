package pn.market.vitroBack.repo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pn.market.market_entities.forWEB.CartItems;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartItemsRepo extends ReactiveCrudRepository<CartItems, Long> {

    @Query("SELECT * FROM carts_items ci WHERE ci.cart_id = $1    ")
    Flux<CartItems> findByCartId(Long cartId);

    @Query("SELECT * FROM carts_items ci WHERE ci.item_id = $1    ")
    Flux<CartItems> findByItemId(Long item_id);

    @Query("SELECT COUNT(*) FROM carts_items ci WHERE ci.item_id = $1 AND  ci.cart_id = $2    ")
    Mono<Long> countOfCartAndItemId(Long itemId, Long cartId);
}
