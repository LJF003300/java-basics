package com.ljf.learning.day09;

import org.junit.jupiter.api.Test;

import static com.ljf.learning.day09.AgeValidationPractice.validateAge;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AgeValidationPracticeTest {
    @Test
    void throwsForAgeBelowZero(){
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validateAge(-1)
        );

        assertEquals(
                "年龄必须在0到120之间",
                exception.getMessage()
        );

    }
}
