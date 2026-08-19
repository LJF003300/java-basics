package com.ljf.learning.day13;

import java.util.*;

public class LibraryService {
    private final Map<String, Book> booksById = new HashMap<>();
    private final Map<String, Reader> readersById = new HashMap<>();
    private final List<LoanRecord> loanRecords = new ArrayList<>();

    public boolean addBook(Book book){
        if(!booksById.containsKey(book.bookId())) {
            booksById.put(book.bookId(), book);
            return true;
        }else{
            return false;
        }
    }

    public Optional<Book> findBookById(String bookId){
        return Optional.ofNullable(booksById.get(bookId));
    }

    public Optional<Book> removeBookById(String bookId){
        return Optional.ofNullable(booksById.remove(bookId));
    }

    public boolean addReader(Reader reader){
        if(!readersById.containsKey(reader.readerId())) {
            readersById.put(reader.readerId(), reader);
            return true;
        }else {
            return false;
        }
    }

    public Optional<Reader> findReaderById(String readerId){
        return Optional.ofNullable(readersById.get(readerId));
    }

    public int getReaderCount(){
        return readersById.size();
    }

    public int getBookCount(){
        return booksById.size();
    }

    public LoanRecord borrowBook(String bookId, String readerId){
        if(!booksById.containsKey(bookId)){
            throw new IllegalArgumentException(
                    "图书不存在：" + bookId
            );
        }else if(!readersById.containsKey(readerId)){
            throw new IllegalArgumentException(
                    "读者不存在：" + readerId
            );
        }

        boolean alreadyBorrowed = loanRecords.stream()
                .anyMatch(record -> record.getBookId().equals(bookId)
                                                && !record.isReturned());
        if(alreadyBorrowed) {
            throw new IllegalStateException(
                    "图书已借出：" + bookId
            );
        }
        LoanRecord loanRecord = new LoanRecord(bookId, readerId);
        loanRecords.add(loanRecord);
        return loanRecord;
    }

    public LoanRecord returnBook(String bookId){
        if(!booksById.containsKey(bookId)){
            throw new IllegalArgumentException(
                    "图书不存在：" + bookId
            );
        }

        LoanRecord record = loanRecords.stream()
                .filter(item ->
                        item.getBookId().equals(bookId)
                                && !item.isReturned()
                )
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException(
                                "不存在未归还的借阅记录：" + bookId
                        )
                );
        record.markReturned();
        return record;
    }

    public int getLoanRecordCount(){
        return loanRecords.size();
    }

    public List<Book> getAllBooks(){
        return List.copyOf(booksById.values());
    }
    public List<Reader> getAllReaders(){
        return List.copyOf(readersById.values());
    }
    public List<LoanRecord> getAllLoanRecords(){
        return List.copyOf(loanRecords);
    }
}
