package com.ljf.learning.day05;

import java.util.Scanner;

public class TaskCombination {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] arr = new int[5];

        System.out.println("请输入5个整数：");
        for (int i = 0; i < 5; i++) {
            arr[i] = sc.nextInt();
        }

        int maximum = findMaximum(arr);
        int minimum = findMinimum(arr);
        double average = calculateAverage(arr);
        int evenCount = countEvenNumbers(arr);

        System.out.println("最大值：" + maximum);
        System.out.println("最小值：" + minimum);
        System.out.println("平均值：" + average);
        System.out.println("偶数数量：" + evenCount);

        sc.close();
    }


    public static int findMaximum(int[] numbers){
        int maximum = numbers[0];

        for(int i=0;i<numbers.length;i++){
            if(numbers[i]>maximum){
                maximum = numbers[i];
            }
        }
        return maximum;
    }

    public static int findMinimum(int[] numbers){
        int minimum = numbers[0];

        for(int i=0;i<numbers.length;i++){
            if(numbers[i]<minimum){
                minimum = numbers[i];
            }
        }
        return minimum;
    }

    public static double calculateAverage(int[] numbers){
        int sum = 0;
        for(int i=0;i<numbers.length;i++){
            sum += numbers[i];
        }
        double average = (double)sum/numbers.length;

        return average;
    }

    public static int countEvenNumbers(int[] numbers){
        int count = 0;
        for(int i=0;i<numbers.length;i++){
            if(numbers[i]%2==0){
                count++;
            }
        }
        return count;
    }
}
