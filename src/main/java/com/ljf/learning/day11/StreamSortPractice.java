package com.ljf.learning.day11;

import java.util.List;

public class StreamSortPractice {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(90, 70, 85, 70);

        List<Integer> sortedNumbers = numbers.stream()
                .sorted()
                .toList();

        List<Integer> emptyList = List.of();
        List<Integer> sortedEmptyList = emptyList.stream()
                        .sorted()
                .toList();

        System.out.println("原列表：" + numbers);
        System.out.println("排列后：" + sortedNumbers);
        System.out.println("空列表排序后："  + sortedEmptyList);
        System.out.println("空列表大小：" + emptyList.size());
    }
}
