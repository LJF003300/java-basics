# Day11 学习日志（Lambda、Stream、Optional 完成）

## 今日完成

- 使用 Lambda 为 `forEach` 提供处理行为，并理解参数类型由集合元素类型推断。
- 使用 Stream 的 `filter`、`map`、`sorted`、`toList` 完成筛选、转换、排序和结果收集。
- 使用 `Comparator<Contact>` 明确联系人按姓名排序的规则。
- 使用 `Collectors.groupingBy` 按单词长度分组。
- 使用 `Collectors.counting` 完成分组计数。
- 使用 `findFirst`、`Optional`、`orElse` 处理可能不存在的结果。
- 使用 `orElseThrow` 将不存在的联系人转换为明确异常，并由调用处捕获。

## 验证证据

- Lambda 正常输出 3 个联系人；空联系人 List 执行 `forEach` 无输出、无异常。
- 手机号按 `"1"` 筛选得到张三、李四；按 `"9"` 筛选无结果；原 List 大小保持为 `4`。
- 空手机号使用 `startsWith` 时安全跳过，没有触发 `charAt(0)` 越界。
- `map` 能把 `Contact` 转换为姓名 `String`，也能把手机号转换为长度 `Integer`；空手机号长度为 `0`。
- `filter + map + toList` 得到姓名列表 `[张三, 李四]`，无匹配列表为 `[]`，原列表大小不变。
- 数字排序结果为 `[70, 70, 85, 90]`，原顺序保持不变；空数字 List 排序结果大小为 `0`。
- 联系人按姓名排序结果为 `[Alice, Bob, Charlie]`；空联系人 List 使用同一 Comparator 后结果大小为 `0`。
- 单词长度为 `4` 的分组为 `[Java, List]`，大小为 `2`；空分组 Map 大小为 `0`。
- 单词长度 `3、4、6` 的计数分别为 `2、2、1`；空统计 Map 大小为 `0`。
- Optional 找到 `Java`；无匹配和空 List 均通过 `orElse` 得到“未找到”。
- 联系人查询中手机号 `222` 返回完整 `Contact` 并输出李四；手机号 `999` 通过 `orElseThrow` 抛出异常，捕获后程序继续。
- 最终执行 `mvn test`：1 个测试通过，0 失败，0 错误，构建成功。

## 我现在能独立解释什么

- Lambda 参数为什么不必重复声明具体类型。
- Stream 是处理流水线，不是保存最终结果的 List；`toList()` 返回的新 List 需要由变量接收。
- `filter` 决定元素是否保留，`map` 决定元素转换成什么类型，操作顺序会影响后续还能使用哪些属性。
- Stream 操作不会自动改变源集合结构和顺序，但 Lambda 主动调用 setter 仍会修改原对象。
- `Integer` 有自然顺序；`Contact` 有多个可比较属性，需要 Comparator 指定比较规则。
- `groupingBy` 的 key 是分组依据，一个 key 可对应多个元素；`counting()` 将组内元素转换为 `Long` 数量。
- `findFirst()` 返回 Optional 是因为查询可能没有结果；`orElse` 返回默认值，`orElseThrow` 在空时抛出异常。

## 今日纠正的关键问题

- 最初把 Stream 简单理解为复制出的新集合；修正为读取源集合元素的处理流水线，元素对象仍可能是原引用。
- 最初使用 `charAt(0)` 判断手机号开头；修正为可以安全处理空字符串的 `startsWith`。
- 最初先把 Contact 映射为手机号再筛选，导致最终收集的是手机号；修正为先按 Contact 的手机号筛选，再映射为姓名。
- 最初遗漏 `toList()` 返回值保存位置；明确右侧返回新 List，左侧变量接收后才能调用 `size`、`get` 等方法。
- 最初使用 `.map(Contact::getName)` 后再查找，得到的是 `Optional<String>`；删除 map 后得到 `Optional<Contact>` 和完整联系人。
- 修正了 Day11 练习类名中的 `Prictice`、`Steam` 等拼写问题。

## 仍需后续学习

- 尚未系统学习 Stream 的 `distinct`、`limit`、`skip`、`reduce` 和并行流。
- 尚未比较 `orElse` 与 `orElseGet` 的求值时机。
- 尚未把 Lambda、Stream 和 Optional 写成 JUnit 5 自动化测试。

## 下一步

- 开始 JUnit 5，先理解测试方法、断言以及正常场景与边界场景的自动化验证。
