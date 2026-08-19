package com.ljf.learning.day09;

import com.ljf.learning.day08.Contact;

import java.util.*;

public class ContactService {

    private final List<Contact> contacts = new ArrayList<>();
    private final Set<String> phoneNumbers = new HashSet<>();
    private final Map<String, Contact> contactsByPhone =
            new HashMap<>();

    public void addContact(Contact contact){
        if(contactsByPhone.containsKey(contact.getPhone())){
            throw new DuplicatePhoneException(
                    "手机号" + contact.getPhone() + "已存在"
            );
        }else {
            contactsByPhone.put(contact.getPhone(), contact);
            contacts.add(contact);
            phoneNumbers.add(contact.getPhone());
        }
    }

    public Contact findByPhone(String phone){
        if(!contactsByPhone.containsKey(phone)){
            throw new ContactNotFoundException(
                    "手机号" + phone +"不存在"
            );
        }
        return contactsByPhone.get(phone);
    }

    public int getContactCount(){
        return contacts.size();
    }
    public int getPhoneNumberCount(){
        return phoneNumbers.size();
    }
    public int getContactMapCount(){
        return contactsByPhone.size();
    }
}
