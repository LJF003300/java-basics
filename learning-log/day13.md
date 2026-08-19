# Day13 学习日志（命令行图书管理系统完成）

## 今日完成

- 使用 `Book`、`Reader` 和 `LoanRecord` 表达图书、读者与一次借阅事件。
- 使用 `LibraryService` 集中维护新增、删除、查询、借出和归还规则。
- 使用 `Map<String, Book>`、`Map<String, Reader>` 按唯一 ID 保存实体，使用 `List<LoanRecord>` 保留借阅历史。
- 使用 `Optional<Book>` 和 `Optional<Reader>` 表达查询或删除结果可能不存在。
- 拒绝重复图书 ID、重复读者 ID、重复借出、缺失图书或读者以及不存在的未归还记录。
- 使用 `List.copyOf` 返回不可增删的集合快照，避免调用者绕过服务方法修改集合结构。
- 使用 `LibraryFileStorage` 将图书、读者和借阅记录保存为 UTF-8 文本，并从文件恢复新的 `LibraryService`。
- 使用 `@TempDir` 隔离文件测试，完成保存、加载、已归还记录往返和错误图书行测试。
- 创建 `LibraryApp` 菜单，接入新增、删除、查询、添加读者、借出、归还、保存和加载。

## 验证证据

- `LibraryServiceTest` 共 16 个测试，覆盖新增、查找、删除、重复 ID、借阅前置条件、重复借阅、归还和再次借阅。
- `LibraryFileStorageTest` 共 4 个测试，覆盖三类数据保存、正常加载、已归还记录往返和错误图书行。
- `BookTest`、`ReaderTest`、`LoanRecordTest` 共 5 个测试，验证实体字段与归还状态变化。
- 手动验证同一图书首次借阅成功、重复借阅失败，首次归还成功、重复归还失败。
- 手动验证保存文件包含 `BOOK`、`READER`、`LOAN` 三类记录。
- 退出并重新运行程序后，加载得到 1 本图书、1 位读者和 1 条未归还记录；查询成功且重复借出被拒绝。
- 最终执行 `mvn test`：全项目共 36 个测试，0 失败、0 错误、0 跳过，构建成功。

## 我现在能独立解释什么

- 为什么图书和读者适合按唯一 ID 放入 `Map`，借阅记录适合放入 `List` 保留历史。
- 为什么 `borrowBook` 返回新建的 `LoanRecord`，而不是只返回 `boolean`。
- 为什么归还应修改借阅记录的 `returned` 状态，而不是删除历史记录。
- 为什么 `Optional.empty()` 不是 `null`，以及如何使用 `ifPresentOrElse`分别处理存在和不存在。
- 为什么 `List.copyOf` 能阻止增删列表元素，却不能让其中的可变对象变成不可变对象。
- 为什么保存时先把对象格式化为 `List<String>`，再只调用一次 `Files.write`。
- 为什么加载方法返回新的 `LibraryService` 后，调用者必须使用变量接住并替换旧引用。
- 为什么业务层负责抛出异常，命令行入口负责捕获并向用户显示提示。

## 今日纠正的关键问题

- 创建 `Book`、`Reader`、`LoanRecord` 对象不等于它们已经进入 `LibraryService`；必须调用服务方法建立受规则约束的状态。
- `Path.of("tempDir", ...)` 只是普通相对路径；`@TempDir Path tempDir` 才由 JUnit 注入临时目录。
- 测试文件读写会抛出 checked exception，测试方法需要声明 `throws IOException` 或自行捕获。
- `split("\\|", -1)` 中需要转义正则表达式的 `|`，并保留末尾空字段。
- 文件中的 `LOAN` 必须在对应 `BOOK` 和 `READER` 加载后恢复，否则业务前置条件不成立。
- 只调用 `storage.load(path)` 而忽略返回值，不会改变菜单当前持有的 `service`。
- `main` 中的文件异常已经由保存和加载辅助方法处理，因此不需要继续声明 `throws IOException`。

## 下一步

- 进入第二周复盘，整理 Git 分支与本周综合练习。
