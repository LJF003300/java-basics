# 异常、枚举与泛型速记

## 1. 异常：把失败从正常结果中分离

最容易记忆的流程：

```text
try   = 尝试执行可能出错的代码
throw = 发现问题后主动报告失败
catch = 捕获匹配的异常并决定如何处理
```

发生异常后，当前 `try` 中尚未执行的语句会被跳过，控制流进入第一个匹配的 `catch`。`catch` 完成后，程序可以继续执行其后的代码。

### catch 参数是什么

```java
catch (NumberFormatException exception) {
    System.out.println(exception.getMessage());
}
```

- `NumberFormatException` 是要捕获的异常类型。
- `exception` 是引用变量，指向本次捕获到的异常对象。
- 把变量名改成 `e` 不会改变异常类型或程序行为。

### 精确捕获

已知可能出现的异常时，应捕获具体类型：

```java
catch (IllegalArgumentException exception)
```

不要习惯性写：

```java
catch (Exception exception)
```

后者可能把无关的 `NullPointerException` 等错误也误当作当前业务问题处理。

## 2. throw：校验方法报告失败

```java
static void validateAge(int age) {
    if (age < 0 || age > 120) {
        throw new IllegalArgumentException(
                "年龄必须在0到120之间"
        );
    }
}
```

职责划分：

```text
校验方法：发现问题并 throw
调用者：使用 try-catch 决定如何处理
```

不要在同一个小方法中刚抛出异常又立即捕获，否则异常没有传达给调用者。

## 3. 自定义业务异常

标准结构：

```java
public class DuplicatePhoneException
        extends RuntimeException {

    public DuplicatePhoneException(String message) {
        super(message);
    }
}
```

- `extends RuntimeException` 表示它是一种运行时异常。
- `super(message)` 把错误信息交给异常父类保存。
- 异常类只描述并携带问题，不直接打印。

抛出完整业务信息：

```java
throw new DuplicatePhoneException(
        "手机号已存在：" + phone
);
```

由调用者处理：

```java
catch (DuplicatePhoneException exception) {
    System.out.println(exception.getMessage());
}
```

当前业务异常：

```text
DuplicatePhoneException  = 手机号重复
ContactNotFoundException = 联系人不存在
```

### 写入前先校验

联系人同时保存在 List、Set 和 Map 中。必须先完成重复手机号校验，再写入三个集合：

```text
先校验成功 → 同步写入 List、Set、Map
校验失败   → 直接抛出异常，三个集合都不改变
```

如果先写入 List 再发现失败，会导致：

- List 新增重复联系人；
- Set 拒绝重复手机号；
- Map 用新联系人覆盖旧联系人；
- 三个集合不再表示同一份数据。

## 4. 枚举：限制固定的合法值

```java
public enum ContactStatus {
    ACTIVE,
    DISABLED,
    PENDING
}
```

使用枚举后，状态不能再被任意字符串或错别字污染。

```java
ContactStatus status = ContactStatus.ACTIVE;
```

枚举值通常直接使用 `==` 比较：

```java
status == ContactStatus.ACTIVE
```

### switch 处理多个状态

```java
switch (status) {
    case ACTIVE:
        System.out.println("联系人可以正常使用");
        break;
    case DISABLED:
        System.out.println("联系人已停用");
        break;
    case PENDING:
        System.out.println("联系人等待验证");
        break;
}
```

`case` 中直接写 `ACTIVE`，不需要重复写 `ContactStatus.ACTIVE`。

### 字符串转换为枚举

```java
ContactStatus.valueOf(input.toUpperCase())
```

- `valueOf` 按枚举常量名称精确匹配。
- 枚举名称区分大小写。
- 非法名称会抛出 `IllegalArgumentException`。
- `toUpperCase()` 可以让 `active` 匹配 `ACTIVE`，但不能让 `unknown` 变成合法状态。

## 5. 泛型：在编译期限制类型

```java
Map<String, Contact> contactsByPhone;
```

- 第一个泛型参数限制 key 为 `String`。
- 第二个泛型参数限制 value 为 `Contact`。

如果写入 `Integer` key 或 `String` value，属于编译错误，不是运行时异常。

```text
泛型类型不匹配      = 编译期错误
IllegalArgumentException = 运行时异常
NullPointerException     = 运行时异常
```

### 自定义泛型类

```java
public class Result<T> {
    private T data;

    public Result(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
```

`T` 是类型占位符，创建对象时才确定具体类型：

```java
Result<String> textResult = new Result<>("成功");
Result<Contact> contactResult =
        new Result<>(new Contact("李四", "222"));
```

编译器分别按照 `String` 和 `Contact` 检查输入与返回值，因此取得数据时不需要强制类型转换。

## 6. 一句话记忆

```text
异常：让失败沿调用关系明确传递，而不是伪装成正常结果。
枚举：把任意字符串收紧为有限、合法、可检查的状态。
泛型：把类型错误提前到编译期发现。
```

## 7. 当前代码自检

1. 为什么 `throw` 后面同一代码路径的语句不会继续运行？
2. 为什么异常构造器不应直接 `System.out.println`？
3. 为什么应捕获 `DuplicatePhoneException`，而不是宽泛的 `Exception`？
4. 为什么 `valueOf("unknown")` 会抛出异常？
5. 为什么 `Result<Contact>` 不能接收字符串？
6. 为什么联系人写入三个集合之前必须先完成重复手机号校验？

对应练习源码位于：

- `src/main/java/com/ljf/learning/day09/NumberInputPractice.java`
- `src/main/java/com/ljf/learning/day09/AgeValidationPractice.java`
- `src/main/java/com/ljf/learning/day09/CustomExceptionPractice.java`
- `src/main/java/com/ljf/learning/day09/EnumPractice.java`
- `src/main/java/com/ljf/learning/day09/GenericPractice.java`
- `src/main/java/com/ljf/learning/day09/ContactService.java`
