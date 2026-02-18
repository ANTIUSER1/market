package pn.market.vitroBack.repo;

//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pn.market.market_entities.forWEB.Cart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Transactional
public interface CartRepo extends ReactiveCrudRepository<Cart, Long> {

    @Query("SELECT MAX(id) FROM carts    ")
    Mono<Long> findMaxId();


    @Query("SELECT * FROM carts c  WHERE   c.user_id = :userId ORDER BY c.id ASC")
    Flux<Cart> findByUserId(Long userId);

}
