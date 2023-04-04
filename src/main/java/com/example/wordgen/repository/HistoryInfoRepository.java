package com.example.wordgen.repository;

import com.example.wordgen.model.entity.HistoryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author: rich
 * @date: 2023/4/4 17:08
 * @description:
 */
public interface HistoryInfoRepository extends JpaRepository<HistoryInfo,Integer> {
    Optional<HistoryInfo> findByRawFileId(Integer rawFileId);
}
