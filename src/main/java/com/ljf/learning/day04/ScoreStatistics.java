package com.ljf.learning.day04;

public class ScoreStatistics {
    public  static void main(String[] args) {
        int[] scores = {78,95,60,43,88};

        int maximum = scores[0];
        int minimum = scores[0];
        int sum=0;
        int passedCount = 0;

        for(int score:scores){
            if(score>maximum){
                maximum = score;
            }
            if(score<minimum){
                minimum = score;
            }

            sum += score;

            if(score>=60){
                passedCount++;
            }

        }

        double average = (double)sum/scores.length;//(doublr)避免整数运算

        System.out.println("最高分：" + maximum);
        System.out.println("最低分：" + minimum);
        System.out.println("总分：" + sum);
        System.out.println("平均分：" + average);
        System.out.println("及格人数：" + passedCount);
    }
}
