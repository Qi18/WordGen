package com.example.wordgen;

import com.example.wordgen.util.ParseWordUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ParseWordUtil.class})
class WordGenApplicationTests {

    @Test
    void contextLoads() {
        ParseWordUtil.parse("C:\\Users\\rich\\Desktop\\2023-标书制作\\技术规范书-学员数字人工具开发.docx");
    }

}
