package pn.market.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
public class FileServiceImpl {

    public Mono<String> storeFile(MultipartFile file, String uploadDir, long id) throws IOException {
        if (file.isEmpty()) {
            return null;
        } else {
            Mono<Boolean> dirCreated = Mono.just(false);
            Mono<Path> monoPath = Mono.just(null);
            String imgFolderName = "images/";
            String imgPreffix = "im-";
            String fullFileName = file.getOriginalFilename();
            byte[] fbytes = Mono.just(file.getBytes()).block();

            String[] fileNameSplit = fullFileName.split("\\.");
            if (fileNameSplit.length > 0) {
                String fileExt = fileNameSplit[fileNameSplit.length - 1];
                if (isImage(fileExt)) {
                    if (id > 0) {
                        uploadDir = uploadDir + imgFolderName;
                        File fid = Mono.just(new File(uploadDir)).block();
                        if (!fid.exists()) {
                            dirCreated = Mono.just(fid.mkdirs());
                        }
                        if (dirCreated.block()) {
                            File copied =
                                    Mono.just(new File(uploadDir + imgPreffix + id + "." + fileExt))
                                            .block();
                            monoPath = Mono.just(Files.write(copied.toPath(), fbytes));
                        }
                        if (monoPath.block() != null) {
                            return
                                    Mono.just(imgFolderName + imgPreffix + id + "." + fileExt);
                        }else {
                            return null;
                        }
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
