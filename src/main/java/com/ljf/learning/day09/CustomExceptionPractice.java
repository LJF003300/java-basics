package com.ljf.learning.day09;
import com.ljf.learning.day08.Contact;

import java.util.*;

public class CustomExceptionPractice {
    static void validatePhoneAvailable(
            Set<String> phoneNumbers,
            String phone
    ){
        if(!phoneNumbers.contains(phone)){
            System.out.println("手机号可用：" + phone);
        }else {
            throw new DuplicatePhoneException( "手机号已存在：" + phone);
        }

    }

    static Contact findContact(
            Map<String, Contact> contactsByPhone,
            String phone
    ){
        if(contactsByPhone.containsKey(phone)){
            return contactsByPhone.get(phone);
        }else {
            throw new ContactNotFoundException(
                    "联系人不存在："+ phone
            );
        }
    }

    public static void main(String[] args) {
        Set<String> phoneNumbers = new HashSet<>();
        phoneNumbers.add("111");
        try{
            validatePhoneAvailable(phoneNumbers,"222");
        }catch (DuplicatePhoneException e){
            System.out.println("异常信息：" + e.getMessage());
        }

        try {
            validatePhoneAvailable(phoneNumbers,"111");
        }catch (DuplicatePhoneException e){
            System.out.println("异常信息：" + e.getMessage());
        }
        System.out.println("程序继续运行");

        Map<String, Contact> contactsByPhone = new HashMap<>();
        contactsByPhone.put(
                "111",
                new Contact("张三", "111")
        );

        try{
            Contact foundContact = findContact(contactsByPhone,"111");
            System.out.println("111号码的联系人：" + foundContact.getName());
        }catch (ContactNotFoundException e){
            System.out.println("异常信息：" + e.getMessage());
        }

        try{
            Contact foundContact = findContact(contactsByPhone,"999");
            System.out.println("999号码的联系人：" + foundContact.getName());
        }catch (ContactNotFoundException e){
            System.out.println("异常信息：" + e.getMessage());
        }

        System.out.println("程序继续进行");
    }

}

