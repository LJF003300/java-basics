package com.ljf.learning.day08;
import java.util.HashSet;
import java.util.Set;

public class SetPractice {
    public static void main(String[] args) {
        Set<String> skills = new HashSet<>();
        boolean firstAdd = skills.add("Java");
        boolean secondAdd = skills.add("MySQL");
        boolean duplicateAdd = skills.add("Java");
        boolean fourthAdd = skills.add("Git");
        System.out.println("第一次添加Java:" + firstAdd);
        System.out.println("添加MySQL:" + secondAdd);
        System.out.println("再次添加Java:" + duplicateAdd);
        System.out.println("添加Git:" + fourthAdd);

        System.out.println("是否包含MySQL：" + skills.contains("MySQL"));
        System.out.println("移除Git:" + skills.remove("Git"));
        System.out.println("最终数量：" + skills.size());

        for(String skill : skills){
            System.out.print(skill + " ");

        }
    }
}