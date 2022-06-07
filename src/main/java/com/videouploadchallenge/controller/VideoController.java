package com.videouploadchallenge.controller;

import com.videouploadchallenge.config.ApiResponse;
import com.videouploadchallenge.exception.ApiException;
import com.videouploadchallenge.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@Tag(name = "Video", description = "Video Upload api")
@RequestMapping("/v1/videos/")
@Slf4j
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "upload video", description = "Upload a video file", tags = {"Video"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success Response", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "202", description = "Failure Response", content = @Content(schema = @Schema(implementation = ApiException.class))),
    })
    public ResponseEntity<ApiResponse> uploadVideo(@RequestParam("file") MultipartFile video) throws IOException {
        log.info("inside uploadVideo method of VideoController");

        return ResponseEntity.status(HttpStatus.CREATED).body(videoService.uploadVideo(video));
    }

    @DeleteMapping("/{fileId}")
    @Operation(summary = "Delete a video file", description = "Returns delete success message ", tags = {"Video"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Failure operation", content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    public ResponseEntity<ApiResponse> deleteVideoFile(@PathVariable("fileId") long fileId) throws IOException {
        log.info("inside deleteVideoFile method of VideoController");
        return ResponseEntity.status(HttpStatus.OK).body(videoService.deleteVideoFile(fileId));
    }

    @GetMapping("/files/{fileId}")
    @Operation(summary = "Download a video file by fileId", description = "Download a video file by fileId", tags = {"Video"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Failure operation", content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    public ResponseEntity<InputStreamSource> downloadVideoFile(@PathVariable("fileId") long fileId) throws IOException {
        log.info("inside downloadVideoFile method of VideoController");
        return videoService.downloadVideoFile(fileId);
    }

    @GetMapping
    @Operation(summary = "List uploaded files", description = "File list", tags = {"Video"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success Response", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "202", description = "Failure Response", content = @Content(schema = @Schema(implementation = ApiException.class))),
    })
    public ResponseEntity<ApiResponse> listUploadedFiles() {
        log.info("inside listUploadedFiles method of VideoController");
        return ResponseEntity.status(HttpStatus.OK).body(this.videoService.listUploadedFiles());
    }

    @GetMapping("/search")
    @Operation(summary = "search videos", description = "File list", tags = {"Video"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success Response", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "202", description = "Failure Response", content = @Content(schema = @Schema(implementation = ApiException.class))),
    })
    public ResponseEntity<ApiResponse> searchVideos(@RequestParam("search_key") String search_key) {
        log.info("inside searchVideos method of VideoController");
        return ResponseEntity.status(HttpStatus.OK).body(this.videoService.searchVideos(search_key));
    }
}
