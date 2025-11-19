package pn.market.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pn.market.entities.Item;

import java.util.List;

@Repository
@Transactional
public interface ItemRepo extends JpaRepository<Item, Long> {


    @Query("SELECT i FROM Item i ORDER BY i.id DESC")
    List<Item> getAllItemsSortedDescById();

    @Query("SELECT i FROM Item i ORDER BY i.id ASC")
    List<Item> getAllItemsSortedAscById();

  // @Query("SELECT i FROM Item i  ")
  //  List<Item> findAllAndPaging(Pageable parsable);
Page<Item> findAll(Pageable parsable);
    /*

    --------------------------


     */

    @Query("""
            SELECT i FROM Item i
            WHERE i.title LIKE %:search% OR i.description LIKE %:search%
            ORDER BY i.title  ASC
            OFFSET :page
            """)
    List<Item> findAllAndSortByTitle(
            @Param("page")
            int page,
            @Param("pageSize")
            int pageSize,
           @Param("search")
            String search );

    @Query("""
            SELECT i FROM Item i
            WHERE i.title LIKE %:search% OR i.description LIKE %:search%
            ORDER BY i.price ASC
            OFFSET :page
            """)
    List<Item> findAllAndSortByPrice(
            @Param("page")
            int page,
            @Param("pageSize")
            int pageSize,
            @Param("search")
            String search );


    @Query("""
            SELECT i FROM Item i
            WHERE i.title LIKE %:search% OR i.description LIKE %:search%
           
            """)
    List<Item> findAllNoSort(
            @Param("page")
            int page,
            @Param("pageSize")
            int pageSize,
            @Param("search")
            String search );
    /*


                 WHERE i.title LIKE %:search% OR i.description LIKE %:search%
            ORDER BY i.id :sorted
           OFFSET :page
            LIMIT  :pageSize
     */
}
