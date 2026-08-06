package com.ljf.learning.day05;

public class StudentDemo {
    public static void main(String[] args) {
        Student student1 = new Student("张三",19);

        System.out.println("第一位同学姓名：" + student1.getName());

        student1.setAge(20);
        System.out.println("第一位同学年龄：" + student1.getAge());

        student1.setAge(-100);
        System.out.println("第一位同学年龄：" + student1.getAge());

        System.out.println();

        Student student2 = new Student("李四",20);

        System.out.println("第二位同学姓名：" + student2.getName());
        System.out.println("第二位同学年龄："  + student2.getAge());
    }
}
