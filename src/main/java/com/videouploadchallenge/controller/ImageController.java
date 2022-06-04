package com.videouploadchallenge.controller;

import com.videouploadchallenge.config.ApiResponse;
import com.videouploadchallenge.exception.ApiException;
import com.videouploadchallenge.service.ImageService;
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
@RequestMapping("/v1/images/")
@Tag(name = "Image", description = "Image upload api")
@Slf4j
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "upload image", description = "Upload a image file", tags = {"Image"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success Response", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "202", description = "Failure Response", content = @Content(schema = @Schema(implementation = ApiException.class))),
    })
    public ResponseEntity<ApiResponse> uploadImage(@RequestParam("file") MultipartFile image) throws IOException {
        log.info("inside uploadImage method of ImageController");

        return ResponseEntity.status(HttpStatus.CREATED).body(imageService.uploadImage(image));
    }

    @DeleteMapping("/{fileid}")
    @Operation(summary = "Delete a image file", description = "Returns delete success message ", tags = {"Image"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Failure operation", content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    public ResponseEntity<ApiResponse> deleteImageFile(@PathVariable("fileid") long fileid) throws IOException {
        log.info("inside deleteImageFile method of ImageController");
        return ResponseEntity.status(HttpStatus.OK).body(imageService.deleteImageFile(fileid));
    }

    @GetMapping("/files/{fileid}")
    @Operation(summary = "Download a image file by fileid", description = "Download a image file by fileid", tags = {"Image"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Failure operation", content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    public ResponseEntity<InputStreamSource> downloadImageFile(@PathVariable("fileid") long fileid) throws IOException {
        log.info("inside downloadImageFile method of ImageController");
        return imageService.downloadImageFile(fileid);
    }

    @GetMapping
    @Operation(summary = "List uploaded files", description = "File list", tags = {"Image"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success Response", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "202", description = "Failure Response", content = @Content(schema = @Schema(implementation = ApiException.class))),
    })
    public ResponseEntity<ApiResponse> listUploadedImageFiles() {
        log.info("inside listUploadedImageFiles method of ImageController");
        return ResponseEntity.status(HttpStatus.OK).body(this.imageService.listUploadedImageFiles());
    }
}
