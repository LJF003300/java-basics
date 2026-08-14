package com.ljf.learning.day11;

import com.ljf.learning.day08.Contact;

import java.util.ArrayList;
import java.util.List;

public class StreamMapPractice {
    public static void main(String[] args) {
        List<Contact> list = new ArrayList<>();
        list.add(new Contact("张三","111"));
        list.add(new Contact("李四","222"));
        list.add(new Contact("王五","333"));
        list.add(new Contact("",""));

        list.stream()
                .map(contact -> contact.getName())
                .forEach(name -> System.out.println( "姓名："+name));

        list.stream()
                .map(contact -> contact.getPhone().length())
                .forEach(length -> System.out.println("手机号长度：" + length));
    }
}
