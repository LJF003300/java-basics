package com.ljf.learning.day02;
import java.util.Scanner;


public class TemperatureConverter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("请输入摄氏度: ");
        double sheshidu = sc.nextDouble();

        double huashidu = sheshidu * 9 / 5 +32;

        System.out.println("华氏温度：" + huashidu);

        sc.close();
    }

}
