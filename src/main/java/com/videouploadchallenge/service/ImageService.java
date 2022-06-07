package com.videouploadchallenge.service;

import com.videouploadchallenge.config.ApiResponse;
import com.videouploadchallenge.config.ConfigurationConstants;
import com.videouploadchallenge.config.FileScanner;
import com.videouploadchallenge.config.ImageResizer;
import com.videouploadchallenge.entity.ImageEntity;
import com.videouploadchallenge.exception.CorruptFileException;
import com.videouploadchallenge.exception.FilesNotFoundException;
import com.videouploadchallenge.exception.InvalidFileFormatException;
import com.videouploadchallenge.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    FileScanner fileScanner;

    private ApiResponse result;

    @Value("${image.upload.folder}")
    String uploadDir;

    @Value("${image.resize.width}")
    private int resizedImageWidth;

    @Value("${image.resize.height}")
    private int resizedImageHeight;

    // Upload a image file
    public ApiResponse uploadImage(MultipartFile image) throws IOException {
        log.info("inside uploadImage method of ImageService");
        ImageEntity savedImageEntity = null;

        String imageName = System.currentTimeMillis() + ApiResponse.DEFAULT_IMAGE_PREFIX + ApiResponse.DEFAULT_IMAGE_EXTENSION;

        if (image.getContentType().contains("image/") && image.getSize() > 0) {


            //compress image
            //ImageResizer.transferResizedImage(image,resizedImageWidth,resizedImageHeight,imageName,uploadDir);

           ImageEntity scannedImage = fileScanner.scanFileForVirusesAndSave(ConfigurationConstants.SCANII_API_KEY, ConfigurationConstants.SCANII_SECRET, image);

            savedImageEntity = imageRepository.save(scannedImage);

        } else {
            throw new InvalidFileFormatException("Invalid Image Format");
        }

        if (Objects.nonNull(savedImageEntity)) {
            result = new ApiResponse(ApiResponse.CREATION_SUCCESS_MESSAGE, null);
        } else {
            result = new ApiResponse(ApiResponse.OPERATION_FAILURE_MESSAGE, null);
        }
        return result;
    }

    //Delete a image file

    public ApiResponse deleteImageFile(long fileId) throws IOException {
        log.info("inside deleteImageFile method of ImageService");
        ImageEntity retrievedImage = imageRepository.findById(fileId).orElseThrow(() -> new FilesNotFoundException("File not found"));
        if (Objects.nonNull(retrievedImage)) {
            imageRepository.deleteById(fileId);
            result = new ApiResponse(ApiResponse.DELETION_SUCCESS_MESSAGE, null);
        }
        return result;
    }

    //Download a image file
    public ResponseEntity<InputStreamSource> downloadImageFile(long fileId) throws IOException {
        log.info("inside downloadImageFile method of ImageService");
        ImageEntity retrievedImage = imageRepository.findById(fileId).orElseThrow(() -> new FilesNotFoundException("File not found"));
        String filename = retrievedImage.getImageName();

        File file = new File(uploadDir+filename);

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "attachment;filename=" + filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).
                contentType(MediaType.parseMediaType("image/png")).body(resource);
    }

    //List uploaded files
    public ApiResponse listUploadedImageFiles(){
        log.info("inside listUploadedImageFiles method of ImageService");
        List<ImageEntity> uploadedImageFiles = imageRepository.findAll();

        if (!ObjectUtils.isEmpty(uploadedImageFiles)) {
            result = new ApiResponse(ApiResponse.RETRIEVE_SUCCESS_MESSAGE, uploadedImageFiles);
        } else {
            result = new ApiResponse(ApiResponse.NO_RECORD_FOUND_MESSAGE, null);
        }
        return result;
    }
}
