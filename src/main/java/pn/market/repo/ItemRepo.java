package pn.market.repo;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pn.market.entities.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@Transactional
public interface ItemRepo extends ReactiveCrudRepository<Item, Long> {

    @Query("SELECT * FROM items i WHERE i.cart_id = :cartId  ORDER BY i.id ASC")
    Flux<Item> findByCartId(Long id);


}
