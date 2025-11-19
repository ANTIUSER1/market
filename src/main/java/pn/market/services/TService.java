package pn.market.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TService<T> {

    T create(T item);

    Optional<T> findById(Long id);

    int createList(List<T> itemList);

    Optional<T> getById(Long id);


    Page<T> findAllAndPaging(Pageable pageable);
    List<T> getAllUnsorted();

    List<T> getAllDSsorted();

    List<T> getAllASsorted();

    void deleteById(Long id);

}
