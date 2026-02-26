package pn.payment.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pn.market.market_entities.forPAYMENTS.PersonData;

public interface PersonDataRepo extends ReactiveCrudRepository<PersonData, Long> {

}
