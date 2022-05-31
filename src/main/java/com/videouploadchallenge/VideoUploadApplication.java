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
    String uploadDirectoryPath;

    @PostConstruct
    public void createVideoUploadFolder() throws IOException {
        //check if upload folder has been created
        if (!new File(uploadDirectoryPath).exists()) {
            log.info("creating video upload folder..............");
            try {
                Files.createDirectories(Paths.get(uploadDirectoryPath));
            } catch (IOException ex) {
                throw new IOException("Error " +ex.getMessage()+" has occurred");
            }
        }
    }
}
