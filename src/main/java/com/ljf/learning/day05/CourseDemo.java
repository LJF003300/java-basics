package com.ljf.learning.day05;

public class CourseDemo {
    public  static void main(String[] args) {
        Course course1 = new Course("Java程序设计","王老师",60);

        System.out.println("课程名称：" + course1.getCourseName());
        System.out.println("授课教师：" + course1.getTeacherName());
        System.out.println("人数上限：" + course1.getLimit());

        course1.setLimit(80);
        System.out.println("修改后人数上限：" + course1.getLimit());

        course1.setLimit(-10);
        System.out.println("非法修改后人数上限：" + course1.getLimit());
    }

}
