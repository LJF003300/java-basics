package com.ljf.learning.day11;

import com.ljf.learning.day08.Contact;

import java.util.ArrayList;
import java.util.List;

public class StreamCollectPractice {
    public static void main(String[] args) {
        List<Contact> list = new ArrayList<>();
        list.add(new Contact("张三","111"));
        list.add(new Contact("李四","122"));
        list.add(new Contact("王五","333"));
        list.add(new Contact("",""));

        List<String> name1 = list.stream()
                .filter(contact -> contact.getPhone().startsWith("1"))
                .map(contact -> contact.getName())
                .toList();

        List<String> name9 = list.stream()
                .filter(contact -> contact.getPhone().startsWith("9"))
                .map(contact -> contact.getName())
                .toList();

        System.out.println(name1);
        System.out.println(name9);

        System.out.println("原列表大小：" + list.size());
        System.out.println("姓名列表大小：" + name1.size());
        System.out.println("无匹配列表大小：" + name9.size());

    }
}
