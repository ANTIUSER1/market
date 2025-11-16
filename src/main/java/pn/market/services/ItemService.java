package pn.market.services;

import org.springframework.data.jpa.repository.Query;
import pn.market.entities.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

     Item createItem(Item item);
    int createItemList(List<Item>  itemList);
    Optional<Item> getItemById(Long id);

     List<Item> getAllItemsUnsorted();
    List<Item> getAllItemsDSsorted();
    List<Item> getAllItemsASsorted();

    void deleteItemById(Long id);

}
