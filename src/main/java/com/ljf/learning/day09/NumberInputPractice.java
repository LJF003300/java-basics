package com.ljf.learning.day09;

public class NumberInputPractice  {
    public static void main(String[] args) {
        String normalInput = "25";
        String invalidInput = "abc";
        try{
            int a = Integer.parseInt(normalInput);
            System.out.println("转换成功："+a);
        }catch(NumberFormatException e){
            System.out.println("转换失败：必须输入整数");
        }

        try{
            int b = Integer.parseInt(invalidInput);
            System.out.println("转换成功："+b);
        }catch(NumberFormatException e){
            System.out.println("转换失败：必须输入整数");
            System.out.println("异常类型：" + e.getClass().getSimpleName());
            System.out.println("异常信息：" + e.getMessage());
        }
        System.out.println("程序继续进行");

    }
}
