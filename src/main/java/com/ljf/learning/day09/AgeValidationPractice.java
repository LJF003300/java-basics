package com.ljf.learning.day09;

public class AgeValidationPractice {
    public static void main(String[] args) {
        try {
            validateAge(20);
        }catch (IllegalArgumentException e){
            System.out.println("异常信息："+ e.getMessage());
        }

        try {
            validateAge(0);
        }catch (IllegalArgumentException e){
            System.out.println("异常信息："+ e.getMessage());
        }

        try {
            validateAge(-1);
        }catch (IllegalArgumentException e){
            System.out.println("异常信息："+ e.getMessage());
        }

        try {
            validateAge(121);
        }catch (IllegalArgumentException e){
            System.out.println("异常信息："+ e.getMessage());
        }

        try {
            validateAge(120);
        }catch (IllegalArgumentException e){
            System.out.println("异常信息："+ e.getMessage());
        }

        System.out.println("程序继续进行");
    }

    static void validateAge(int age) {
        if (age < 0 || age > 120) {
            throw new IllegalArgumentException(
                    "年龄必须在0到120之间"
            );
        }

        System.out.println("年龄合法：" + age);
    }
}
