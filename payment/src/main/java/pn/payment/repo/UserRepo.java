package pn.payment.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pn.payment.ent.User;

public interface UserRepo extends ReactiveCrudRepository<User,  Long> {
}
