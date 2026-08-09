package com.ljf.learning.day07;

public class StudentManagerDemo {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager(3);

        StudentRecord duplicate =
                new StudentRecord("2026002", "另一个名字");

        StudentRecord student1 = new StudentRecord("2026002","李四");
        StudentRecord student2 = new StudentRecord("2026003","王五");
        StudentRecord student3 = new StudentRecord("2026004","石六");
        System.out.println("添加第一名：" + manager.addStudent(student1));
        System.out.println("添加第二名：" + manager.addStudent(student2));
        System.out.println("添加重复学号：" + manager.addStudent(duplicate));
        System.out.println("添加第三名：" + manager.addStudent(student3));
        System.out.println("当前人数：" + manager.getCount());

        //查询2026002和9999999学号的输出验证
        StudentRecord found1 =
                manager.findById("2026002");

        if (found1 != null) {
            System.out.println("查询2026002：" + found1.getName());
        } else {
            System.out.println("查询2026002：未找到");
        }

        StudentRecord found2 =
                manager.findById("9999999");

        if (found2 != null) {
            System.out.println("查询9999999：" + found2.getName());
        } else {
            System.out.println("查询9999999：未找到");
        }

        //打印manager数组里的所有学生信息
        manager.printAllStudents();

        //平均分逻辑正确性判断
        manager.recordScore("2026002", 75);
        manager.recordScore("2026003", 90);
        manager.recordScore("2026004", 88.5);
        System.out.println("已有成绩人数：" + manager.getScoredStudentCount() +"人参与计算");
        System.out.println("平均分：" + manager.calculateAverageScore());

        StudentManager emptyManager =
                new StudentManager(3);

        System.out.println(
                "空管理器平均分：" +
                emptyManager.calculateAverageScore()
        );

        manager.printRanking();
    }

}
