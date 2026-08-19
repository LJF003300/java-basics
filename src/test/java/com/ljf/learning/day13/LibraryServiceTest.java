package com.ljf.learning.day13;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryServiceTest {
    @Test
    void addsBookWhenIdIsNew(){
        LibraryService libraryService = new LibraryService();
        Book book = new Book("B001","Java","GPT");

        boolean result = libraryService.addBook(book);
        assertTrue(result);
        assertEquals(1,libraryService.getBookCount());

    }

    @Test
    void returnsBookWhenIdExists(){
        LibraryService service = new LibraryService();
        service.addBook(new Book("B001","Java","GPT"));
        Optional<Book> result = service.findBookById("B001");

        Book book = result.orElseThrow();
        assertEquals("B001",book.bookId());
        assertEquals("Java",book.title());
        assertEquals("GPT",book.author());
    }

    @Test
    void returnsEmptyWhenBookIdDoesNotExist(){
        LibraryService service = new LibraryService();
        service.addBook(new Book("B001","Java","GPT"));

        Optional<Book> result = service.findBookById("B999");
        assertTrue(result.isEmpty());
    }

    @Test
    void rejectsDuplicateBookIdWithoutReplacingOriginal(){
        LibraryService service = new LibraryService();
        boolean result1 = service.addBook(new Book("B001","Java","GPT"));
        boolean result2 = service.addBook(new Book("B001","Python","GPT2"));
        Book book = service.findBookById("B001").orElseThrow();

        assertTrue(result1);
        assertFalse(result2);
        assertEquals(1,service.getBookCount());
        assertEquals("Java",book.title());
        assertEquals("GPT",book.author());
    }

    @Test
    void removesExistingBookById(){
        LibraryService service = new LibraryService();
        Book book1 = new Book("B001","Java","GPT");
        Book book2 = new Book("B002","Python","GPT2");

        assertTrue(service.addBook(book1));
        assertTrue(service.addBook(book2));

        Optional<Book> removedBook = service.removeBookById("B001");
        Book bookRemoved = removedBook.orElseThrow();

        assertEquals(1,service.getBookCount());

        assertEquals("B001",bookRemoved.bookId());

        assertTrue(service.findBookById("B001").isEmpty());

        assertTrue(service.findBookById("B002").isPresent());

    }

    @Test
    void returnsEmptyWhenRemovingMissingBook(){
        LibraryService service = new LibraryService();
        Book book1 = new Book("B001","Java","GPT");
        assertTrue(service.addBook(book1));

        Optional<Book> removedBook = service.removeBookById("B999");

        assertEquals(1,service.getBookCount());
        assertTrue(service.findBookById("B001").isPresent());
        assertTrue(removedBook.isEmpty());
    }

    @Test
    void addsAndFindsReaderWhenIdIsNew(){
        LibraryService service = new LibraryService();
        Reader reader1 = new Reader("R001","张三");
        assertTrue(service.addReader(reader1));
        assertEquals(1,service.getReaderCount());

        Optional<Reader> result = service.findReaderById("R001");
        Reader reader2 = result.orElseThrow();
        assertEquals("R001",reader2.readerId());
        assertEquals("张三",reader2.name());
    }

    @Test
    void rejectsDuplicateReaderIdWithoutReplacingOriginal(){
        LibraryService service = new LibraryService();
        Reader reader01 = new Reader("R001","张三");
        assertTrue(service.addReader(reader01));
        assertFalse(service.addReader(new Reader("R001","李四")));
        assertEquals(1,service.getReaderCount());

        Optional<Reader> reader = service.findReaderById("R001");
        Reader reader1 = reader.orElseThrow();
        assertEquals("R001",reader1.readerId());
        assertEquals("张三",reader1.name());

    }

    @Test
    void borrowsExistingBookForExistingReader(){
        LibraryService service = new LibraryService();
        Book book1 = new Book("B001","Java","GPT");
        Reader reader1 = new Reader("R001","LJF");
        assertTrue(service.addReader(reader1));
        assertTrue(service.addBook(book1));

        LoanRecord loanRecord = service.borrowBook("B001","R001");
        assertEquals("B001",loanRecord.getBookId());
        assertEquals("R001",loanRecord.getReaderId());
        assertFalse(loanRecord.isReturned());
        assertEquals(1,service.getLoanRecordCount());
    }

    @Test
    void throwsWhenBorrowingMissingBook(){
        LibraryService service = new LibraryService();
        Book book1 = new Book("B001","Java","GPT");
        Reader reader1 = new Reader("R001","LJF");
        assertTrue(service.addReader(reader1));
        assertTrue(service.addBook(book1));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    ()->service.borrowBook("B999","R001"));
        assertEquals("图书不存在：B999",exception.getMessage());
        assertEquals(0,service.getLoanRecordCount());
    }

    @Test
    void throwsWhenBorrowingForMissingReader(){
        LibraryService service = new LibraryService();
        Book book1 = new Book("B001","Java","GPT");
        Reader reader1 = new Reader("R001","LJF");
        assertTrue(service.addReader(reader1));
        assertTrue(service.addBook(book1));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () ->  service.borrowBook("B001","R999")
                );
        assertEquals(
                "读者不存在：R999",
                exception.getMessage()
        );
        assertEquals(0,service.getLoanRecordCount());
    }

    @Test
    void throwsWhenBorrowingBookThatIsAlreadyBorrowed(){
        LibraryService service = new LibraryService();
        Book book1 = new Book("B001","Java","GPT");
        Reader reader1 = new Reader("R001","LJF");
        Reader reader2 = new Reader("R002","LJF2");
        assertTrue(service.addReader(reader1));
        assertTrue(service.addReader(reader2));
        assertTrue(service.addBook(book1));
        LoanRecord record = service.borrowBook("B001","R001");
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> service.borrowBook("B001","R002")
                );
        assertEquals(
                "图书已借出：B001",
                exception.getMessage()
        );
        assertEquals(1,service.getLoanRecordCount());
        assertEquals("R001",record.getReaderId());
        assertEquals("B001",record.getBookId());
    }

    @Test
    void returnsBorrowedBook(){
        LibraryService service = new LibraryService();
        Book book1 = new Book("B001","Java","GPT");
        Reader reader1 = new Reader("R001","LJF");
        assertTrue(service.addReader(reader1));
        assertTrue(service.addBook(book1));

        LoanRecord borrowedRecord = service.borrowBook("B001","R001");
        LoanRecord returnedRecord = service.returnBook("B001");

        assertSame(borrowedRecord, returnedRecord);
        assertTrue(returnedRecord.isReturned());
        assertEquals(1,service.getLoanRecordCount());
    }

    @Test
    void throwsWhenReturningBookWithoutActiveLoan(){
        LibraryService service = new LibraryService();
        Book book1 = new Book("B001","Java","GPT");
        assertTrue(service.addBook(book1));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> service.returnBook("B001"));
        assertEquals("不存在未归还的借阅记录：B001",
                exception.getMessage());
        assertEquals(0,service.getLoanRecordCount());
    }

    @Test
    void allowsBorrowingAgainAfterReturn(){
        LibraryService service = new LibraryService();
        Book book1 = new Book("B001","Java","GPT");
        Reader reader1 = new Reader("R001","LJF");
        Reader reader2 = new Reader("R002","LJF2");
        assertTrue(service.addReader(reader1));
        assertTrue(service.addReader(reader2));
        assertTrue(service.addBook(book1));
        LoanRecord firstRecord = service.borrowBook("B001","R001");
        service.returnBook("B001");
        LoanRecord secondRecord = service.borrowBook("B001","R002");

        assertTrue(firstRecord.isReturned());
        assertFalse(secondRecord.isReturned());
        assertEquals("R002",secondRecord.getReaderId());
        assertNotSame(firstRecord,secondRecord);
        assertEquals(2,service.getLoanRecordCount());
    }

    @Test
    void returnsCollectionSnapshotsForPersistence(){
        LibraryService service = new LibraryService();
        Book book1 = new Book("B001","Java","GPT");
        Reader reader1 = new Reader("R001","LJF");
        assertTrue(service.addReader(reader1));
        assertTrue(service.addBook(book1));
        LoanRecord firstRecord = service.borrowBook("B001","R001");

        List<Book> books = service.getAllBooks();
        List<Reader> readers = service.getAllReaders();
        List<LoanRecord> records = service.getAllLoanRecords();
        assertEquals(1,books.size());
        assertEquals(1,readers.size());
        assertEquals(1,records.size());

        Optional<Book> exitBook = service.getAllBooks().stream()
                .filter(book -> book.bookId().equals("B001"))
                .findFirst();
        assertTrue(exitBook.isPresent());

        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> books.add(new Book("B002","Python","Mee")));
        assertEquals(1,books.size());
    }
}
