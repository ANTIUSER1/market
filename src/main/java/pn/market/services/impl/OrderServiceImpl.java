package pn.market.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pn.market.additional.Paging;
import pn.market.entities.Order;
import pn.market.repo.ItemRepo;
import pn.market.repo.OrderRepo;
import pn.market.services.TService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        return orderRepo.findAll();
    }

    @Override
    public Mono<Order> getById(Long id) {
        return orderRepo.findById(id);
    }

    @Override
    public Mono<Paging> findAllAndPaging(Mono<Pageable> pageable) {
        return null;
    }

    public Mono<Order> save(Order order) {
        return orderRepo.save(order);
    }
    /*
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

 */
}
