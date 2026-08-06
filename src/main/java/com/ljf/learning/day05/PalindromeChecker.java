package com.ljf.learning.day05;
import  java.util.Scanner;


public class PalindromeChecker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一个字符串：");
        String str = sc.nextLine();

        boolean result = isPalindrome(str);

        if (result) {
            System.out.println("是回文");
        }else {
            System.out.println("不是回文");
        }
        sc.close();
    }

    public static boolean isPalindrome(String text) {

                for(int i = 0;i < text.length()/2 ;i++){
                    if(text.charAt(i) != text.charAt(text.length()-i-1)){
                        return false;
                    }
                }
                return true;
    }
}
