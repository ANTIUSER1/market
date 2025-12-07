package pn.market.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pn.market.entities.Item;
import pn.market.repo.ItemRepo;
import pn.market.services.TService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItemServiceImpl implements TService<Item> {

    @Value("${spring.web.resources.static-locations}")
    private String imgPath;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private ItemRepo itemRepo;

    @Override
    public Optional<Item> getById(Long id) {
        return Optional.empty();
                //itemRepo.findById(id);
    }

    @Override
    public Page<Item> findAllAndPaging(Pageable pageable) {
        List<Item> items = itemRepo.findAll().collectList().block();
//System.out.println(items);
Page<Item> page = new PageImpl<>(items, pageable, items.size());
System.out.println(page);
System.out.println(
        "\n CONTENT  "+ page.getContent() +
        "\nPAGE NUM "+ page.getNumber() +
        "\nTOTAL PAGES "+ page.getTotalPages() + " " +
        "\nPG TOTAL ELEM "+  page.getTotalElements()+
        "\n CONTENT SIZE "+page.getContent().size()

);
        return page;
    }

    public Item plus(Item item) {
        item.plusCount();
        return null;
    }

    public Item minus(Item item) {
        item.minusCount();
        return null;
    }

    public Item uploadFile(MultipartFile file, long id) throws IOException {
//        Optional<Item> itemOptional = itemRepo.findById(id);
//        Item item = null;
//        if (itemOptional.isPresent()) {
//            item = itemOptional.get();
//            String dirToUpload = createImagePath();
//            String fileName = fileService.storeFile(file, dirToUpload, id);
//            if (fileName != null) {
//                item.setImgPath(fileName);
//            }
//        }
        return null;
    }

    private String createImagePath() {
        imgPath = imgPath.split("file:")[1]
                .replace("//", "/")
                .replace(",", "");

        return imgPath;
    }
}
