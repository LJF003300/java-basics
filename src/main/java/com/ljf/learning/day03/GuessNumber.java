package com.ljf.learning.day03;
import java.util.Scanner;
import java.util.Random;

public class GuessNumber {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Random random = new Random();


        int attempts = 0;
        int answer = random.nextInt(100) + 1;

        while(true){
            System.out.println("请输入1~100的数字：");
            int guess = sc.nextInt();
            attempts++;

            if(guess<answer){
                System.out.println("猜小了");
            }else if(guess>answer){
                    System.out.println("猜大了");
            } else{
                    System.out.println("猜中了，共尝试 " + attempts + " 次");
                    break;
            }

        }
        sc.close();
    }


}
