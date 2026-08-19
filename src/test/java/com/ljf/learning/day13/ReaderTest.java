package com.ljf.learning.day13;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReaderTest {
    @Test
    void storesReaderInformation() {
        Reader reader01 = new Reader("R001","张三");
        assertEquals("R001",reader01.readerId());
        assertEquals("张三",reader01.name());

    }
}
