package com.videouploadchallenge.config;

import com.uvasoftware.scanii.ScaniiClient;
import com.uvasoftware.scanii.ScaniiClients;
import com.uvasoftware.scanii.models.ScaniiProcessingResult;
import com.videouploadchallenge.entity.ImageEntity;
import com.videouploadchallenge.exception.CorruptFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Slf4j
@Component
public class FileScanner {
    @Value("${image.upload.folder}")
     String uploadDir;

    public ImageEntity scanFileForVirusesAndSave(String apiKey, String secret, MultipartFile image) throws IOException {
        log.info("Inside scanFileForViruses method of FileScanner class");

        boolean fileHasViruses = true;
        ImageEntity imageEntity = new ImageEntity();

        String fileName = image.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        File file = File.createTempFile(fileName, suffix);

        image.transferTo(file);

        ScaniiClient client = ScaniiClients.createDefault(apiKey, secret);
        ScaniiProcessingResult scanResult =  client.process( Paths.get(file.getPath()));

        if (scanResult.getFindings().isEmpty()) {

            String imageName = System.currentTimeMillis() + ApiResponse.DEFAULT_IMAGE_PREFIX + ApiResponse.DEFAULT_IMAGE_EXTENSION;

            imageEntity.setImageName(imageName);
            imageEntity.setImageSize((int) image.getSize());
            imageEntity.setCreatedAt(LocalDateTime.now());

            Path targetPath = Paths.get(uploadDir + imageName);
            Files.copy(Paths.get(file.getPath()),targetPath);

            return imageEntity;
        }else{
            throw new CorruptFileException(ApiResponse.FILE_SCAN_MALWARE_DETECTED);
        }

    }
}
