package com.ljf.learning.day13;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanRecordTest {
    @Test
    void startsAsActiveLoan(){
        LoanRecord record1 = new LoanRecord("B001","R001");

        assertEquals("B001",record1.getBookId());
        assertEquals("R001",record1.getReaderId());
        assertFalse(record1.isReturned());
    }

    @Test
    void marksActiveLoanAsReturned(){
        LoanRecord record1 = new LoanRecord("B001","R001");

        boolean result = record1.markReturned();
        assertTrue(result);
        assertTrue(record1.isReturned());
    }

    @Test
    void rejectsReturningSameLoanTwice(){
        LoanRecord record1 = new LoanRecord("B001","R001");
        boolean result1 = record1.markReturned();
        boolean result2 = record1.markReturned();
        assertTrue(result1);
        assertFalse(result2);
        assertTrue(record1.isReturned());
    }
}
