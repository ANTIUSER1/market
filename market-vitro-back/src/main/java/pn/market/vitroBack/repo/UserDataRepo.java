package pn.market.vitroBack.repo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pn.market.market_entities.data.UserData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Transactional
public interface UserDataRepo extends ReactiveCrudRepository<UserData, Long> {

    @Query("SELECT * FROM user_data where username = $1")
    Mono<UserData> findByName(String un);

    @Query("SELECT  *    FROM user_data")
    Flux<UserData> findAllUsers();

}
