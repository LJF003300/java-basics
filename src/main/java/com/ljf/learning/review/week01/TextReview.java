package com.ljf.learning.review.week01;

import java.util.Scanner;

public class TextReview  {
    public static  void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入字符串：");
        String text = sc.nextLine();

        System.out.println("请输入目标字符：");
        String targetText = sc.nextLine();

        if (targetText.isEmpty()) {
            System.out.println("目标字符不能为空！");
            sc.close();
            return;
        }
            char target = targetText.charAt(0);


        System.out.println("字符数量：" + countCharacter(text,target));
        System.out.println("是否回文：" + isPalindrome(text));

        sc.close();
    }

    public static int countCharacter(
            String text,
            char target
    ){
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == target) {
                count++;
            }
        }
        return count;
    }

    public static boolean isPalindrome(String text){
        for(int i =0;i < text.length()/2;i++){
            if(text.charAt(i) != text.charAt(text.length() - 1 - i)){
                return false;
            }
        }
        return true;
    }
}
