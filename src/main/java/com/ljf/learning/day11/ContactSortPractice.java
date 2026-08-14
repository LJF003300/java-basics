package com.ljf.learning.day11;

import com.ljf.learning.day08.Contact;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactSortPractice {
    public static void main(String[] args) {
        List<Contact> list = new ArrayList<>();
        list.add(new Contact("Charlie","111"));
        list.add(new Contact("Alice","222"));
        list.add(new Contact("Bob","333"));

        List<Contact> emptyList = new ArrayList<>();

        Comparator<Contact> byName =
                Comparator.comparing(contact -> contact.getName());

        List<String> sortedNames = list.stream()
                .sorted(byName)
                .map(c -> c.getName())
                .toList();

        List<String> sortedEmptyNames = emptyList.stream()
                .sorted(byName)
                .map(c -> c.getName())
                .toList();

        System.out.println("空列表排序后大小：" + sortedEmptyNames.size());
        System.out.println(sortedNames);
    }
}
