# 文件与 IO 速记

## 1. Path 表示文件位置

```java
Path path = Path.of(
        "data",
        "day10-note.txt"
);
```

相对路径从程序当前工作目录开始解析。查看绝对路径：

```java
path.toAbsolutePath()
```

写文件前可以确保父目录存在：

```java
Files.createDirectories(path.getParent());
```

- 目录不存在：创建目录。
- 目录已存在：正常继续。

## 2. 使用 UTF-8 写入和读取文本

写入完整字符串：

```java
Files.writeString(
        path,
        "Java 文件写入练习",
        StandardCharsets.UTF_8
);
```

读取完整字符串：

```java
String content = Files.readString(
        path,
        StandardCharsets.UTF_8
);
```

明确指定 UTF-8，可以降低中文乱码风险。终端显示乱码不一定表示文件损坏，应按相同编码读取并核对实际内容。

`Files.writeString` 默认覆盖原内容，不会自动追加。只有显式使用 `StandardOpenOption.APPEND` 等选项时才会追加。

## 3. 空文件与不存在文件

```text
文件存在但内容为空 → 读取成功，返回空字符串 ""
文件不存在         → 读取失败，抛出 IOException 的具体子类
```

不存在文件不会被 `Files.readString` 自动创建，也不会用空字符串伪装成读取成功。

```java
try {
    String content = Files.readString(
            path,
            StandardCharsets.UTF_8
    );
} catch (IOException exception) {
    System.out.println(
            "读取失败：" + exception.getMessage()
    );
}
```

### 便条：checked 与 unchecked exception

```text
IOException
→ 继承 Exception
→ 属于 checked exception
→ 编译器要求：必须 catch，或者在方法上写 throws

IllegalArgumentException
→ 继承 RuntimeException
→ 属于 unchecked exception
→ 编译器不强制 catch，也不强制写 throws
```

因此，包含文件读取的存储方法可以声明：

```java
public List<Contact> load(Path path)
        throws IOException
```

如果删除 `throws IOException`，方法内部又不使用 `try-catch`，代码无法编译。

`IllegalArgumentException` 即使不写：

```java
throws IllegalArgumentException
```

也能自然向调用者传播。调用者若希望程序在格式错误后继续运行，仍可以主动捕获它。

```text
“不必声明”不等于“不会发生”。
“不强制捕获”不等于“不应该处理”。
```

捕获有继承关系的异常时，具体子类必须写在宽泛父类前面：

```java
try {
    storage.load(path);
} catch (NoSuchFileException exception) {
    // 文件不存在
} catch (IOException exception) {
    // 其他文件读取失败
}
```

如果先写 `catch (IOException)`，它已经覆盖所有 `NoSuchFileException`，后面的子类分支永远无法到达，编译器会拒绝。

## 4. 多行文本

写入多行：

```java
List<String> lines = List.of(
        "张三,111",
        "李四,222",
        "王五,333"
);

Files.write(
        path,
        lines,
        StandardCharsets.UTF_8
);
```

读取多行：

```java
List<String> loadedLines = Files.readAllLines(
        path,
        StandardCharsets.UTF_8
);
```

`readAllLines` 将每一行保存为一个 `String` 元素，因此 `loadedLines.size()` 表示行数，不是字符数。

## 5. 从联系人文本恢复对象

当前约定一行保存一个联系人：

```text
姓名,手机号
```

例如：

```text
张三,111
```

解析方法的契约：

```text
成功 → 返回真实 Contact
失败 → 抛出 IllegalArgumentException
绝不返回 null 假装解析成功
```

```java
static Contact parseContact(String line) {
    String[] parts = line.split(",", -1);

    if (parts.length != 2
            || parts[0].isEmpty()
            || parts[1].isEmpty()) {
        throw new IllegalArgumentException(
                "联系人行格式错误：" + line
        );
    }

    return new Contact(parts[0], parts[1]);
}
```

### 为什么使用 split(",", -1)

```java
"李四,".split(",")
```

默认可能丢弃末尾空字段，不利于明确识别空手机号。

```java
"李四,".split(",", -1)
```

保留末尾空字段，结果相当于：

```text
["李四", ""]
```

因此可以使用 `parts[1].isEmpty()` 判断手机号缺失。

文件格式使用英文半角逗号 `,`，写入与解析必须保持一致；中文全角逗号 `，` 是不同字符。

## 6. 电话号码为什么保存为 String

电话号码看起来由数字组成，但它不是用于数学计算的数字：

- 可能有前导零；
- 可能包含国家区号 `+86`；
- 可能包含空格或短横线；
- 转为数值可能改变原始格式。

因此联系人中的手机号继续使用 `String`。如果业务明确要求纯数字，可以校验字符串格式：

```java
if (!phone.matches("\\d+")) {
    throw new IllegalArgumentException(
            "手机号必须为纯数字：" + phone
    );
}
```

格式校验不等于把手机号转换成 `int` 或 `long`。当前练习阶段只验证字段数量和非空，纯数字规则等待业务要求明确后再加入。

## 7. 测试样例与业务硬编码

练习代码中的：

```text
张三,111 = 正常样例
错误行   = 字段数量错误样例
李四,    = 空手机号边界样例
```

这些固定输入是为了验证不同分支，不是问题。真正的解析逻辑接收任意 `line` 并按照格式判断，因此可以复用。

需要避免的是只识别固定字符串：

```java
if (line.equals("错误行")) {
    throw new IllegalArgumentException();
}
```

这种写法没有根据通用格式规则判断，才属于不合理的业务硬编码。

## 8. 异常处理职责

### 核心原则：返回有效结果，或者抛出异常

```text
parseContact：成功就返回真实 Contact，失败就抛出异常
调用者：调用 parseContact，并决定如何捕获和处理异常
```

一个方法不应该在失败时打印一句提示，再返回 `null` 假装自己正常结束。更清晰的方法契约是：

```text
正常出口 = return Contact
失败出口 = throw IllegalArgumentException
```

解析方法：

```java
static Contact parseContact(String line) {
    String[] parts = line.split(",", -1);

    if (parts.length != 2
            || parts[0].isEmpty()
            || parts[1].isEmpty()) {
        throw new IllegalArgumentException(
                "联系人行格式错误：" + line
        );
    }

    return new Contact(parts[0], parts[1]);
}
```

解析方法不应在内部捕获异常、打印后返回 `null`：

```java
// 不推荐
static Contact parseContact(String line) {
    try {
        // 解析与校验
    } catch (IllegalArgumentException exception) {
        System.out.println(exception.getMessage());
        return null;
    }
}
```

这样做会把“解析失败”伪装成一个普通的 `null` 返回值。调用者如果忘记额外判空，可能继续调用 getter，产生与原始格式错误不同的 `NullPointerException`。

```text
解析方法：返回对象或抛出异常
调用者：捕获异常并决定如何展示
```

调用者可以自然调用：

```java
try {
    Contact contact = parseContact(line);
    System.out.println(contact.getName());
} catch (IllegalArgumentException exception) {
    System.out.println(exception.getMessage());
}
```

解析失败后，赋值和后续 getter 调用都不会执行，控制流直接进入 `catch`，从而避免返回 `null` 后产生额外的 `NullPointerException`。

控制流可以记成：

```text
合法行
  → parseContact return Contact
  → 调用者继续使用 Contact

非法行
  → parseContact throw
  → 调用者 try 中剩余语句被跳过
  → 进入 catch 处理原始格式错误
```

这种设计的价值是：解析方法只负责判断数据能否成为有效对象，控制台、日志、接口或界面等不同调用者可以自行决定如何展示同一个异常。

## 9. 联系人列表保存与加载

保存方法的职责：

```text
List<Contact>
→ 逐个 formatContact
→ List<String>
→ Files.write 一次写入文件
```

`Files.write` 应放在收集循环之后：

```java
List<String> lines = new ArrayList<>();

for (Contact contact : contacts) {
    lines.add(formatContact(contact));
}

Files.write(path, lines, StandardCharsets.UTF_8);
```

- 三个联系人：只执行一次文件写入。
- 空联系人 List：仍执行一次写入，创建或覆盖为 0 字节空文件。
- 如果写入放在循环内，三个联系人会覆盖写三次；空 List 时则一次也不写，无法清空旧文件。

加载方法的职责：

```text
Files.readAllLines
→ List<String>
→ 逐行 parseContact
→ List<Contact>
→ 全部成功后 return
```

```java
List<String> lines = Files.readAllLines(
        path,
        StandardCharsets.UTF_8
);

List<Contact> contacts = new ArrayList<>();

for (String line : lines) {
    contacts.add(parseContact(line));
}

return contacts;
```

如果第二行格式错误，异常会立即结束 `load`，后面的 `return contacts` 不会执行。即使第一行已被加入方法内部的局部 List，调用者也拿不到这个部分结果。

验证加载功能时，必须检查 `load` 返回的 List，不能检查原始输入 List；原始空 List 本来就是 0，不能证明文件被正确加载。

## 10. 对象与文本往返

```text
formatContact：Contact → String
parseContact： String  → Contact
```

往返验证应比较原对象与恢复对象的字段内容：

```java
boolean same =
        original.getName().equals(restored.getName())
        && original.getPhone().equals(restored.getPhone());
```

`original == restored` 比较两个引用是否指向同一个对象。序列化后重新创建的 `Contact` 是另一个对象，所以即使字段完全相同，通常也不是同一对象。

输出“恢复姓名”和“恢复手机号”时，也必须读取 `restored`，否则只是在重复检查原对象，无法证明恢复结果正确。

## 11. 一句话记忆

```text
Path 决定位置，Files 完成读写，Charset 决定字符解释方式。
readAllLines 按行返回 List，size 表示行数。
解析成功返回真实对象，解析失败抛异常，不伪造结果也不返回 null。
电话号码是有格式的标识符，应保存为 String。
保存先收集后一次写入；加载全部解析成功后才返回。
```

## 12. 当前代码自检

1. 为什么第二次 `writeString` 默认覆盖，而不是追加？
2. 空文件和不存在文件的读取结果有什么区别？
3. 为什么 `readAllLines` 的 List 大小等于文件行数？
4. 为什么解析 `"李四,"` 时使用 `split(",", -1)`？
5. 为什么电话号码即使要求纯数字，也仍应保存为 `String`？
6. 为什么 `parseContact` 不应内部捕获异常并返回 `null`？
7. 固定的测试输入与不合理的业务硬编码有什么区别？
8. 为什么 `Files.write` 应放在联系人遍历循环之后？
9. 为什么格式错误时 `load` 不会返回部分 List？
10. 为什么 `IOException` 必须捕获或声明，而 `IllegalArgumentException` 不受此强制？
11. 为什么具体的 `NoSuchFileException` catch 必须写在 `IOException` 前？
12. 为什么对象往返验证比较字段内容，而不是使用 `==`？

对应练习源码：

- `src/main/java/com/ljf/learning/day10/FileWritePractice.java`
- `src/main/java/com/ljf/learning/day10/FileReadPractice.java`
- `src/main/java/com/ljf/learning/day10/MultiLinePractice.java`
- `src/main/java/com/ljf/learning/day10/ContactParsePractice.java`
- `src/main/java/com/ljf/learning/day10/ContactFileStorage.java`
- `src/main/java/com/ljf/learning/day10/ContactFileStorageDemo.java`
