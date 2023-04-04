package com.example.wordgen.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: rich
 * @date: 2023/4/4 17:56
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "file_rawfile")
@Builder
public class FileRawFile {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "file_id")
    private Integer fileId;

    @Column(name = "rawfile_id")
    private Integer rawFileId;
}
