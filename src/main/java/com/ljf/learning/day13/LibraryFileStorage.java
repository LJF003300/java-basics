package com.ljf.learning.day13;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.parseBoolean;

public class LibraryFileStorage {
    public void save(Path path, LibraryService service)
            throws IOException {
        Files.createDirectories(path.getParent());
        List<Book> books = service.getAllBooks();
        List<Reader> readers = service.getAllReaders();
        List<LoanRecord> records = service.getAllLoanRecords();
        List<String> lines = new ArrayList<>();

        books.stream().forEach(book -> lines.add("BOOK"+"|"+book.bookId()+"|"+book.title()+"|"+book.author()));
        readers.stream().forEach(reader -> lines.add("READER"+"|"+reader.readerId()+"|"+reader.name()));
        records.stream().forEach(record -> lines.add("LOAN"+"|"+record.getBookId()+"|"+record.getReaderId()+"|"+record.isReturned()));

        Files.write(path, lines, StandardCharsets.UTF_8);
    }

    public LibraryService load(Path path)
            throws IOException{
        List<String> lines = Files.readAllLines(
                path,
                StandardCharsets.UTF_8
        );

        LibraryService loadedService = new LibraryService();
        for(String line : lines){
            String[] parts = line.split("\\|",-1);
            if ("BOOK".equals(parts[0])) {
                if (parts.length != 4) {
                    throw new IllegalArgumentException(
                            "图书数据格式错误：" + line
                    );
                }

                loadedService.addBook(
                        new Book(parts[1], parts[2], parts[3])
                );
            }
            if("READER".equals(parts[0])){
                loadedService.addReader(new Reader(parts[1],parts[2]));
            }
            if("LOAN".equals(parts[0])){
                loadedService.borrowBook(parts[1],parts[2]);
                if(parseBoolean(parts[3])){
                    loadedService.returnBook(parts[1]);
                }
            }
        }
        return loadedService;

    }
}
