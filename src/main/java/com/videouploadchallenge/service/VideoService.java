package com.videouploadchallenge.service;

import com.videouploadchallenge.config.ApiResponse;
import com.videouploadchallenge.dataMapper.FileSearchInterface;
import com.videouploadchallenge.entity.VideoEntity;
import com.videouploadchallenge.exception.InvalidFileFormatException;
import com.videouploadchallenge.exception.FilesNotFoundException;
import com.videouploadchallenge.repository.VideoRepository;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    private ApiResponse result;

    @Value("${video.upload.folder}")
    String uploadDir;

    // Upload a video file
    public ApiResponse uploadVideo(MultipartFile video) throws IOException {
        log.info("inside uploadVideo method of VideoService");
        VideoEntity videoEntity = new VideoEntity();
        VideoEntity savedVideoEntity = null;

        String name = System.currentTimeMillis() + "-" + video.getOriginalFilename();

        if (video.getContentType().contains("video/") && video.getSize() > 0) {

            Path path = Paths.get(uploadDir + name);
            video.transferTo(path);

            videoEntity.setName(name);
            videoEntity.setSize((int) video.getSize());
            videoEntity.setCreated_at(LocalDateTime.now());

            savedVideoEntity = videoRepository.save(videoEntity);

        } else {
            throw new InvalidFileFormatException("Invalid Video Format");
        }

        if (Objects.nonNull(savedVideoEntity)) {
            result = new ApiResponse(ApiResponse.CREATION_SUCCESS_MESSAGE, null);
        } else {
            result = new ApiResponse(ApiResponse.OPERATION_FAILURE_MESSAGE, null);
        }
        return result;
    }

    //Delete a video file

    public ApiResponse deleteVideoFile(long fileid) throws IOException {
        log.info("inside deleteVideoFile method of VideoService");
        VideoEntity retrievedVideo = videoRepository.findById(fileid).orElseThrow(() -> new FilesNotFoundException("File not found"));
        if (Objects.nonNull(retrievedVideo)) {
            videoRepository.deleteById(fileid);
            result = new ApiResponse(ApiResponse.DELETION_SUCCESS_MESSAGE, null);
        }
        return result;
    }

    //Download a video file
    public ResponseEntity<InputStreamSource> downloadVideoFile(long fileid) throws IOException {
        log.info("inside downloadVideoFile method of VideoService");
        VideoEntity retrievedVideo = videoRepository.findById(fileid).orElseThrow(() -> new FilesNotFoundException("File not found"));
        String filename = retrievedVideo.getName();

        File file = new File(uploadDir+filename);

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "attachment;filename=" + filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).
                contentType(MediaType.parseMediaType("video/mp4")).body(resource);
    }

    //List uploaded files
    public ApiResponse listUploadedFiles() {
        log.info("inside listUploadedFiles method of VideoService");
        List<VideoEntity> uploadedFiles = videoRepository.findAll();

        if (!ObjectUtils.isEmpty(uploadedFiles)) {
            result = new ApiResponse(ApiResponse.RETRIEVE_SUCCESS_MESSAGE, uploadedFiles);
        } else {
            result = new ApiResponse(ApiResponse.NO_RECORD_FOUND_MESSAGE, null);
        }
        return result;
    }

    // Video searching by name, duration and when uploaded
    public ApiResponse searchVideos(String search_key) {
        log.info("inside searchVideos method of VideoService");
        List<FileSearchInterface> foundVideos = videoRepository.searchVideo(search_key);
        if (!ObjectUtils.isEmpty(foundVideos)) {
            result = new ApiResponse(ApiResponse.RETRIEVE_SUCCESS_MESSAGE, foundVideos);
        } else {
            result = new ApiResponse(ApiResponse.NO_RECORD_FOUND_MESSAGE, null);
        }
        return result;
    }
}
