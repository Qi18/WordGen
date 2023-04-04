package com.example.demo.util;

import com.alibaba.fastjson.JSON;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author: rich
 * @date: 2023/4/4 11:51
 * @description:
 */
@Converter
public class JpaConverterListJson  implements AttributeConverter<Object, String> {
    @Override
    public String convertToDatabaseColumn(Object o) {
        return JSON.toJSONString(o);
    }

    @Override
    public Object convertToEntityAttribute(String s) {
        return JSON.parseArray(s);
    }
}
