package com.ljf.learning.day03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrimeCheckerTest {
    @Test
    void returnsTrueForPrimeNumber(){
        int number = 7;
        boolean result = PrimeChecker.isPrime(number);
        assertTrue(result);
    }

    @Test
    void returnsFalseForCompositeNumber(){
        int number = 8;
        boolean result = PrimeChecker.isPrime(number);
        assertFalse(result);
    }

    @Test
    void returnsFalseForNumberBelowTwo(){
        int number = 1;
        boolean result = PrimeChecker.isPrime(number);
        assertFalse(result);
    }

    @Test
    void returnsTrueForSmallestPrimeNumber(){
        int number = 2;
        boolean result = PrimeChecker.isPrime(number);
        assertTrue(result);
    }
}
