package com.ljf.learning.day11;

import com.ljf.learning.day08.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactOptionalPractice {
    public static void main(String[] args) {
        List<Contact> contactList = new ArrayList<>();
        contactList.add(new Contact("张三","111"));
        contactList.add(new Contact("李四","222"));
        contactList.add(new Contact("王五","333"));

        try {
            Contact contact222 = contactList.stream()
                    .filter(contact -> contact.getPhone().equals("222"))
                    .findFirst()
                    .orElseThrow(
                            () -> new IllegalArgumentException("手机号不存在：222")
                    );
            System.out.println("找到联系人：" + contact222.getName());

            Contact contact999 = contactList.stream()
                    .filter(contact -> contact.getPhone().equals("999"))
                    .findFirst()
                    .orElseThrow(
                            () -> new IllegalArgumentException("手机号不存在：999")
                    );
            System.out.println("找到联系人：" + contact999.getName());
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println("程序继续进行");

    }
}
