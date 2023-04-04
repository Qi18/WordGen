package com.example.wordgen.model.entity;


import com.example.wordgen.util.JpaConverterListJson;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: rich
 * @date: 2023/4/3 15:18
 * @description:
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "tbl_rawfile")
public class RawFile {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String post;

    @Column
    private String survey;

    @Convert(converter = JpaConverterListJson.class)
    @Column(name = "purchase_ranges")
    private List<String> purchaseRanges;

    @Column
    private String period;

    @Convert(converter = JpaConverterListJson.class)
    private List<String> service;

    @Column
    private String manpower;

    @Column
    private String achievement;

    @Column
    private String acceptance;

    @Column
    private String payment;

    @Column
    private String ownership;

    @Convert(converter = JpaConverterListJson.class)
    private List<String> other;

    @Column
    private String attachment;

    public RawFile() {
        this.purchaseRanges = new ArrayList<>();
        this.other = new ArrayList<>();
        this.service = new ArrayList<>();
    }
}

