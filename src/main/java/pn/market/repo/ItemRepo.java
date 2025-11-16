package pn.market.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pn.market.entities.Item;

public interface ItemRepo extends JpaRepository<Item, Long> {
}
