package com.ljf.learning.day06;

public class Teacher extends Person {
    private final String subject;

    public Teacher(String name, String subject) {
        super(name);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public void introduce() {
        System.out.println(
                "我是" + getName() + "，教授" + subject
        );
    }
}