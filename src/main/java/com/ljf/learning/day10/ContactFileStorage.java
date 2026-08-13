package com.ljf.learning.day10;

import com.ljf.learning.day08.Contact;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ContactFileStorage {
    public void save(
            Path path,
            List<Contact> contacts
    ) throws IOException{
        Files.createDirectories(path.getParent());
        List<String> lines = new ArrayList<>();

        for (Contact contact : contacts) {
            lines.add(formatContact(contact));
        }

        Files.write(
                path,
                lines,
                StandardCharsets.UTF_8
        );
    }

    public List<Contact> load(
            Path path
    ) throws IOException{
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        List<Contact> contacts = new ArrayList<>();
        for (String line : lines) {
            contacts.add(parseContact(line));
        }
        return contacts;
    }

    private String formatContact(Contact contact){
        return contact.getName() + "," + contact.getPhone();
    }

    private Contact parseContact(String line){
        String[] parts = line.split(",",-1);
        if(parts.length != 2
                ||parts[0].isEmpty()
                || parts[1].isEmpty()) {
            throw new IllegalArgumentException(
                    "联系人行格式错误：" + line
            );
        }
        return new Contact(parts[0], parts[1]);

    }
}
