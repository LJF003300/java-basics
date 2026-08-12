package com.ljf.learning.day09;

import com.ljf.learning.day08.Contact;

import java.util.ArrayList;
import java.util.List;

public class GenericPractice {
    public static void main(String[] args) {
        List<Contact> contacts = new ArrayList<>();

        contacts.add(new Contact("张三", "111"));

        Contact firstContact = contacts.get(0);
        System.out.println(firstContact.getName());

        Result<String> textResult =
                new Result<>("成功");

        String text = textResult.getData();
        System.out.println("字符串结果："+ text);

        Result<Contact> contactResult =
                new Result<>(
                        new Contact("李四", "222")
                );

        Contact contact = contactResult.getData();
        System.out.println("联系人姓名：" + contact.getName());
    }
}
