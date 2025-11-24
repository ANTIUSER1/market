package pn.market.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TService<T> {

    Optional<T> getById(Long id);

    Page<T> findAllAndPaging(Pageable pageable);

   // Optional<T> findById(Long id);
}
