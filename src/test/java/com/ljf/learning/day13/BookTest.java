package com.ljf.learning.day13;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {
    @Test
    void storesBookInformation(){
        Book book01 = new Book("B001","Effective Java","Joshua Bloch");

        assertEquals("B001",book01.bookId());
        assertEquals("Effective Java",book01.title());
        assertEquals("Joshua Bloch",book01.author());
    }
}
