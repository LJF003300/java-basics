package com.ljf.learning.day06;

public class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void introduce() {
        System.out.println("我是" + name);
    }
}