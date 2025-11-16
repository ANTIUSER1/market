package pn.market.repo.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.entities.Item;
import pn.market.repo.ItemRepo;
import pn.market.services.ItemService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepo itemRepo;

    @Override
    public Item createItem(Item item) {
        log.info("add item \n{}\n to db", item);
        return itemRepo.save(item);
    }

    @Override
    public int createItemList(List<Item> itemList) {
        log.info("add itemList \n{}\n to db", itemList);
      return   itemRepo.saveAll(itemList).size();
    }

    @Override
    public Optional<Item> getItemById(Long id) {
        return itemRepo.findById(id);
    }

    @Override
    public List<Item> getAllItemsUnsorted() {
        return itemRepo.findAll( );
    }

    @Override
    public List<Item> getAllItemsDSsorted() {
       return itemRepo.getAllItemsSsortedDescById();
    }

    @Override
    public List<Item> getAllItemsASsorted() {
        return itemRepo.getAllItemsSsortedAscById();
    }


    @Override
    public void deleteItemById(Long id) {
        log.info("try remove item with ID {} ", id);
       Optional<Item> itemOptional=itemRepo.findById(id);
       if(itemOptional.isPresent()){
           log.info("remove item   {} ", itemOptional.get());
           itemRepo.delete(itemOptional.get());
       }
        log.info("No kitems to remove   ");
    }
}
