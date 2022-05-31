package com.videouploadchallenge.repository;

import com.videouploadchallenge.dataMapper.VideoSearchInterface;
import com.videouploadchallenge.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<VideoEntity,Long> {

    @Query(value = "SELECT * FROM videos v WHERE v.deleted is false AND v.fileid = :id",nativeQuery = true)
    Optional<VideoEntity> findById(Long id);

    @Query(value = "SELECT * FROM videos v WHERE v.deleted is false",nativeQuery = true)
    List<VideoEntity> findAll();

    @Transactional
    @Modifying
    @Query(value = "update videos set deleted = true, deleted_at=now() where fileid = :id",nativeQuery = true)
    void deleteById(Long id);

    @Query(value = "SELECT * FROM searchVideos(:search_key)",nativeQuery = true)
    List<VideoSearchInterface> searchVideo(@Param("search_key") String search_key);
}
