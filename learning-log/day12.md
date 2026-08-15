# Day12 学习日志（JUnit 5 完成）

## 今日完成

- 理解测试代码位于 `src/test/java`，并与正式代码保持相同包路径。
- 使用 `@Test` 标记由 JUnit 自动执行的测试方法。
- 区分 IDEA 单方法、单类运行与 Maven 全项目 `mvn test`。
- 使用 Arrange、Act、Assert 组织测试。
- 使用 `assertTrue`、`assertFalse`、`assertEquals`、`assertNull`、`assertNotNull` 和 `assertThrows`。
- 为 `PrimeChecker`、`StudentManager` 和 `AgeValidationPractice` 编写正常、边界、状态、空值和异常测试。
- 理解测试场景需要隔离目标原因，不能让容量已满掩盖重复学号问题。
- 理解 `assertThrows` 使用 Lambda 延迟执行被测代码，并可返回异常对象继续验证信息。

## 验证证据

- `PrimeCheckerTest` 共 4 个：普通素数 `7`、普通合数 `8`、无效边界 `1`、最小素数 `2`。
- `StudentManagerTest` 共 5 个：添加后数量为 `1`、容量已满拒绝添加、重复学号拒绝添加、存在 ID 查找成功、不存在 ID 返回 `null`。
- `AgeValidationPracticeTest` 共 1 个：年龄 `-1` 抛出 `IllegalArgumentException`，异常信息为“年龄必须在0到120之间”。
- 容量边界测试同时验证第二次添加返回 `false` 和最终数量仍为 `1`，确认失败操作没有修改状态。
- 重复学号测试使用容量 `2`，排除容量已满的干扰因素。
- 查找成功时先执行 `assertNotNull` 再读取姓名，避免空值导致额外的 `NullPointerException`。
- 最终执行 `mvn test`：全项目共 11 个测试，0 失败、0 错误、0 跳过，构建成功；其中今天亲手新增 10 个测试。

## 我现在能独立解释什么

- `@Test` 为什么能让方法被 JUnit 自动发现和执行。
- `assertEquals(expected, actual)` 中，expected 来自业务规则，actual 来自正式代码运行结果。
- 为什么打印结果不能代替断言。
- Failure 是实际值不符合断言，Error 是测试过程中出现未处理异常。
- 为什么边界测试应验证紧邻范围两侧的值，例如 `1` 和 `2`。
- 为什么方法返回失败后，还应验证对象内部状态没有被错误修改。
- 为什么重复学号测试要保留剩余容量，以隔离失败原因。
- 为什么 `assertThrows` 必须接收 Lambda：直接提前调用会让异常在 JUnit 开始检查前抛出。

## 今日纠正的关键问题

- 最初认为每个测试都必须在 PowerShell 中执行 `mvn test`；修正为日常可在 IDEA 中运行单方法或单类，阶段结束时再运行全套 Maven 测试。
- 最初在测试中打印 boolean；删除打印，改为让断言自动判断通过或失败。
- 最初把 actual 简单理解为待测试数据；修正为被测代码执行后产生的实际结果，输入数据属于 Arrange。
- 最初不清楚 `assertFalse` 后为何还要验证数量；明确返回值验证操作报告，数量验证失败操作没有产生副作用。
- 最初未区分空管理器查找与有数据但无目标 ID；增加已有 `S001` 后查找 `S999` 的更强场景。

## 仍需后续学习

- 尚未使用 `@BeforeEach` 抽取重复初始化。
- 尚未学习参数化测试、`assertAll` 和测试替身。
- 当前测试类主要复用前几天的练习代码；后续综合项目应在编写业务逻辑时同步编写测试。

## 下一步

- 开始命令行图书管理系统，先定义最小业务模型与可测试的核心行为，再逐步增加功能。
