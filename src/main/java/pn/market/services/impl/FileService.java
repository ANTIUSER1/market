package pn.market.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    public String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        } else {
            String fileName = file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            System.out.println(fileName);


            return file.getOriginalFilename();
        }
    }
}
