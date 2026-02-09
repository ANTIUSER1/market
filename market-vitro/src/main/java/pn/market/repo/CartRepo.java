package pn.market.repo;

//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pn.market.market_entities.forWEB.Cart;
import reactor.core.publisher.Mono;

@Repository
@Transactional
public interface CartRepo extends ReactiveCrudRepository<Cart, Long> {

    default Mono<Long> findMaxId(DatabaseClient client) {
        Mono<Long> maxId = client.sql("select max(id) as mp from carts")
                .map(i -> (Long) i.get("mp")).one();
        return maxId;

    }
}
