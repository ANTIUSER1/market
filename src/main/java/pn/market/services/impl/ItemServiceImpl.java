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
    private static final String SORT_ALPHA = "ALPHA" ;
    private static final String SORT_PRICE = "PRICE";


    @Autowired
    private ItemRepo itemRepo;

    @Override
    public Item create(Item item) {
        log.info("add item \n{}\n to db", item);
        return itemRepo.save(item);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepo.findById(id);
    }

    @Override
    public int createList(List<Item> itemList) {
        log.info("add itemList \n{}\n to db", itemList);
        return itemRepo.saveAll(itemList).size();
    }

    @Override
    public Optional<Item> getById(Long id) {
        return itemRepo.findById(id);
    }

    @Override
    public List<Item> findAll(int page, int pageSize, String search, String sorted) {

        System.out.println("\n---\n     SORTED --- "+sorted+"\n---");
        System.out.println("\n---\n     SORTED --- "+ ( SORT_ALPHA.equalsIgnoreCase(sorted.trim())) +"\n---");
   if(SORT_ALPHA.equalsIgnoreCase(sorted.trim())){

       System.out.println("\n---\n SORT-BY-TITLE\n   \n---");
      return itemRepo.findAllAndSortByTitle(page, pageSize, search);

      }
       else if(SORT_PRICE.equalsIgnoreCase(sorted.trim()))
      return itemRepo.findAllAndSortByPrice(page, pageSize, search);
       else
          return itemRepo.findAllNoSort(page, pageSize, search);
    }



    @Override
    public List<Item> getAllUnsorted() {
        return itemRepo.findAll();
    }

    @Override
    public List<Item> getAllDSsorted() {
        return itemRepo.getAllItemsSortedDescById();
    }


    @Override
    public List<Item> getAllASsorted() {
        return itemRepo.getAllItemsSortedAscById();
    }


    @Override
    public void deleteById(Long id) {
        log.info("try remove item with ID {} ", id);
        Optional<Item> itemOptional = itemRepo.findById(id);
        if (itemOptional.isPresent()) {
            log.info("remove item   {} ", itemOptional.get());
            itemRepo.delete(itemOptional.get());
        }
        log.info("No kitems to remove   ");
    }
}
