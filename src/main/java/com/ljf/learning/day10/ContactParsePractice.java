package com.ljf.learning.day10;

import com.ljf.learning.day08.Contact;

public class ContactParsePractice {
    static Contact parseContact(String line) {
        String[] parts = line.split(",", -1);

        if (parts.length != 2
                || parts[0].isEmpty()
                || parts[1].isEmpty()) {
            throw new IllegalArgumentException(
                    "联系人行格式错误：" + line
            );
        }

        return new Contact(parts[0], parts[1]);
    }

    static String formatContact(Contact contact) {
        return contact.getName() + "," + contact.getPhone();
    }

    public static void main(String[] args) {
        try{
            Contact c = parseContact("张三,111");
            System.out.println("姓名：" + c.getName() + "；手机号：" + c.getPhone());
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        try{
            Contact c = parseContact("错误行");
            System.out.println("姓名：" + c.getName() + "；手机号：" + c.getPhone());
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        try{
            Contact c = parseContact("李四,");
            System.out.println("姓名：" + c.getName() + "；手机号：" + c.getPhone());
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println("程序继续进行");

        Contact original =
                new Contact("赵六", "666");

        String line = formatContact(original);

        Contact restored =
                parseContact(line);
        System.out.println("保存文本："+line);
        System.out.println("恢复姓名："+restored.getName());
        System.out.println("恢复手机号："+restored.getPhone());

        boolean same =
                original.getName().equals(restored.getName())
                        && original.getPhone().equals(restored.getPhone());

        System.out.println("数据一致：" + same);
    }

}
