package com.example.demo.repository;

import com.example.demo.entity.ImageMetadata;

import com.example.demo.entity.User;
import com.example.demo.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImageMetadataRepository extends JpaRepository<ImageMetadata, Long> {

    @Modifying
    @Query("delete from ImageMetadata im WHERE im.username = :username and im.fileId = :fileId")
    Optional<ImageMetadata> findUserByUserNameAndFileId(@Param("username") String userName,
                                                       @Param("fileId") String fileId);
}
