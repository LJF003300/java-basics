package com.ljf.learning.day11;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamCountingPractice {
    public static void main(String[] args) {
        List<String> words =
                List.of("Java", "Map", "List", "Set", "Stream");
        List<String> emptywords = List.of();

        Map<Integer, Long> countByLength = words.stream()
                .collect(Collectors.groupingBy(
                        word -> word.length(),
                        Collectors.counting()
                ));
        Map<Integer, Long> emptyCountByLength =  emptywords.stream()
                .collect(Collectors.groupingBy(
                        word -> word.length(),
                        Collectors.counting()
                ));


        System.out.println("长度为3的数量：" + countByLength.get(3));
        System.out.println("长度为4的数量："  + countByLength.get(4));
        System.out.println("长度为6的数量：" + countByLength.get(6));
        System.out.println("空统计大小：" + emptyCountByLength.size());
    }
}
