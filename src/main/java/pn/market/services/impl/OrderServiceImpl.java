package pn.market.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.entities.Order;
import pn.market.repo.OrderRepo;
import pn.market.services.TService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements TService<Order> {

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public Order create(Order Order) {
        log.info("add Order \n{}\n to db", Order);
        return orderRepo.save(Order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepo.findById(id);
    }

    @Override
    public int createList(List<Order> orderList) {
        log.info("add OrderList \n{}\n to db", orderList);
        return orderRepo.saveAll(orderList).size();
    }

    @Override
    public Optional<Order> getById(Long id) {
        return orderRepo.findById(id);
    }

    @Override
    public List<Order> getAllUnsorted() {
        return orderRepo.findAll();
    }

    @Override
    public List<Order> getAllDSsorted() {
        return orderRepo.getAllOrdersSortedDescById();
    }

    @Override
    public List<Order> getAllASsorted() {
        return orderRepo.getAllOrdersSortedAscById();
    }


    @Override
    public void deleteById(Long id) {
        log.info("try remove Order with ID {} ", id);
        Optional<Order> orderOptional = orderRepo.findById(id);
        if (orderOptional.isPresent()) {
            log.info("remove Order   {} ", orderOptional.get());
            orderRepo.delete(orderOptional.get());
        }
        log.info("No kOrders to remove   ");
    }
}
