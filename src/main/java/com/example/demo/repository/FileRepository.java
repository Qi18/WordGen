package com.example.demo.repository;


import com.example.demo.model.entity.File;
import com.example.demo.model.entity.HistoryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

/**
 * @author: rich
 * @date: 2023/4/3 15:18
 * @description:
 */
@Repository
public interface FileRepository extends JpaRepository<File,Integer> {
    Optional<File> findById(Integer fileId);
}