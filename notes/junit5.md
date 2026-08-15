# JUnit 5 速记

## 测试文件的位置

正式代码与测试代码分开放置，包路径保持一致：

```text
src/main/java/com/ljf/learning/day03/PrimeChecker.java
src/test/java/com/ljf/learning/day03/PrimeCheckerTest.java
```

- `src/main/java`：正式代码。
- `src/test/java`：测试代码。
- 测试通常使用 `被测类名 + Test` 命名。

## @Test 与运行方式

```java
@Test
void returnsTrueForPrimeNumber() {
    // 测试代码
}
```

`@Test` 告诉 JUnit 自动执行这个方法。测试方法不需要 `main()`，通常返回 `void`。

运行方式：

```text
写完一个测试方法 → 点击 IDEA 方法旁的绿色运行按钮
写完一个测试类   → 点击 IDEA 类旁的绿色运行按钮
阶段结束或提交前 → 使用 mvn test 运行整个项目的测试
```

## Arrange、Act、Assert

一个测试通常分成三个阶段：

```java
int number = 7;                              // Arrange：准备输入
boolean result = PrimeChecker.isPrime(number); // Act：执行正式代码
assertTrue(result);                          // Assert：验证实际结果
```

断言必须检查正式代码产生的实际结果，不能手动制造一个固定结果，否则正式代码写错时测试也可能通过。

## 常用断言

### boolean 结果

```java
assertTrue(result);
assertFalse(result);
```

### 具体值

```java
assertEquals(expected, actual);
assertEquals(1, manager.getCount());
```

- `expected`：根据业务规则确定的预期值。
- `actual`：正式代码执行后产生的实际结果。

### null 结果

```java
assertNotNull(found);
assertEquals("张三", found.getName());

assertNull(missing);
```

先执行 `assertNotNull(found)`，可以在对象缺失时得到明确的断言失败；如果直接调用 `found.getName()`，可能产生 `NullPointerException`，表现为 Error。

## Failure 与 Error

```text
Failure = 正式代码运行到断言，但实际结果不符合预期
Error   = 测试执行过程中出现未处理异常
```

例如，`PrimeChecker.isPrime(8)` 返回 `false`，但使用 `assertTrue(false)` 时属于断言失败。

## 边界测试与场景隔离

素数有效范围的相邻边界：

```text
1 = 紧贴有效范围的无效值
2 = 最小有效值，也是最小素数
```

同时验证 `1` 和 `2`，更容易发现 `<` 与 `<=` 写错。

测试重复学号时应让管理器仍有剩余容量。如果容量已经满，第二次添加即使返回 `false`，也无法判断原因究竟是容量已满还是学号重复。

返回值和状态可以共同验证同一个行为：

```java
assertFalse(secondResult);
assertEquals(1, manager.getCount());
```

前者验证方法报告失败，后者验证失败操作没有错误地修改内部状态。

## 使用 assertThrows 验证异常

```java
IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> AgeValidationPractice.validateAge(-1)
);

assertEquals(
        "年龄必须在0到120之间",
        exception.getMessage()
);
```

`assertThrows` 的两个参数：

```text
IllegalArgumentException.class = 预期的异常类型
() -> validateAge(-1)          = 交给 JUnit 执行和观察的代码
```

### 为什么必须使用 Lambda？

如果在 `assertThrows` 之前直接调用：

```java
AgeValidationPractice.validateAge(-1);
```

异常会在 JUnit 开始检查前直接抛出，`assertThrows` 没有机会执行和捕获它。

Lambda：

```java
() -> AgeValidationPractice.validateAge(-1)
```

会延迟被测代码的执行，把“如何执行”交给 JUnit。JUnit 调用这段代码后，才能判断是否抛出了预期异常。

`assertThrows` 还会返回捕获到的异常对象，因此可以继续验证 `exception.getMessage()`。

## 已验证的练习

- `PrimeCheckerTest`：普通素数、普通合数、无效边界 `1`、最小素数 `2`。
- `StudentManagerTest`：添加后数量、容量已满、重复学号、查找成功、查找不存在。
- `AgeValidationPracticeTest`：年龄小于 `0` 时的异常类型与异常信息。

## 自检问题

1. IDEA 单个测试运行与 `mvn test` 分别适合什么场景？
2. `assertEquals(expected, actual)` 两个参数分别来自哪里？
3. 为什么返回 `false` 后还可能需要验证对象状态？
4. 为什么测试重复学号时必须排除容量已满？
5. 为什么 `assertThrows` 需要用 Lambda 包住被测代码？
