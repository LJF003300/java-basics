package com.ljf.learning.day11;

import com.ljf.learning.day08.Contact;

import java.util.ArrayList;
import java.util.List;

public class LambdaPractice {
    public  static void main(String[] args) {
        List<Contact>  list = new ArrayList<>();
        list.add(new Contact("张三","111"));
        list.add(new Contact("李四","222"));
        list.add(new Contact("王五","333"));

        list.forEach(element -> System.out.println("姓名：" + element.getName() + "；手机号：" + element.getPhone()));

        List<Contact> emptyList =  new ArrayList<>();
        emptyList.forEach(Contact -> System.out.println("姓名：" + Contact.getName() + "；手机号：" + Contact.getPhone()));
    }
}
