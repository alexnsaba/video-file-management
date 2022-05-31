package com.videouploadchallenge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "videos")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long fileid;

    @Column(nullable = false)
    @Schema(required = true, example = "Video.mp4")
    private String name;

    @Column(nullable = false)
    @Schema(required = true, example = "24")
    private int size;

    @Schema(required = true, example = "24-05-2022 00:00")
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime created_at;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonIgnore
    private LocalDateTime deleted_at;

    @Column(nullable = false)
    @JsonIgnore
    private boolean deleted = false;
}
