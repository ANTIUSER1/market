package pn.market.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pn.market.entities.Item;

import java.util.List;

@Repository
@Transactional
public interface ItemRepo extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i ORDER BY i.id ASC")
    List<Item> getAllItemsSortedAscById();

    Page<Item> findAll(Pageable parsable);

}
