package com.ljf.learning.day01;

public class MyGoal {
    public static String Name(){
        return "我的名字是 李霁峰；";
    }

    public static String Target(){
        return "我的目标是 开始学习Java。";
    }

    public static void main(String[] args){
        System.out.println(Name() + Target());// 先拼接两个方法返回的字符串，再作为一个实参输出
        System.out.println(Target());
    }
}
