package com.ljf.learning.day04;

public class StringReverser {
    public static void main(String[] args) {
        String text = "Java";
        StringBuilder reversed = new StringBuilder();

        for (int i = text.length() - 1; i >= 0; i--) {
            char current = text.charAt(i);
            reversed.append(current);// 将current添加到reversed末尾

        }

        System.out.println("原字符串：" + text);
        System.out.println("反转结果：" + reversed);

        String builtInResult = new StringBuilder(text)
                .reverse()
                .toString();//链式调用，=右边执行一系列调用，用.分割执行
        /**
         * reverse()：反转StringBuilder中的字符。
         * toString()：把当前对象的文字表示转换成String。
         * 二者不是固定组合，只是这段代码刚好先反转，再需要一个String结果。
         * */

        System.out.println("内置反转：" + builtInResult);
    }
}