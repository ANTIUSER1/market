package pn.payment.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pn.payment.ent.PersonData;

public interface PersonDataRepo extends ReactiveCrudRepository<PersonData, Long> {
}
