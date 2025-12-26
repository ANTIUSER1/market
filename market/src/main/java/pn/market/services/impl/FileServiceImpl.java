package pn.market.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pn.market.error.FileException;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class FileServiceImpl {

    public Mono<String> storeFile(
            MultipartFile file, String uploadDir, long id) throws IOException, FileException {
        if (file.isEmpty() || id <= 0) {
            throw new IOException("Failed to store empty file " + file.getOriginalFilename());
        }
        String fullFileName = file.getOriginalFilename();
        String[] fileNameSplit = fullFileName.split("\\.");
        if (fileNameSplit.length > 0) {
            String fileExt = fileNameSplit[fileNameSplit.length - 1];
            if (isImage(fileExt)) {
                return uploadMechanizm(file, uploadDir, fileExt, id);
            } else {
                throw new FileException("Invalid file type");
            }
        } else {
            throw new FileException("Invalid file type");
        }

    }

    private Mono<String> uploadMechanizm(
            MultipartFile file, String uploadDir, String fileExt, long id) {
        String imgFolderName = "images/";
        AtomicReference<String> result = new AtomicReference<>("");// imgFolderName + imgPreffix + id + "." + fileExt;
        String uplDir = uploadDir + imgFolderName;
        return Mono.just(new File(uploadDir)).map(
                        f -> {//creating folder if necessary
                            if (!f.exists()) {
                                f.mkdirs();

                            }
                            return f;
                        })
                .map(f -> {// createing file to copy
                    String imgPreffix = "im-";
                    result.set(imgFolderName + imgPreffix + id + "." + fileExt);
                    return Mono.just(new File(uploadDir + imgPreffix + id
                            + "." + fileExt));
                }).flatMap(f -> f)
                .map(f -> {// writing file
                    try {
                        Files.write(f.toPath(), file.getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return result.get();
                });
    }

    public boolean isImage(String ext) {
        return ext.equalsIgnoreCase("jpg") ||
                ext.equalsIgnoreCase("jpeg") ||
                ext.equalsIgnoreCase("png") ||
                ext.equalsIgnoreCase("gif");
    }

}
