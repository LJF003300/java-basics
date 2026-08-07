package com.ljf.learning.day06;

public class InheritanceDemo {
    public static void main(String[] args) {
        Teacher teacher = new Teacher("王老师","Java");

        System.out.println("姓名：" + teacher.getName());
        System.out.println("科目：" + teacher.getSubject());

        Person person = new Person("张三");
        Person teacherAsPerson =
                new Teacher("李老师", "数据库");

        person.introduce();
        teacher.introduce();

        showIntroduction(person);
        showIntroduction(teacher);
        teacherAsPerson.introduce();
    }

    public static void showIntroduction(Person person) {
        person.introduce();
    }
}
