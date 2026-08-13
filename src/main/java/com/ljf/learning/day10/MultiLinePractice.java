package com.ljf.learning.day10;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MultiLinePractice {
    public static void main(String[] args) {
        Path path = Path.of(
                "data",
                "contacts.txt"
        );
        List<String> lines = List.of(
                "张三,111",
                "李四,222",
                "王五,333"
        );


        try {
            Files.createDirectories(path.getParent());
            Files.write(
                    path,
                    lines,
                    StandardCharsets.UTF_8
            );
            List<String> loadedLines = Files.readAllLines(
                    path,
                    StandardCharsets.UTF_8
            );
            for (String line : loadedLines) {
                System.out.println("读取联系人：" + line);
            }
            System.out.println("联系人数量：" + loadedLines.size());
        }catch (IOException e){
            System.out.println("读取失败："  + e.getMessage());
        }
        System.out.println("程序继续进行");

    }
}
