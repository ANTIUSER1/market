package pn.market.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pn.market.entities.Item;
import pn.market.repo.ItemRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemServiceImplNegativeTest {


    private MultipartFile file;
    private String fileName;
    private String fileNameTarget;
    private String ext;
    private Item item;
    private String imgPath;
private Long id;

    @Mock
    private ItemRepo itemRepo;
    @Mock
    private ItemServiceImpl itemService;

    @BeforeEach
    void init() {
        fileName = "1.jpg";
        fileNameTarget = "t-1.jpg";
        ext = "op";

        file = new MockMultipartFile(fileName,  "image/jpeg".getBytes() );
        id = 1L;

        item = new Item();
        item.setId(1L);
        imgPath = "path"    ;
    }


    @Test
    void getById() {
        when ( itemRepo.findById(1L) ).thenReturn(Optional.ofNullable(item));
        when ( itemService.getById(1L) ).thenReturn(Optional.ofNullable(item));
        assertFalse( itemService.getById(1L).isEmpty());
    }

    @Test
    void findAllAndPaging() {
        when(itemRepo.findAll()).thenReturn(new ArrayList<>());
        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> page =  Page.empty( pageable );
        when(itemService.findAllAndPaging(pageable)).thenReturn(page);
        assertFalse(itemService.findAllAndPaging(pageable).getTotalPages() == 10);
    }

    @Test
    void plus() {
        item.setCount(1);
        when(itemService.plus(item)).thenReturn(item);
        assertNotEquals(100, itemService.plus(item).getCount());

    }

    @Test
    void minus() {
        item.setCount(2);
        when(itemService.plus(item)).thenReturn(item);
        assertNotEquals(100, itemService.plus(item).getCount());
    }

    @Test
    void uploadFile() throws IOException {
        item.setImgPath(imgPath);
        when(itemService.uploadFile(file,id)).thenReturn(item);
        assertNotEquals( null, itemService.uploadFile(file,id).getImgPath());
    }
}