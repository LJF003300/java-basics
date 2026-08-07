package com.ljf.learning.day06;

public class NotificationDemo {
    public static void main(String[] args) {
        NotificationSender consoleSender =
                new ConsoleNotificationSender();

        NotificationSender emailSender =
                new EmailNotificationSender("123@example.com");

        sendNotification(consoleSender, "课程即将开始");
        sendNotification(emailSender, "课程即将开始");

    }

    public static void sendNotification(
            NotificationSender sender,
            String message
    ) {
        sender.send(message);
    }
}