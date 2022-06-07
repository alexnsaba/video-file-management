package com.videouploadchallenge.repository;

import com.videouploadchallenge.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageEntity,Long> {

    @Query(value = "SELECT * FROM images i WHERE i.deleted IS FALSE AND i.image_id = :imageId",nativeQuery = true)
    Optional<ImageEntity> findById(long imageId);

    @Query(value = "SELECT * FROM images i WHERE i.deleted IS FALSE",nativeQuery = true)
    List<ImageEntity> findAll();

    @Transactional
    @Modifying
    @Query(value = "UPDATE images SET deleted IS TRUE, deleted_at = NOW() WHERE image_id = :imageId",nativeQuery = true)
    void deleteById(long imageId);
}
