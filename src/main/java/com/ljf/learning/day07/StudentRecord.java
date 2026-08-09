package com.ljf.learning.day07;

public class StudentRecord {
    private String studentId;
    private String name;
    private double score;

    public StudentRecord(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        score = -1;
    }

    public String getStudentId() {
        return studentId;
    }
    public String getName() {
        return name;
    }
    public double getScore() {
        return score;
    }

    public boolean hasScore() {
        return score >= 0;
    }

    public boolean recordScore(double score){
        if(0 <= score && score <= 100){
            this.score = score;
            return true;
        }
        return false;
    }
}
