package pn.market.vitroBack.repo;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Flux;

@Repository
@Transactional
public interface ItemRepo extends ReactiveCrudRepository<Item, Long> {

    @Query("SELECT * FROM items i WHERE i.cart_id = :cartId  ORDER BY i.id ASC")
    Flux<Item> findByCartId(Long cartId);


    @Query(
            """ 
                            SELECT * FROM  items i  
                            WHERE   i.cart_id IN( SELECT id FROM carts c WHERE c.user_id = :userId )
                    
                    """)
    Flux<Item> findByUserId(Long userId);


    @Query("SELECT * FROM items i WHERE i.order_id = :orderId  ORDER BY i.id ASC")
    Flux<Item> findByOrderId(Long orderId);


    Flux<Item> findAllBy(Pageable pageable);

}
