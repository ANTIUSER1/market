package pn.market.services;

import org.springframework.data.domain.Pageable;
import pn.market.market_entities.Paging;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TService<T> {


    Flux<T> findAll();

    Mono<T> getById(Long id);

    Mono<Paging> findAllAndPaging(Mono<Pageable> pageable);

}
