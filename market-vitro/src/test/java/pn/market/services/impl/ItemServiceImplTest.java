package pn.market.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pn.market.repo.ItemRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemServiceImplTest {


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
    private List<Item> items;

    @BeforeEach
    void init() {
        fileName = "1.jpg";
        fileNameTarget = "t-1.jpg";
        ext = "jpg";

        file = new MockMultipartFile(fileName, "image/jpeg".getBytes());
        id = 1L;

        imgPath = "path";
        item = new Item();
        item.setId(1L);
        item.setPrice(100L);
        item.setImgPath(imgPath);
        items = List.of(item);
    }

    @Test
    void findAllAndPagingTest() {
        when(itemRepo.findAll()).thenReturn(Flux.empty());
        when(itemService.findAllAndPaging(Mono.empty())).thenReturn(Mono.empty());
        assertEquals(Mono.empty(), itemService.findAllAndPaging(Mono.empty()));
    }

    @Test
    void getItemsEmptyByCartTest() {
        when(itemRepo.findByCartId(1L)).thenReturn(Flux.empty());
        when(itemService.getItemsByCart(1L)).thenReturn(Flux.empty());
        assertEquals(Flux.empty(), itemService.getItemsByCart(1L));
    }

    @Test
    void getItemsByCartTest() {
        when(itemRepo.findByCartId(1L)).thenReturn(Flux.fromIterable(items));
        when(itemService.getItemsByCart(1L)).thenReturn(Flux.fromIterable(items));
        assertEquals(Flux.just(items).collectList().blockOptional().get().get(0).get(0),
                itemService.getItemsByCart(1L).collectList().blockOptional().get().get(0));

        StepVerifier.create(itemService.getItemsByCart(1L))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void getItemsByOrderIdTest() {
        when(itemRepo.findByOrderId(1L)).thenReturn(Flux.empty());
        when(itemService.getItemsByOrderId(1L)).thenReturn(Flux.empty());
        assertEquals(Flux.empty(), itemService.getItemsByOrderId(1L));
    }

    @Test
    void getTotalSumTest() {
        when(itemService.getTotalSum(Flux.empty())).thenReturn(Mono.just(100L));
        assertEquals(100L, itemService.getTotalSum(Flux.empty()).block());
    }

    @Test
    void plusForMono() {
        when(itemService.plusForMono(item, 1L)).thenReturn(Mono.just(item));
        assertEquals(Mono.just(item).blockOptional().get(),
                itemService.plusForMono(item, 1L).blockOptional().get());


    }

    @Test
    void minusForMono() {
        when(itemService.minusForMono(item)).thenReturn(Mono.just(item));
        assertEquals(Mono.just(item).blockOptional().get(),
                itemService.minusForMono(item).blockOptional().get());
    }

    @Test
    void uploadFileTest() throws IOException {
        when(itemService.uploadFileMono(file, id)).thenReturn(Mono.just(item));
        assertEquals(item.getImgPath(), itemService.uploadFileMono(file, id).blockOptional().get().getImgPath());
    }

    @Test
    void createImagePathest() {
        when(itemService.createImagePath()).thenReturn(fileNameTarget);
        assertEquals(fileNameTarget, itemService.createImagePath());
    }

    @Test
    void addToCartTest() {
        when(itemService.addToCart(item, 1L, "a")).thenReturn(Mono.just(item));
        assertEquals(Mono.just(item).blockOptional().get(),
                itemService.addToCart(item, 1L, "a").blockOptional().get());
    }

    @Test
    void saveTest() {
        when(itemRepo.save(item)).thenReturn(Mono.empty());
        when(itemService.save(item)).thenReturn(Mono.empty());
        assertEquals(Mono.empty(), itemService.save(item));
    }

    @Test
    void getItemsByCartIdToFluxTest() {
        when(itemRepo.findByCartId(1L)).thenReturn(Flux.empty());
        when(itemService.getItemsByCartIdToFlux(1L)).thenReturn(Flux.empty());
        assertEquals(Flux.empty(), itemService.getItemsByCartIdToFlux(1L));

    }
}