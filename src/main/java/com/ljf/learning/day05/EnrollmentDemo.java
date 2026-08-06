package com.ljf.learning.day05;

public class EnrollmentDemo {
    public static void main(String[] args) {

        Student student = new Student("张三", 19);

        Course course = new Course(
                "Java程序设计",
                "王老师",
                60
        );

        Enrollment enrollment = new Enrollment(student, course);

        System.out.println(
                "学生：" + enrollment.getStudent().getName()
        );

        System.out.println(
                "所选课程：" + enrollment.getCourse().getCourseName()
        );

        course.setLimit(100);

        System.out.println(
                "修改后人数上限：" + enrollment.getCourse().getLimit()
        );
    }
}
