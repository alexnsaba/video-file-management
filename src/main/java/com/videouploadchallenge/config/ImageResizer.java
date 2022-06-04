package com.videouploadchallenge.config;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ImageResizer {

    public static void transferResizedImage(MultipartFile sourceImage,int resizedImageWidth, int resizedImageHeight, String resizedImageName, String uploadDirectory) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(sourceImage.getInputStream());

        BufferedImage outPutImage = Scalr.resize(bufferedImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT,resizedImageWidth,resizedImageHeight);

        Path path = Paths.get(uploadDirectory +resizedImageName+ApiResponse.DEFAULT_IMAGE_EXTENSION);

        File newImageFile = path.toFile();

        ImageIO.write(outPutImage, "png", newImageFile);

        outPutImage.flush();
    }
}
