package com.ljf.learning.day11;

import java.util.List;
import java.util.Optional;

public class OptionalPractice {
    public static void main(String[] args) {
        List<String> names = List.of("Java", "Map", "Stream");
        List<String> emptynames = List.of( );

        Optional<String> resultJ = names.stream()
                .filter(n -> n.startsWith("J"))
                .findFirst();
        Optional<String> resultZ = names.stream()
                .filter(n -> n.startsWith("Z"))
                .findFirst();
        Optional<String> resultEmpty = emptynames.stream()
                .findFirst();

        String nameJ = resultJ.orElse("未找到");
        String nameZ = resultZ.orElse("未找到");
        String nameEmpty = resultEmpty.orElse("未找到");

        System.out.println("以J开头第一个：" + nameJ);
        System.out.println("以Z开头第一个：" + nameZ);
        System.out.println("空列表第一个：" + nameEmpty);

    }
}
