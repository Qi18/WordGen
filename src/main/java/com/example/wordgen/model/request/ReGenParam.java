package com.example.wordgen.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: rich
 * @date: 2023/4/4 17:51
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReGenParam {
    Integer id;
    String index;
    String content;
}
