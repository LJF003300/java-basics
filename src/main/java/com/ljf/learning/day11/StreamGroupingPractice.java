package com.ljf.learning.day11;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamGroupingPractice {
    public static void main(String[] args) {
        List<String> words =
                List.of("Java", "Map", "List", "Stream");

        List<String> emptyWords = List.of();

        Map<Integer, List<String>> wordsByLength = words.stream()
                .collect(Collectors.groupingBy(word -> word.length()));
        Map<Integer,List<String>> emptyWordsByLength = emptyWords.stream()
                .collect(Collectors.groupingBy(emptyword -> emptyword.length()));

        System.out.println("长度为4的分组：" + wordsByLength.get(4) + "; 大小：" + wordsByLength.get(4).size());
        System.out.println("空分组的大小：" + emptyWordsByLength.size());



    }
}
