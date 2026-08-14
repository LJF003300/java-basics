package com.ljf.learning.day11;

import com.ljf.learning.day08.Contact;

import java.util.ArrayList;
import java.util.List;

public class StreamFilterPractice {
    public static void main(String[] args) {
        List<Contact> list = new ArrayList<>();
        list.add(new Contact("张三","111"));
        list.add(new Contact("李四","122"));
        list.add(new Contact("王五","333"));
        list.add(new Contact("",""));

        System.out.println("手机号以1开头的联系人：");
        list.stream()
                .filter(contact -> contact.getPhone().startsWith("1"))
                .forEach(contact -> System.out.println(contact.getName()));

        System.out.println("手机号以9开头的联系人：");
        list.stream()
                .filter(contact -> contact.getPhone().startsWith("9"))
                .forEach(contact -> System.out.println("手机号以9开头的联系人：" + contact.getName()));

        System.out.println("联系人集合大小：" + list.size());


    }
}
