package com.ljf.learning.day02;

import java.util.Scanner;

public class BMICalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("请输入体重：");
        double weight = sc.nextDouble();

        System.out.println("请输入身高：");
        double height = sc.nextDouble();

        double bmi = weight / (height *  height);

        System.out.println("你的BMI是：" + bmi);

        sc.close();
    }

}
