package com.ljf.learning.day07;

public class StudentManager {
    private StudentRecord[] students;
    private int count;

    public StudentManager(int capacity) {
        students = new StudentRecord[capacity];
        count = 0;
    }

    public boolean addStudent(StudentRecord student){
        //判断是否已有重复学生信息
        if(findById(student.getStudentId()) != null){
            return false;
        }

        if(count >= students.length){
            return false;
        }

        students[count] = student;
        count ++;
        return true;
    }

    public int getCount(){
        return count;
    }

    public void printAllStudents(){
        if(count == 0){
            System.out.println("暂无学生");
            return;
        }

        System.out.println("-----下面是全部学生信息：-----");

        for(int i = 0;i < count;i++){

            System.out.println("学生学号：" + students[i].getStudentId() + "；" +"学生姓名：" + students[i].getName());

            if(students[i].hasScore()){
                System.out.println(students[i].getName() + "的成绩：" + students[i].getScore());
            }else{
                System.out.println(students[i].getName() + "暂无成绩");
            }
        }
    }

    public StudentRecord findById(String studentId){
        for(int i = 0;i < count;i++){
            if(students[i].getStudentId().equals(studentId)){
                return students[i];
            }
        }
        return null;
    }

    public boolean recordScore(String studentId,
                               double score){
        StudentRecord studentFound = findById(studentId);

        if(studentFound == null){
            return false;
        }

        return studentFound.recordScore(score);
    }

    public double calculateAverageScore(){
        double sum = 0;
        int scoredCount = 0;

        for(int i = 0;i < count;i++){
            if(students[i].hasScore()){
                sum += students[i].getScore();
                scoredCount ++;
            }
        }

        if(scoredCount == 0){
            return -1;
        }
        return sum/scoredCount;
    }

    public int getScoredStudentCount() {
        int scoredCount = 0;

        for (int i = 0; i < count; i++) {
            if (students[i].hasScore()) {
                scoredCount++;
            }
        }

        return scoredCount;
    }

    public void printRanking(){
        if(getScoredStudentCount() == 0){
            System.out.println("暂无成绩");
        }else {

            StudentRecord[] rankedStudents = new StudentRecord[getScoredStudentCount()];
            int rankedCount = 0;

            //复制有成绩的学生至数组rankedStudents[]
            for (int i = 0; i < count; i++) {
                if (students[i].hasScore()) {
                    rankedStudents[rankedCount] = students[i];
                    rankedCount++;
                }
            }

            //排序
            for (int i = 0; i < rankedStudents.length - 1; i++) {

                int maxIndex = i;

                for (int j = i + 1; j < rankedStudents.length; j++) {

                    if (rankedStudents[j].getScore() > rankedStudents[maxIndex].getScore()) {
                        maxIndex = j;
                    }
                }

                StudentRecord temp = rankedStudents[i];
                rankedStudents[i] = rankedStudents[maxIndex];
                rankedStudents[maxIndex] = temp;
            }

            //输出打印
            for (int i = 0; i < getScoredStudentCount(); i++) {
                System.out.println("第" + (i+1) + "名：" + rankedStudents[i].getName() + ",成绩：" + rankedStudents[i].getScore());
            }
        }
    }
}