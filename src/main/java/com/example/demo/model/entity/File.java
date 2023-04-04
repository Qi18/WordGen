package com.example.demo.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author: rich
 * @date: 2023/4/3 15:18
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tbl_file")
public class File {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String understanding;

    private String schedule;

    private String quality;

    private String commitment;

    private String back;
}
