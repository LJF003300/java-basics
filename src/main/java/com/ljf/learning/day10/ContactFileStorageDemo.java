package com.ljf.learning.day10;

import com.ljf.learning.day08.Contact;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ContactFileStorageDemo {
    public static void main(String[] args) {
        Path path = Path.of(
                "data",
                "save-contacts.txt"
        );
        List<Contact> original = new ArrayList<>();
        original.add(new Contact("张三","111"));
        original.add(new Contact("李四","222"));
        original.add(new Contact("王五","333"));

        ContactFileStorage contactFileStorage = new ContactFileStorage();

        //空文件创立
        try {
            List<Contact> emptyContacts =
                    new ArrayList<>();
            Path emptyPath = Path.of(
                    "data",
                    "empty-contacts.txt"
            );
            contactFileStorage.save(emptyPath, emptyContacts);
            List<Contact> loadedEmpty =
                    contactFileStorage.load(emptyPath);

            System.out.println(
                    "空列表加载数量："
                            + loadedEmpty.size()
            );
        }catch (IOException e){
            System.out.println("文件处理失败：" + e.getMessage());
        }

        //加载不存在联系人文件
        try{
            Path missingPath = Path.of(
                    "data",
                    "not-found-contacts.txt"
            );

            contactFileStorage.load(missingPath);
        }catch (NoSuchFileException e) {
            System.out.println(
                    "联系人文件不存在："
                            + e.getFile()
            );
        } catch (IOException e) {
            System.out.println(
                    "联系人文件读取失败："
                            + e.getMessage()
            );
        }


        //将初始List<Contact> original存入contacts里
        try{
               contactFileStorage.save(path,original);
        } catch(IOException e){
            System.out.println(e.getMessage());
        }

        //逐条打印contacts里的各项信息
        try{
            List<Contact> loaded = contactFileStorage.load(path);
            for(Contact contact:loaded){
                System.out.println(contact.getName() + "手机号:" + contact.getPhone());
            }
            System.out.println("加载数量：" + loaded.size());
            boolean same = original.getFirst().getName().equals(loaded.getFirst().getName()) && original.getFirst().getPhone().equals(loaded.getFirst().getPhone());
            System.out.println("首条数据一致：" + same);
        } catch(IOException e){
            System.out.println(e.getMessage());
        }

        //验证异常触发
        try{
            Path invalidPath = Path.of(
                    "data",
                    "invalid-contacts.txt"
            );

            List<String> invalidLines = List.of(
                    "张三,111",
                    "错误行",
                    "李四,222"
            );

            Files.write(
                    invalidPath,
                    invalidLines,
                    StandardCharsets.UTF_8
            );
            contactFileStorage.load(invalidPath);
        }catch (IOException e) {
            System.out.println(
                    "文件处理失败：" + e.getMessage()
            );
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "联系人格式失败：" + e.getMessage()
            );
        }
        System.out.println("程序继续进行");

    }
}
