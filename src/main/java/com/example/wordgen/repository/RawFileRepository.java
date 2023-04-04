package com.example.wordgen.repository;


import com.example.wordgen.model.entity.RawFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author: rich
 * @date: 2023/4/3 15:18
 * @description:
 */
@Repository
public interface RawFileRepository extends JpaRepository<RawFile,Integer> {
    Optional<RawFile> findById(Integer id);
}

