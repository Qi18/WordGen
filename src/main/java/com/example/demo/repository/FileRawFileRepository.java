package com.example.demo.repository;

import com.example.demo.model.entity.File;
import com.example.demo.model.entity.FileRawFile;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

/**
 * @author: rich
 * @date: 2023/4/4 17:56
 * @description:
 */
public interface FileRawFileRepository extends JpaRepository<FileRawFile,Integer> {
    Optional<FileRawFile> findByRawFileId(Integer RawTextId);
}
