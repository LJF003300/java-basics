package com.ljf.learning.day10;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReadPractice {
    public static void main(String[] args) {
        Path path = Path.of(
                "data",
                "day10-note.txt"
        );

        try{
            String content = Files.readString(
                    path,
                    StandardCharsets.UTF_8
            );
            System.out.println("读取内容：" + content);
            System.out.println("读取成功");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        try{
            Path path1 = Path.of("data", "missing.txt");
            String content = Files.readString(
                    path1,
                    StandardCharsets.UTF_8
            );
            System.out.println("读取内容：" + content);
            System.out.println("读取成功");
        }catch (IOException e){
            System.out.println("读取失败：" + e.getMessage());
        }
        System.out.println("程序继续进行");
    }
}
