package com.example.wordgen.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * @author: rich
 * @date: 2023/4/4 12:05
 * @description:
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ApiError {
    String message;
    List<Detail> details;

    public ApiError(String message) {
        this(message, new LinkedList<>());
    }

    public ApiError(String message, List<Detail> details) {
        this.message = message;
        this.details = details;
    }

    public void addDetail(String message, String code, String field) {
        if (details != null) {
            details.add(new Detail(message, code, field));
        }
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "message='" + message + '\'' +
                ", details=" + details +
                '}';
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Detail {
    String message;
    String code;
    String field;

    @Override
    public String toString() {
        return "Detail{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", field='" + field + '\'' +
                '}';
    }
}