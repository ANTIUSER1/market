package pn.market.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.entities.Item;
import pn.market.repo.ItemRepo;
import pn.market.services.TService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItemServiceImpl implements TService<Item> {
    @Autowired
    private ItemRepo itemRepo;

    @Override
    public Item create(Item item) {
        log.info("add item \n{}\n to db", item);
        return itemRepo.save(item);
    }

    @Override
    public int createList(List<Item> itemList) {
        log.info("add itemList \n{}\n to db", itemList);
      return   itemRepo.saveAll(itemList).size();
    }

    @Override
    public Optional<Item> getById(Long id) {
        return itemRepo.findById(id);
    }

    @Override
    public List<Item> getAllUnsorted() {
        return itemRepo.findAll( );
    }

    @Override
    public List<Item> getAllDSsorted() {
        return itemRepo.getAllItemsSsortedDescById();
    }


    @Override
    public List<Item> getAllASsorted() {
        return itemRepo.getAllItemsSsortedAscById();
    }


    @Override
    public void deleteById(Long id) {
        log.info("try remove item with ID {} ", id);
       Optional<Item> itemOptional=itemRepo.findById(id);
       if(itemOptional.isPresent()){
           log.info("remove item   {} ", itemOptional.get());
           itemRepo.delete(itemOptional.get());
       }
        log.info("No kitems to remove   ");
    }
}
