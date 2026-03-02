package pn.market.vitroBack.repo;

//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pn.market.market_entities.forWEB.Cart;
import reactor.core.publisher.Mono;

@Repository
@Transactional
public interface CartRepo extends ReactiveCrudRepository<Cart, Long> {

    @Query("SELECT MAX(id) FROM carts    ")
    Mono<Long> findMaxId();

    @Query("""
             select sum(i.count *i.price)  as cp from items i  where i.id in (
               select distinct oi.item_id  from carts_items  oi where oi.cart_id = $1
               )
            
            """)
    Mono<Long> calcTotalSumOfCartId(Long cartId);

}
