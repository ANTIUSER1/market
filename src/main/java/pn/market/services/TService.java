package pn.market.services;

import java.util.List;
import java.util.Optional;

public interface TService<T> {

    T create(T item);

    int createList(List<T> itemList);

    Optional<T> getById(Long id);

    List<T> getAllUnsorted();

    List<T> getAllDSsorted();

    List<T> getAllASsorted();

    void deleteById(Long id);

}
