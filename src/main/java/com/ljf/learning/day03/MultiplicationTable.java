package com.ljf.learning.day03;

public class MultiplicationTable {
    public static void main(String[] args) {
        printTable();
    }

    public static void printTable(){
        for(int i = 1;i<=9;i++){
            for(int j = 1;j<=i;j++){
                System.out.print(j + "x" + i + "=" + (i*j) + " ");//第i行共有i个式子
            }
            System.out.println();//紧跟内层，每行内容结束后换行
        }
    }
}
