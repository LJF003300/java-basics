package com.ljf.learning.day07;

import java.util.Scanner;

public class StudentGradeApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManager manager = new StudentManager(100);

        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("===== 学生成绩管理系统 =====");
            System.out.println("1. 添加学生");
            System.out.println("2. 录入成绩");
            System.out.println("3. 查询学生");
            System.out.println("4. 显示全部学生");
            System.out.println("5. 显示成绩统计");
            System.out.println("6. 显示成绩排名");
            System.out.println("0. 退出");
            System.out.print("请选择：");

            String choice = scanner.nextLine();

            switch (choice) {
                case "0" -> {
                    running = false;
                    System.out.println("系统已退出");
                }
                case "1" -> handleAddStudent(scanner,manager);
                case "2" -> handleRecordScore(scanner,manager);
                case "3" -> handleFindStudent(scanner,manager);
                case "4" -> manager.printAllStudents();
                case "5" -> handleShowStatistics(manager);
                case "6" -> manager.printRanking();
                default ->
                        System.out.println("无效选项，请重新输入");
            }
        }

        scanner.close();
    }

    private static void handleAddStudent(Scanner scanner, StudentManager manager) {
        System.out.println("请输入学号：");
        String studentId = scanner.nextLine();

        System.out.println("请输入姓名：");
        String name = scanner.nextLine();

        if(studentId.isBlank() || name.isBlank()){
            System.out.println("添加失败：学号和姓名不能为空");
            return;
        }

        StudentRecord student = new StudentRecord(studentId,name);

        boolean added = manager.addStudent(student);
        if(added){
            System.out.println("添加成功");
        }else {
            System.out.println("添加失败：学号重复或容量已满");
        }

    }

    private static void handleFindStudent(Scanner scanner, StudentManager manager) {
        System.out.println("请输入要查询的学号：");
        String studentId = scanner.nextLine();

        if(studentId.isBlank()){
            System.out.println("查询失败：学号不能为空");
            return;
        }

        StudentRecord student = manager.findById(studentId);

        if(student == null){
            System.out.println("未找到该学生");
            return;
        }

        System.out.println("学号：" + student.getStudentId());
        System.out.println("姓名：" + student.getName());

        if(student.hasScore()){
            System.out.println("成绩：" + student.getScore());
        }else{
            System.out.println("成绩：尚未录入");
        }
    }

    private static void handleRecordScore(Scanner scanner, StudentManager manager) {
        System.out.println("请输入要录入成绩的学生学号：");
        String studentId = scanner.nextLine();
        
        if(studentId.isBlank()){
            System.out.println("录入失败：学号不能为空");
            return;
        }
        
        StudentRecord student = manager.findById(studentId);
        if(student == null){
            System.out.println("录入失败：未找到该学生");
            return;
        }

        System.out.println("请输入为" + student.getName() + "录入的成绩：");
        String scoreText = scanner.nextLine();

        try {
            double score = Double.parseDouble(scoreText);

            boolean recorded =
                    manager.recordScore(studentId, score);

            if (recorded) {
                System.out.println("成绩录入成功");
            } else {
                System.out.println("录入失败：成绩必须在0至100之间");
            }
        } catch (NumberFormatException exception) {
            System.out.println("录入失败：成绩必须是数字");
        }
    }

    private  static void handleShowStatistics(StudentManager manager) {
        System.out.println("学生总人数：" + manager.getCount());
        System.out.println("已有成绩人数：" + manager.getScoredStudentCount());

        double average = manager.calculateAverageScore();
        if(average < 0){
            System.out.println("平均分：暂无成绩");
        }else {
            System.out.println("平均分：" + average);
        }
    }
}
