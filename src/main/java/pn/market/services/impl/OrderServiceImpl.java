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

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements TService<Order> {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ItemServiceImpl itemService;


    @Override
    public Optional<Order> getById(Long id) {
        return  orderRepo.findById(id).blockOptional();
    }

    @Override
    public Page<Order> findAllAndPaging(Pageable pageable) {
        return null;
    }

    public List<Order> findAllOrders() {
        return orderRepo.findAll().collectList().block();
    }

    public void buyOrder(long id) {
        Optional<Order> orderOptional = orderRepo.findById(id).blockOptional();
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            System.out.println("    ORDER TO BUY  GET "+order);

            List<Item> items=itemRepo.findByOrderId(id).collectList().block();
            itemService.setNullOderId(items);
//                 order.setItems(new ArrayList<>());

           // orderRepo.save(order);

            System.out.println("    ORDER TO BUY-- SAVE  "+order);
            orderRepo.delete(order).log().block();

        }
    }
}
