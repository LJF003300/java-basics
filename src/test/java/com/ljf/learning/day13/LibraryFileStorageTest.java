package com.ljf.learning.day13;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryFileStorageTest {
    @Test
    void savesLibraryData(@TempDir Path tempDir)
            throws IOException {
        LibraryFileStorage storage = new LibraryFileStorage();
        LibraryService service = new LibraryService();
        service.addBook(new Book("B001","Java","GPT"));
        service.addReader(new Reader("R001","LJF"));
        service.borrowBook("B001","R001");

        Path path = tempDir.resolve("library-data.txt");
        storage.save(path, service);
        List<String> lines = Files.readAllLines(
                path,
                StandardCharsets.UTF_8
        );


        assertEquals(3, lines.size());
        assertTrue(lines.contains(
                "BOOK|B001|Java|GPT"
        ));
        assertTrue(lines.contains(
                "READER|R001|LJF"
        ));
        assertTrue(lines.contains(
                "LOAN|B001|R001|false"
        ));
    }

    @Test
    void loadsLibraryData(@TempDir Path tempDir)
            throws IOException {
        Path path = tempDir.resolve("library-data.txt");

        Files.write(
                path,
                List.of(
                        "BOOK|B001|Java|GPT",
                        "READER|R001|LJF",
                        "LOAN|B001|R001|false"
                ),
                StandardCharsets.UTF_8
        );

        LibraryFileStorage storage =
                new LibraryFileStorage();

        LibraryService loadedService =
                storage.load(path);

        assertEquals(1, loadedService.getBookCount());
        assertEquals(1, loadedService.getReaderCount());
        assertEquals(1, loadedService.getLoanRecordCount());

        Book loadedBook = loadedService
                .findBookById("B001")
                .orElseThrow();

        assertEquals("Java", loadedBook.title());

        LoanRecord loadedRecord = loadedService
                .getAllLoanRecords()
                .get(0);

        assertEquals("B001", loadedRecord.getBookId());
        assertEquals("R001", loadedRecord.getReaderId());
        assertFalse(loadedRecord.isReturned());
    }

    @Test
    void roundTripsReturnedLoan(@TempDir Path tempDir)
            throws IOException {
        LibraryService originalService =
                new LibraryService();

        originalService.addBook(
                new Book("B001", "Java", "GPT")
        );
        originalService.addReader(
                new Reader("R001", "LJF")
        );

        originalService.borrowBook("B001", "R001");
        originalService.returnBook("B001");

        Path path = tempDir.resolve("library-data.txt");

        LibraryFileStorage storage =
                new LibraryFileStorage();

        storage.save(path, originalService);

        LibraryService loadedService =
                storage.load(path);

        assertEquals(
                1,
                loadedService.getLoanRecordCount()
        );

        LoanRecord loadedRecord = loadedService
                .getAllLoanRecords()
                .get(0);

        assertEquals("B001", loadedRecord.getBookId());
        assertEquals("R001", loadedRecord.getReaderId());
        assertTrue(loadedRecord.isReturned());
    }

    @Test
    void rejectsMalformedBookLine(@TempDir Path tempDir)
            throws IOException {
        Path path = tempDir.resolve("library-data.txt");

        Files.write(
                path,
                List.of("BOOK|B001|Java"),
                StandardCharsets.UTF_8
        );

        LibraryFileStorage storage =
                new LibraryFileStorage();

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> storage.load(path)
                );

        assertEquals(
                "图书数据格式错误：BOOK|B001|Java",
                exception.getMessage()
        );
    }
}
