package com.videouploadchallenge.config;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    @PostConstruct
    public void testCompressImage() throws IOException {
        File input = new File("C:/_PROJECTS/videoupload/image-uploads/big_ben_clockface_192640.jpg");
        BufferedImage image = ImageIO.read(input);

        File output = new File("C:/_PROJECTS/videoupload/image-uploads/compressedClock2.png");
        OutputStream out = new FileOutputStream(output);

        ImageWriter writer =  ImageIO.getImageWritersByFormatName("jpg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(out);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()){
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.04f);
        }

        writer.write(null, new IIOImage(image, null, null), param);

        out.close();
        ios.close();
        writer.dispose();
        System.out.println("done................");
    }
}
