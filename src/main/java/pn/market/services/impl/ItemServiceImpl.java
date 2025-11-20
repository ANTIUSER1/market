package pn.market.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Optional<Item> getById(Long id) {
        return itemRepo.findById(id);
    }


    @Override
    public Page<Item> findAllAndPaging(Pageable pageable) {
        return itemRepo.findAll(pageable);
    }

}