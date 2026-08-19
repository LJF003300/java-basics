package com.ljf.learning.day13;

public class LoanRecord {
    private final String bookId;
    private final String readerId;
    private boolean returned;

    public LoanRecord(String bookId, String readerId) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.returned = false;

    }
    public String getBookId() {
        return bookId;
    }
    public String getReaderId() {
        return readerId;
    }
    public boolean isReturned() {
        return returned;
    }

    public boolean markReturned() {
        if(!returned) {
            this.returned = true;
            return true;
        }else  {
            return false;
        }
    }
}
