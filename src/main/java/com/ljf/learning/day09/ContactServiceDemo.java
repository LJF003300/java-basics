package com.ljf.learning.day09;

import com.ljf.learning.day08.Contact;

public class ContactServiceDemo {
    public static void main(String[] args) {
        ContactService contactService = new ContactService();
        //初始添加张三李四
        try{
            contactService.addContact(new Contact("张三","111"));
        }catch (DuplicatePhoneException e){
            System.out.println(e.getMessage());
        }
        try {
            contactService.addContact(new Contact("李四","222"));
        }catch (DuplicatePhoneException e){
            System.out.println(e.getMessage());
        }

        //查询111电话联系人姓名
        try{
            Contact foundContact = contactService.findByPhone("111");
            System.out.println("111电话联系人姓名：" + foundContact.getName());
        }catch (ContactNotFoundException e){
            System.out.println(e.getMessage());
        }

        //添加重复号码222
        try{
            contactService.addContact(new Contact("王五","222"));
        }catch (DuplicatePhoneException e){
            System.out.println(e.getMessage());
        }

       //查询999号码
        try {
            contactService.findByPhone("999");
        }catch (ContactNotFoundException e){
            System.out.println(e.getMessage());
        }

        //输出List、Set、Map分别数量
        System.out.println("List数量：" + contactService.getContactCount());
       System.out.println("Set数量：" + contactService.getPhoneNumberCount());
       System.out.println("Map数量：" + contactService.getContactMapCount());

        System.out.println("程序继续运行");
    }
}
