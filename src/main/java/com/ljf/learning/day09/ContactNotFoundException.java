package com.ljf.learning.day09;

public class ContactNotFoundException
        extends RuntimeException {

    public ContactNotFoundException(String message) {
        super(message);
    }
}