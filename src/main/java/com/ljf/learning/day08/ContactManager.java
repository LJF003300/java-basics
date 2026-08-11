package com.ljf.learning.day08;
import java.util.*;

public class ContactManager {
    public  static void main(String[] args) {

        Map<String, Contact> contactsByPhone = new HashMap<>();
        List<Contact> contacts = new ArrayList<>();
        Set<String> phoneNumbers = new HashSet<>();

        Contact zhangSan = new Contact("张三","111");
        Contact liSi = new Contact("李四","222");
        Contact wangWu = new Contact("王五", "222");

        if (phoneNumbers.add(zhangSan.getPhone())) {
            contacts.add(zhangSan);
            contactsByPhone.put(
                    zhangSan.getPhone(),
                    zhangSan
            );
        } else {
            System.out.println(zhangSan.getName()+ "手机号重复");
        }

        if (phoneNumbers.add(liSi.getPhone())) {
            contacts.add(liSi);
            contactsByPhone.put(
                    liSi.getPhone(),
                    liSi
            );
        } else {
            System.out.println(liSi.getName()+  "手机号重复");
        }

        if (phoneNumbers.add(wangWu.getPhone())) {
            contacts.add(wangWu);
            contactsByPhone.put(
                    wangWu.getPhone(),
                    wangWu
            );
        } else {
            System.out.println(wangWu.getName()+ "手机号重复");
        }

        Contact foundContact =
                contactsByPhone.get("111");
        if (foundContact != null) {
            System.out.println("号码"+foundContact.getPhone()+"的人是：" + foundContact.getName());
            foundContact.setName("张小三");
            System.out.println(foundContact.getName());
        }

        Contact removedContact =
                contactsByPhone.remove("222");
        if (removedContact != null) {
            phoneNumbers.remove(removedContact.getPhone());
            contacts.remove(removedContact);
            System.out.println("删除手机号"+removedContact.getPhone()+"对应的"+removedContact.getName());
        }


        System.out.println("List大小：" + contacts.size());
        System.out.println("Set大小：" + phoneNumbers.size());
        System.out.println("Map大小：" + contactsByPhone.size());
    }

}