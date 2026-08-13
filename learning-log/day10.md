# Day10 学习日志（文件与 IO 完成）

## 今日完成

- 使用 `Path` 表示相对文件路径，并打印实际绝对路径。
- 使用 UTF-8 和 `Files.writeString`、`Files.readString` 完成单段文本写入与读取。
- 验证 `writeString` 默认覆盖文件，而不是追加内容。
- 使用 `Files.write`、`Files.readAllLines` 完成多行联系人文本写入与读取。
- 使用 `split(",", -1)` 将联系人行解析为 `Contact`，并将 `Contact` 格式化为文本。
- 完成联系人对象到文本再恢复为对象的往返验证。
- 创建 `ContactFileStorage`，实现 `List<Contact>` 的保存与加载。
- 学习 `throws IOException`、checked exception、unchecked exception 和异常捕获顺序。

## 验证证据

- `FileWritePractice` 连续运行两次后，文件仍只有一行，UTF-8 内容精确匹配“Java 文件写入练习”。
- 正常文件读取成功；不存在的 `missing.txt` 进入 `IOException` 分支，之后程序继续运行。
- 多行联系人文件读取出三行，`loadedLines.size()` 为 `3`，三行 UTF-8 内容全部匹配。
- `"张三,111"` 恢复为真实联系人；`"错误行"` 和 `"李四,"` 均抛出 `IllegalArgumentException`，没有返回 `null` 或触发额外空指针异常。
- 赵六联系人往返后姓名和手机号一致，输出 `数据一致：true`。
- `ContactFileStorage` 保存并加载张三、李四、王五成功，加载数量为 `3`，首条数据一致。
- 格式错误文件在第二行抛出异常，调用者没有收到包含第一行的部分 List。
- 空联系人 List 保存后生成 0 字节文件，加载结果数量为 `0`。
- 不存在的联系人文件被精确捕获为 `NoSuchFileException`。
- 最终执行 `mvn test`：59 个主源文件编译成功，1 个现有测试通过，0 失败、0 错误。

## 我现在能独立解释什么

- `Path` 负责描述位置，`Files` 负责文件操作，`StandardCharsets.UTF_8` 规定字符解释方式。
- 存在的空文件读取结果是空字符串或空 List；不存在文件会抛出异常。
- `readAllLines` 返回一行对应一个元素的 `List<String>`，所以 `size()` 表示行数。
- `split(",", -1)` 保留末尾空字段，可以识别空手机号；英文逗号和中文逗号不是同一字符。
- 电话号码是带格式的标识符，应保存为 `String`；纯数字要求属于字符串格式校验，不意味着转换为数值类型。
- 解析方法应“返回有效对象或抛出异常”，不应内部打印后返回 `null`。
- 固定的正常和错误字符串可以作为测试样例；业务逻辑应依据通用格式规则判断，而不能只识别某个固定错误值。
- `Files.write` 放在循环后可以一次写入全部联系人，并能正确处理空 List。
- `load` 中任意一行解析失败都会异常结束，`return` 不执行，因此不会返回部分结果。
- `IOException` 是 checked exception，必须捕获或声明；`IllegalArgumentException` 是 unchecked exception，编译器不强制处理。
- 捕获异常时先写具体子类，再写宽泛父类，否则子类分支不可达。
- 两个对象字段相同不表示是同一个对象；`==` 比较引用，内容一致应比较字段。

## 今日纠正的关键问题

- 最初认为使用空 List 保存会保留旧文件；实际 `Files.write` 默认覆盖，空 lines 会把文件清空。
- 最初把 `Files.write` 放在联系人循环内，导致重复覆盖写入；改为收集完所有行后一次写入。
- 最初对同一文件调用两次 `readAllLines`；改为保存一次读取结果后遍历。
- 最初格式错误时只打印并继续创建联系人；改为直接抛出 `IllegalArgumentException`。
- 最初解析方法自己捕获异常并返回 `null`；改为解析方法抛出、调用者捕获。
- 最初空列表验证打印原输入 List 的数量；改为检查 `load` 返回的 List。
- 最初往返输出读取原对象字段；改为读取恢复对象字段。
- 最初认为引用两边为 null 时不能使用 `==`；实际 `null == null` 为 true，问题在于 `==` 不比较对象字段内容。
- 最初认为 `throws IOException` 主要因为它是更大的父类；实际原因是 checked exception 的编译期强制规则。

## 仍需后续学习

- 当前 CSV 式格式没有处理姓名包含逗号、引号或换行的情况；后续真实项目应使用可靠格式或专门解析库。
- 当前联系人字段只校验数量与非空，尚未制定手机号字符、长度等业务规则。
- 尚未学习流式 Reader/Writer、缓冲流、二进制 IO 和 try-with-resources 的完整使用场景。
- `data/` 中的运行产物可以重新生成，已加入 `.gitignore`，不作为源码提交。

## 下一步

- 开始 Lambda、Stream 和 Optional，使用联系人集合完成筛选、排序和分组统计。
