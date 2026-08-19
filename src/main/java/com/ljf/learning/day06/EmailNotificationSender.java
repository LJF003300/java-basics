package com.ljf.learning.day06;


public class EmailNotificationSender
        implements NotificationSender {
    private final String email;

    public EmailNotificationSender(String email) {
        this.email = email;
    }

    @Override
    public void send(String message) {
        System.out.println("向 " + email + " 发送邮件：" + message);
    }
}
