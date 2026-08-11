package com.ljf.learning.day08;
import java.util.ArrayList;
import java.util.List;

public class ListPractice {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();

        names.add("张三");
        names.add("李四");
        names.add("王五");
        names.add("张三");

        System.out.println("添加后的数量：" + names.size()); // 当前元素数量
        System.out.println("下标1：" + names.get(1)); // 获取下标1的元素
        System.out.println("是否包含李四：" + names.contains("李四") ); // 是否包含
        System.out.println("删除王五：" + names.remove("王五")); // 删除指定内容
        System.out.println("删除王五后数量：" + names.size());


        for (String name : names) {
            System.out.println(name);
        }

        List<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(20);

        for (Integer number : numbers) {
            System.out.print(number+" ");
        }
        System.out.println();

        numbers.remove(1);
        System.out.print("remove(1)后:");
        for (Integer number : numbers) {
            System.out.print(number+" ");
        }
        System.out.println();

        numbers.remove(Integer.valueOf(20));
        System.out.print("remove(Integer.valueOf(20))后：");
        for (Integer number : numbers) {
            System.out.print(number+" ");
        }
    }
}