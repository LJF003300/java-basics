# 图书管理系统速记

## 1. 实体与业务服务分工

```text
Book       = 图书数据
Reader     = 读者数据
LoanRecord = 一次借阅事件及其归还状态

LibraryService = 维护集合和业务规则
LibraryApp     = 读取命令、调用服务、显示结果
```

实体尽量只表达数据和自身状态，跨实体规则集中放在服务中。例如，能否借书需要同时检查图书、读者和未归还记录，因此属于 `LibraryService`。

## 2. 为什么使用 Map 和 List

```java
Map<String, Book> booksById;
Map<String, Reader> readersById;
List<LoanRecord> loanRecords;
```

- 图书 ID 和读者 ID 唯一，适合用 `Map` 快速定位并防止重复。
- 同一本书在不同时间可以发生多次借阅，借阅记录需要保留历史，因此使用 `List`。
- 重复 ID 不能直接 `put` 覆盖原对象，应先检查并返回失败。

## 3. 借出与归还

借书的前置条件：

```text
图书存在
且读者存在
且没有同一本书的未归还记录
```

成功借出时创建、保存并返回同一个记录对象：

```java
LoanRecord record = new LoanRecord(bookId, readerId);
loanRecords.add(record);
return record;
```

归还不是删除记录，而是修改状态：

```java
record.markReturned();
```

`returned=true` 表示借阅已经结束，不表示借阅从未发生。删除记录会丢失历史。

## 4. Optional 表达可能不存在

```java
Optional<Book> result = service.findBookById(bookId);
```

```text
Optional.of(book) = 找到了一个 Book
Optional.empty()  = 没有找到
```

命令行中可以分别处理两个分支：

```java
result.ifPresentOrElse(
        book -> System.out.println(book.title()),
        () -> System.out.println("图书不存在")
);
```

第二个 Lambda 没有参数，因为不存在可传入的 `Book`。

## 5. 不可增删的集合快照

```java
public List<LoanRecord> getAllLoanRecords() {
    return List.copyOf(loanRecords);
}
```

调用者不能对返回列表执行 `add`、`remove` 或 `clear`，因此不能绕过服务规则改变集合结构。

但这是浅拷贝：列表保存的仍是原对象引用。

```text
快照列表（不可增删）
    └── 引用 ──> 原来的 LoanRecord（仍可能改变自身状态）
```

## 6. 对象与文本的往返

当前文本格式：

```text
BOOK|B001|Java|GPT
READER|R001|LJF
LOAN|B001|R001|false
```

保存的数据流：

```text
LibraryService
→ 三个集合快照
→ List<String>
→ Files.write
```

加载的数据流：

```text
Path
→ Files.readAllLines
→ split("\\|", -1)
→ Book / Reader / LoanRecord
→ 新的 LibraryService
```

正则表达式中的 `|` 有特殊含义，因此需要写成 `"\\|"`。`-1`用于保留末尾空字段。

加载借阅记录前必须先加载图书和读者，因为 `borrowBook` 会验证二者存在。

## 7. 返回的新服务必须被接住

错误用法：

```java
storage.load(dataPath);
```

加载得到的新对象没有保存，当前`service`仍指向旧对象。

正确用法：

```java
service = storage.load(dataPath);
```

加载失败时保留旧对象：

```java
try {
    return storage.load(dataPath);
} catch (IOException | RuntimeException exception) {
    return currentService;
}
```

## 8. 业务层与界面层的异常职责

```text
LibraryService
→ 判断业务规则
→ 失败时抛出异常

LibraryApp
→ 捕获异常
→ 决定向用户显示什么
```

服务层不固定打印内容，因此以后可以被测试、命令行或其他入口复用。

## 9. 文件测试与 @TempDir

```java
@Test
void savesLibraryData(@TempDir Path tempDir)
        throws IOException {
    Path path = tempDir.resolve("library-data.txt");
}
```

JUnit创建并注入临时目录，测试不会依赖或污染项目中的真实数据文件。

加载测试直接写入确定的文本，再调用 `load`，可以把加载问题与保存问题隔离。往返测试则验证：

```text
原对象 → 保存 → 加载 → 恢复对象状态一致
```

## 10. 自检问题

1. 为什么图书使用 `Map`，借阅历史使用 `List`？
2. 为什么归还图书不能直接删除 `LoanRecord`？
3. 为什么 `List.copyOf` 不能保证内部对象完全不可变？
4. 为什么测试中创建实体后，还必须通过 `LibraryService` 加入数据？
5. 为什么 `@TempDir` 与 `Path.of("tempDir")`不是同一件事？
6. 为什么加载借阅记录前必须先恢复图书和读者？
7. 为什么必须用变量接住 `load` 返回的新 `LibraryService`？
8. 为什么业务异常由服务抛出、由命令行入口负责显示？

对应源码：

- `src/main/java/com/ljf/learning/day13/Book.java`
- `src/main/java/com/ljf/learning/day13/Reader.java`
- `src/main/java/com/ljf/learning/day13/LoanRecord.java`
- `src/main/java/com/ljf/learning/day13/LibraryService.java`
- `src/main/java/com/ljf/learning/day13/LibraryFileStorage.java`
- `src/main/java/com/ljf/learning/day13/LibraryApp.java`
- `src/test/java/com/ljf/learning/day13/LibraryServiceTest.java`
- `src/test/java/com/ljf/learning/day13/LibraryFileStorageTest.java`
