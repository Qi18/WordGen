package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: rich
 * @date: 2023/4/4 16:59
 * @description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_history")
@Builder
public class HistoryInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rawfile_id")
    private Integer rawFileId;

    private String history;
}
