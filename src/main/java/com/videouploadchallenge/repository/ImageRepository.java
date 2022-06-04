package com.videouploadchallenge.repository;

import com.videouploadchallenge.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageEntity,Long> {

    @Query(value = "SELECT * FROM images i WHERE i.deleted is false AND i.fileid = :id",nativeQuery = true)
    Optional<ImageEntity> findById(Long id);

    @Query(value = "SELECT * FROM images i WHERE i.deleted is false",nativeQuery = true)
    List<ImageEntity> findAll();

    @Transactional
    @Modifying
    @Query(value = "update images set deleted = true, deleted_at=now() where fileid = :id",nativeQuery = true)
    void deleteById(Long id);
}
