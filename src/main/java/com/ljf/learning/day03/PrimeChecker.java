package com.ljf.learning.day03;
import java.util.Scanner;


public class PrimeChecker {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);

        System.out.println("请输入一个整数：");
        int input=sc.nextInt();

        boolean result = isPrime(input);

        if(result){
            System.out.println(input + "是素数");
        }else {
            System.out.println(input + "不是素数");
        }

        sc.close();
    }

    public static boolean isPrime(int n){
        if(n < 2){
            return false;
        }

        for(int divisor = 2;divisor*divisor<=n;divisor++){
            if(n % divisor == 0){
                return false;
            }
        }

        return true;
    }
}
