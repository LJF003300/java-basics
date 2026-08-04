package com.ljf.learning.day03;
import  java.util.Scanner;

public class ScoreJudge {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("请输入分数：");
        int grade = sc.nextInt();

        if (0 <= grade && grade <= 59){
            System.out.println("不及格");
        }else if (60 <= grade && grade <= 89){
            System.out.println("及格");
        }else if (90 <= grade && grade <= 100){
            System.out.println("优秀");
        }else{
            System.out.println("分数无效");
        }

        sc.close();
    }
}
