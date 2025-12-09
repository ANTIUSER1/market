package pn.market.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.Mockito.when;

@SpringBootTest
class FileServiceImplTest {

    private MultipartFile file;
    private String fileName;
    private String fileNameTarget;
    private String ext;
    private long id;

    @Mock
    private FileServiceImpl fileService;

    @BeforeEach
    void init() {
        fileName = "1.jpg";
        fileNameTarget = "t-1.jpg";
        ext = "jpg";
        file = new MockMultipartFile(fileName, "image/jpeg".getBytes());
        id = 1;
    }

    @Test
    void storeFile() throws IOException {
        when(fileService.storeFile(file, fileName, id)).thenReturn(fileNameTarget);
        assertEquals(fileNameTarget, fileService.storeFile(file, fileName, id));
    }


    @Test
    void isImage() {
        when(fileService.isImage(ext)).thenReturn(true);
        assertTrue(fileService.isImage(ext));
    }
}