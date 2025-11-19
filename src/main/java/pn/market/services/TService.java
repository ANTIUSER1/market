package pn.market.services;

import java.util.List;
import java.util.Optional;

public interface TService<T> {

    T create(T item);

    Optional<T> findById(Long id);

    int createList(List<T> itemList);

    Optional<T> getById(Long id);

    List<T> findAll(int page, int pageSize, String search, String sorted);

    List<T> getAllUnsorted();

    List<T> getAllDSsorted();

    List<T> getAllASsorted();

    void deleteById(Long id);

}
