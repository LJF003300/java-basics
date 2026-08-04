package com.ljf.learning.day02;
import java.util.Scanner;


public class VariablePractice {
    public static void main(String[] args) {
        String name = "李霁峰";
        int age = 19;
        double height = 1.72;
        boolean learningJava = true;

        String goal = "找实习";

        System.out.println("姓名：" + name);
        System.out.println("年龄：" + age);
        System.out.println("身高：" + height);
        System.out.println("正在学习Java：" + learningJava);
        System.out.println("明年年龄：" + (age + 1));
        System.out.println("我的目标是：" + goal);
    }
}

