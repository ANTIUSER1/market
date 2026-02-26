package pn.payment.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pn.market.market_entities.data.UserData;

public interface UserDataRepo extends ReactiveCrudRepository<UserData, Long> {

}
