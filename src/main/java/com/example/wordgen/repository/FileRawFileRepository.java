package com.example.wordgen.repository;

import com.example.wordgen.model.entity.FileRawFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author: rich
 * @date: 2023/4/4 17:56
 * @description:
 */
public interface FileRawFileRepository extends JpaRepository<FileRawFile,Integer> {
    Optional<FileRawFile> findByRawFileId(Integer RawTextId);
}
