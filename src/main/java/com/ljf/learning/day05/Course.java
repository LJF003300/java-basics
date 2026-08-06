package com.ljf.learning.day05;

public class Course {
    private String courseName;
    private String teacherName;
    private int limit;

    public Course(String courseName, String teacherName, int limit) {
            this.courseName = courseName;
            this.teacherName = teacherName;
            setLimit(limit);
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit >= 1 && limit <= 300) {
            this.limit = limit;
        }
    }
}
