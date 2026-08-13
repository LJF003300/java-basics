package com.ljf.learning.day10;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWritePractice {
    public  static void main(String[] args) {
        Path path = Path.of(
            "data",
                "day10-note.txt"
        );
        try{
            Files.createDirectories(path.getParent());
            Files.writeString(
                    path,
                    "Java 文件写入练习",
                    StandardCharsets.UTF_8
            );
            System.out.println("文件位置：" + path.toAbsolutePath());
            System.out.println("写入成功");
        }catch (IOException e){
            System.out.println("写入失败：" + e.getMessage());
        }
        System.out.println("程序继续进行");

    }
}
