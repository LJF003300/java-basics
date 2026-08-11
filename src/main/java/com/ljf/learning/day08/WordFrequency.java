package com.ljf.learning.day08;
import java.util.HashMap;
import java.util.Map;

public class WordFrequency {
    public  static void main(String[] args) {
        String[] words = {
                "java", "mysql", "java",
                "git", "java", "mysql"
        };
        Map<String, Integer> countWords = new HashMap<>();

        for(String word : words) {

                if (countWords.containsKey(word)) {
                    int oldCount = countWords.get(word);
                    countWords.put(word,oldCount + 1);
                } else {
                    countWords.put(word, 1);
                }

        }

        for (Map.Entry<String, Integer> entry : countWords.entrySet()) {
            System.out.print(entry.getKey() + ": " + entry.getValue() + " ");
        }
        System.out.println("不同单词数量：" + countWords.size());

    }
}
