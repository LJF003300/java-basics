package com.ljf.learning.day07;

public class StudentRecordDemo {
    public static void main(String[] args) {
        StudentRecord student = new StudentRecord("2026001","张三");

        System.out.println("姓名：" + student.getName());
        System.out.println("学号：" + student.getStudentId());
        System.out.println("是否已有成绩：" + student.hasScore());

        System.out.println("录入85.5是否成功：" + student.recordScore(85.5));
        System.out.println("当前成绩：" + student.getScore());

        System.out.println("录入120是否成功：" + student.recordScore(120));
        System.out.println("当前成绩：" + student.getScore());

    }


}
