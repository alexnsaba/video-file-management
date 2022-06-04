package com.videouploadchallenge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
@Slf4j
public class VideoUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoUploadApplication.class, args);
    }

    @Value("${video.upload.folder}")
    String videoUploadDirectoryPath;

    @Value("${image.upload.folder}")
    String imageUploadDirectoryPath;

    @PostConstruct
    public void createVideoUploadFolder() throws IOException {
        //check if video upload folder has been created
        if (!new File(videoUploadDirectoryPath).exists()) {
            log.info("creating video upload folder..............");
            try {
                Files.createDirectories(Paths.get(videoUploadDirectoryPath));
            } catch (IOException ex) {
                throw new IOException("Error " +ex.getMessage()+" has occurred");
            }
        }
    }

    @PostConstruct
    public void createImageUploadFolder() throws IOException {
        //check if image folder has been created
        if (!new File(imageUploadDirectoryPath).exists()) {
            log.info("creating image upload folder..............");
            try {
                Files.createDirectories(Paths.get(imageUploadDirectoryPath));
            } catch (IOException ex) {
                throw new IOException("Error " +ex.getMessage()+" has occurred");
            }
        }
    }
}
