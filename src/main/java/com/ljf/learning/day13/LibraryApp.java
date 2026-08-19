package com.ljf.learning.day13;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;

public class LibraryApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryService service = new LibraryService();

        LibraryFileStorage storage =
                new LibraryFileStorage();

        Path dataPath = Path.of(
                "data",
                "library-data.txt"
        );

        boolean running = true;

        while (running) {
            printMenu();

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addBook(scanner, service);
                case "2" -> removeBook(scanner, service);
                case "3" -> findBook(scanner, service);
                case "4" -> addReader(scanner, service);
                case "5" -> borrowBook(scanner,service);
                case "6" -> returnBook(scanner, service);
                case "7" -> saveData(
                        storage,
                        dataPath,
                        service
                );
                case "8" -> service = loadData(
                        storage,
                        dataPath,
                        service
                );
                case "0" -> running = false;
                default -> System.out.println("无效选项");
            }
        }

        System.out.println("程序已退出");
    }

    private static void printMenu() {
        System.out.println("=== 图书管理系统 ===");
        System.out.println("1. 新增图书");
        System.out.println("2. 删除图书");
        System.out.println("3. 查询图书");
        System.out.println("4. 新增读者");
        System.out.println("5. 借出图书");
        System.out.println("6. 归还图书");
        System.out.println("7. 保存数据");
        System.out.println("8. 加载数据");
        System.out.println("0. 退出");
        System.out.print("请选择：");
    }

    private static void addBook(
            Scanner scanner,
            LibraryService service
    ) {
        System.out.print("图书编号：");
        String bookId = scanner.nextLine();

        System.out.print("书名：");
        String title = scanner.nextLine();

        System.out.print("作者：");
        String author = scanner.nextLine();

        boolean added = service.addBook(
                new Book(bookId, title, author)
        );

        if (added) {
            System.out.println("新增成功");
        } else {
            System.out.println("图书编号已存在");
        }
    }

    private static void addReader(
            Scanner scanner,
            LibraryService service
    ) {
        System.out.print("读者编号：");
        String readerId = scanner.nextLine();

        System.out.print("读者姓名：");
        String name = scanner.nextLine();

        boolean added = service.addReader(
                new Reader(readerId, name)
        );

        if (added) {
            System.out.println("新增读者成功");
        } else {
            System.out.println("读者编号已存在");
        }
    }

    private static void removeBook(
            Scanner scanner,
            LibraryService service
    ) {
        System.out.print("图书编号：");
        String bookId = scanner.nextLine();

        Optional<Book> removed =
                service.removeBookById(bookId);

        removed.ifPresentOrElse(
                book -> System.out.println(
                        "删除成功："
                                + book.bookId()
                                + "，"
                                + book.title()
                ),
                () -> System.out.println("图书不存在")
        );
    }

    private static void findBook(
            Scanner scanner,
            LibraryService service
    ) {
        System.out.print("图书编号：");
        String bookId = scanner.nextLine();

        Optional<Book> result =
                service.findBookById(bookId);

        result.ifPresentOrElse(
                book -> System.out.println(
                        "查询成功："
                                + book.bookId()
                                + "，"
                                + book.title()
                                + "，"
                                + book.author()
                ),
                () -> System.out.println("图书不存在")
        );
    }

    private static void borrowBook(
            Scanner scanner,
            LibraryService service
    ) {
        System.out.print("图书编号：");
        String bookId = scanner.nextLine();

        System.out.print("读者编号：");
        String readerId = scanner.nextLine();

        try {
            LoanRecord record = service.borrowBook(
                    bookId,
                    readerId
            );

            System.out.println(
                    "借阅成功："
                            + record.getBookId()
                            + " -> "
                            + record.getReaderId()
            );
        } catch (IllegalArgumentException
                 | IllegalStateException exception) {
            System.out.println(
                    "借阅失败：" + exception.getMessage()
            );
        }
    }

    private static void returnBook(
            Scanner scanner,
            LibraryService service
    ) {
        System.out.print("图书编号：");
        String bookId = scanner.nextLine();

        try {
            LoanRecord record =
                    service.returnBook(bookId);

            System.out.println(
                    "归还成功："
                            + record.getBookId()
                            + " <- "
                            + record.getReaderId()
            );
        } catch (IllegalArgumentException
                 | IllegalStateException exception) {
            System.out.println(
                    "归还失败：" + exception.getMessage()
            );
        }
    }

    private static void saveData(
            LibraryFileStorage storage,
            Path dataPath,
            LibraryService service
    ) {
        try {
            storage.save(dataPath, service);

            System.out.println(
                    "保存成功：" + dataPath.toAbsolutePath()
            );
        } catch (IOException exception) {
            System.out.println(
                    "保存失败：" + exception.getMessage()
            );
        }
    }

    private static LibraryService loadData(
            LibraryFileStorage storage,
            Path dataPath,
            LibraryService currentService
    ) {
        try {
            LibraryService loadedService =
                    storage.load(dataPath);

            System.out.println(
                    "加载成功："
                            + loadedService.getBookCount()
                            + "本图书，"
                            + loadedService.getReaderCount()
                            + "位读者，"
                            + loadedService.getLoanRecordCount()
                            + "条借阅记录"
            );

            return loadedService;
        } catch (IOException | RuntimeException exception) {
            System.out.println(
                    "加载失败：" + exception.getMessage()
            );

            return currentService;
        }
    }
}