package pn.market.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class FileService {

    public String storeFile(MultipartFile file, String uploadDir, long id) throws IOException {
        if (file.isEmpty()) {
            return null;
        } else {
            String imgFolderName = "images/";
            String imgPreffix = "im-";
            byte[] fbytes = file.getBytes();
            String fullFileName = file.getOriginalFilename();
            String[] fileNameSplit = fullFileName.split("\\.");
            if (fileNameSplit.length > 0) {
                String fileExt = fileNameSplit[fileNameSplit.length - 1];
                if (isImage(fileExt)) {
                    if (id > 0) {
                        uploadDir = uploadDir + imgFolderName;
                        File fid = new File(uploadDir);
                        if (!fid.exists()) {
                            fid.mkdirs();
                        }
                        File copied = new File(uploadDir + imgPreffix + id + "." + fileExt);
                        Files.write(copied.toPath(), fbytes);
                        return imgFolderName + imgPreffix + id + "." + fileExt;
                    }
                }
            }


            return null;
        }
    }


    public boolean isImage(String ext) {
        return ext.equalsIgnoreCase("jpg") ||
                ext.equalsIgnoreCase("jpeg") ||
                ext.equalsIgnoreCase("png") ||
                ext.equalsIgnoreCase("gif");
    }
}
