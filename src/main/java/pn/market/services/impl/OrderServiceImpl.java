package pn.market.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pn.market.entities.Item;
import pn.market.entities.Order;
import pn.market.repo.ItemRepo;
import pn.market.repo.OrderRepo;
import pn.market.services.TService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service

public class OrderServiceImpl implements TService<Order> {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ItemServiceImpl itemService;


    @Override
    public Flux<Order> findAll() {
        return null;
    }

    @Override
    public Optional<Order> getById(Long id) {
        return orderRepo.findById(id).blockOptional();
    }

    @Override
    public Mono<Page<Order>> findAllAndPaging(Pageable pageable) {
        return null;
    }

    public List<Order> findAllOrders() {
        return orderRepo.findAll().collectList().block();
    }

    public void buyOrder(long id) {
        Optional<Order> orderOptional = orderRepo.findById(id).blockOptional();
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<Item> items = itemRepo.findByOrderId(id).collectList().block();
            itemService.setNullOderId(items);
            orderRepo.delete(order).block();
        }
    }
}
