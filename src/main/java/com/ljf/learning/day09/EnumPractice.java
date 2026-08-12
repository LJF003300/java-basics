package com.ljf.learning.day09;

public class EnumPractice {
    public static void main(String[] args) {
        ContactStatus status = ContactStatus.PENDING;
        System.out.println("当前状态：" + status);
        switch (status) {
            case ACTIVE:
                System.out.println("联系人可以正常使用");
                break;
            case DISABLED:
                System.out.println("联系人已停用");
                break;
            case PENDING:
                System.out.println("联系人等待验证");
                break;
        }

        try{
            ContactStatus contactStatus = parseStatus("active");
            System.out.println("转换后状态：" + contactStatus);
        }catch (IllegalArgumentException e){
            System.out.println("状态输入无效： "+"active");
        }
        try {
            ContactStatus contactStatus = parseStatus("pending");
            System.out.println("转换后状态："+contactStatus);
        }catch (IllegalArgumentException e){
            System.out.println("状态输入无效：" + "pending");
        }
        try {
            ContactStatus contactStatus = parseStatus("unknown");
            System.out.println("转换后状态：" + contactStatus);
        }catch (IllegalArgumentException e){
            System.out.println("状态输入无效："+"unknown");
        }
        System.out.println("程序继续进行");

    }

    static ContactStatus parseStatus(String input){
        return ContactStatus.valueOf(input.toUpperCase());
    }
}
