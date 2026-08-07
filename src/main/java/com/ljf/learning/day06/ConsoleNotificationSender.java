package com.ljf.learning.day06;

public class ConsoleNotificationSender
        implements NotificationSender {

    @Override
    public void send(String message) {
        System.out.println("控制台通知：" + message);
    }
}