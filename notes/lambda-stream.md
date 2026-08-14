# Lambda 与 Stream 速记

## Lambda 参数的类型推断

```java
contacts.forEach(contact -> System.out.println(contact.getName()));
```

当 `contacts` 是 `List<Contact>` 时，`forEach` 依次传入其中的元素，因此编译器可以推断 Lambda 参数 `contact` 是 `Contact`。

## Stream 是处理流水线

```text
List<Contact>
    ↓ stream()
Stream<Contact>
    ↓ filter(...)
Stream<Contact>
    ↓ map(contact -> contact.getName())
Stream<String>
    ↓ toList()
List<String>
```

- `stream()`：从集合建立处理流水线，不是最终结果列表。
- `filter()`：条件为 `true` 的元素继续向后流动，不删除原 List 中的元素。
- `map()`：把每个元素转换成另一种内容，转换后 Stream 的元素类型可能改变。
- `sorted()`：改变 Stream 中元素经过的顺序，不改变原 List 的顺序。
- `toList()`：把 Stream 的结果收集为一个新的 List，返回后要用变量接收才能继续调用。

例如，先按手机号筛选联系人，再提取姓名：

```java
List<String> names = contacts.stream()
        .filter(contact -> contact.getPhone().startsWith("1"))
        .map(contact -> contact.getName())
        .toList();

System.out.println(names);
System.out.println(names.size());
```

操作顺序很重要。如果先把 `Contact` 映射成手机号 `String`，后面就无法再从这个 String 中取得联系人姓名。

## Stream 不改集合结构，但对象仍可能被修改

`filter`、`map`、`sorted` 和 `toList` 本身不会自动增删原集合，也不会改变原集合的元素顺序。

但是 Stream 中流动的对象仍可能是原对象的引用。Lambda 如果主动调用 setter，原对象仍会改变：

```java
contacts.stream()
        .forEach(contact -> contact.setName("新名字"));
```

因此应记住：

```text
Stream 操作不会自动修改源集合；
Lambda 中仍然可以主动修改元素对象，通常不建议这样做。
```

## sorted 与 Comparator

`Integer` 已定义数字从小到大的自然顺序，因此可以直接调用：

```java
List<Integer> sortedNumbers = numbers.stream()
        .sorted()
        .toList();
```

`Contact` 有姓名、手机号等多个属性，Java 不知道应该按哪个属性排序，需要提供 `Comparator<Contact>`：

```java
Comparator<Contact> byName =
        Comparator.comparing(contact -> contact.getName());

List<String> sortedNames = contacts.stream()
        .sorted(byName)
        .map(contact -> contact.getName())
        .toList();
```

```text
Comparator<Contact> = 比较 Contact 的规则
contact.getName()    = 本次按姓名比较
```

`Comparator` 的使用可以分成两步记忆：

```java
// 第一步：定义比较规则
Comparator<Contact> byName =
        Comparator.comparing(contact -> contact.getName());

// 第二步：把规则传给 sorted
List<Contact> sortedContacts = contacts.stream()
        .sorted(byName)
        .toList();
```

自然顺序明确的 `Integer` 可以直接使用 `sorted()`；具有多个可比较属性的 `Contact` 应通过 Comparator 明确本次按哪个属性比较。

## Collectors 分组与统计

`Collectors` 是工具类，常与 Stream 的终止操作 `collect()` 配合，把流水线结果收集成 List、Map 或统计结果。类名是复数形式 `Collectors`。

### 按规则分组

```java
Map<Integer, List<String>> wordsByLength = words.stream()
        .collect(Collectors.groupingBy(word -> word.length()));
```

类型含义：

```text
Integer      = 分组键，这里是单词长度
List<String> = 拥有该长度的所有单词
```

一个分组键可能对应多个单词，因此 value 是 `List<String>`，不能只用一个 `String`。

### 分组后计数

```java
Map<Integer, Long> countByLength = words.stream()
        .collect(Collectors.groupingBy(
                word -> word.length(),
                Collectors.counting()
        ));
```

两个参数分别表示：

```text
word -> word.length() = 根据什么产生分组键
Collectors.counting() = 每个分组如何处理，这里只统计数量
```

使用 `counting()` 后，value 不再保存组内全部单词，而是保存该组数量。其结果类型是 `Long` 包装类型，因此 Map 应写成 `Map<Integer, Long>`；泛型中不能写基本类型 `long`。

### toList 与 collect(groupingBy) 的区别

```text
toList()                         = 全部结果放进一个 List
collect(Collectors.groupingBy()) = 按 key 分组并生成 Map
```

## Optional 表示可能不存在的结果

`findFirst()` 可能找到元素，也可能没有结果，因此返回 `Optional<T>`，而不是直接返回 `T`。

```java
Optional<String> result = names.stream()
        .filter(name -> name.startsWith("J"))
        .findFirst();

String name = result.orElse("未找到");
```

类型变化：

```text
Stream<String>
    ↓ findFirst()
Optional<String>
    ↓ orElse("未找到")
String
```

- `orElse(defaultValue)`：有值时返回内部值，空时返回默认值。
- `orElseThrow(exceptionSupplier)`：有值时返回内部值，空时创建并抛出异常。

查询完整联系人对象：

```java
Contact contact = contacts.stream()
        .filter(item -> item.getPhone().equals("222"))
        .findFirst()
        .orElseThrow(
                () -> new IllegalArgumentException("手机号不存在：222")
        );
```

```text
Stream<Contact>
→ Optional<Contact>
→ Contact，或者抛出异常
```

如果在 `findFirst()` 前先执行 `.map(Contact::getName)`，类型会变成 `Optional<String>`，最终只能得到姓名，不能得到完整 `Contact`。

`Contact::getName` 是方法引用，等价于：

```java
contact -> contact.getName()
```

## 已验证的边界与易错点

- 空 List 执行 `forEach` 或自然排序不会报错；筛选无匹配时，`map + toList` 得到空 List。
- 使用 `phone.charAt(0)` 检查开头时，空字符串会越界；`phone.startsWith("1")` 可以安全处理空字符串。
- `filter` 后原 List 大小不变。
- `sorted` 会保留重复元素，且原 List 顺序不变。
- 变量名不会决定 Stream 中的内容；真正决定元素类型和内容的是 `map` 的返回值。
- `findFirst()` 找到、无匹配和空 List 三种场景均可用 Optional 明确处理。
- `orElseThrow()` 有值时返回目标对象；空时赋值不发生，控制流转去抛出异常。

## 对应练习

- `day11/LambdaPractice.java`
- `day11/StreamFilterPractice.java`
- `day11/StreamMapPractice.java`
- `day11/StreamCollectPractice.java`
- `day11/StreamSortPractice.java`
- `day11/ContactSortPractice.java`
- `day11/StreamGroupingPractice.java`
- `day11/StreamCountingPractice.java`
- `day11/OptionalPractice.java`
- `day11/ContactOptionalPractice.java`

## 自检问题

1. `filter()` 和 `map()` 分别改变什么？
2. `.map(contact -> contact.getName())` 后，Stream 中的元素是什么类型？
3. `toList()` 返回的新列表保存在哪里，之后如何调用？
4. 为什么 `Contact` 排序时需要 `Comparator<Contact>`？
5. 为什么 Stream 没有改变原 List，却仍可能修改其中的 Contact 对象？
6. `groupingBy()` 为什么通常得到 `Map<K, List<T>>`？
7. 使用 `Collectors.counting()` 后，为什么 Map 的 value 是 `Long`？
8. 为什么 `findFirst()` 返回 `Optional<T>` 而不是直接返回 `T`？
9. `orElse()` 和 `orElseThrow()` 在无值时分别做什么？
