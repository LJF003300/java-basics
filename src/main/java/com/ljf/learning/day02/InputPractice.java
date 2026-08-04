package com.ljf.learning.day02;

import java.util.Scanner;

public class InputPractice {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入年龄：");
        int age = scanner.nextInt();

        System.out.println("你输入的年龄是：" + age);

        scanner.close();
    }
}