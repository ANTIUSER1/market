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
            byte[] bytes = file.getBytes();
            String fullFileName = file.getOriginalFilename();
            System.out.println("***\nfullFileName = " + fullFileName + "\n***");
           fullFileName = fullFileName.replace("\\", "/");
            String[] fileNameSplit = fullFileName.split("/");
            if (fileNameSplit.length > 1) {
               String fileName = fileNameSplit[fileNameSplit.length - 1];
             System.out.println("***\nfileName = " + fileName + "\n***");
            }


            return file.getOriginalFilename();
        }
    }
}
