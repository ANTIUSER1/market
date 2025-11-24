package pn.market.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pn.market.entities.Order;
import pn.market.repo.OrderRepo;
import pn.market.services.TService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements TService<Order> {

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public Optional<Order> getById(Long id) {
        return orderRepo.findById(id);
    }

    @Override
    public Page<Order> findAllAndPaging(Pageable pageable) {
        return null;
    }

    public List<Order> findAllOrders() {
        return orderRepo.findAll();
    }

    public void buyOrder(long id) {
        Optional<Order> orderOptional = orderRepo.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setItems(new ArrayList<>());
            orderRepo.save(order);
            orderRepo.delete(order);

        }
    }

}
