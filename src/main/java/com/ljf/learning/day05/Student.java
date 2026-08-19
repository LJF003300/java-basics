package com.ljf.learning.day05;

public class Student {
    private final String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        setAge(age);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(age >= 0 && age <= 150){
            this.age = age;
        }
    }
}
