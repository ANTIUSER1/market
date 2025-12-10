package pn.market.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

import java.util.Optional;

public interface TService<T> {

    Flux<T> findAll();

    Optional<T> getById(Long id);

    Page<T> findAllAndPaging(Pageable pageable);

}
