package com.videouploadchallenge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long imageId;

    @Column(nullable = false)
    @Schema(required = true, example = "image.png")
    private String imageName;

    @Column(nullable = false)
    @Schema(required = true, example = "24")
    private int imageSize;

    @Schema(required = true, example = "24-05-2022 00:00")
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonIgnore
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    @JsonIgnore
    private boolean deleted = false;
}
