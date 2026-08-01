package com.ljf.learning.day01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloWorldTest {
    @Test
    void returnsExpectedGreeting() {
        assertEquals("Hello, Java 21!", HelloWorld.message());
    }
}

