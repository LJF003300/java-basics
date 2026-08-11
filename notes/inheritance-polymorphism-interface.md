# 继承、多态与接口速记

这三个概念可以连成一条主线：

```text
继承或接口建立“可以被统一看待”的关系
                 ↓
子类或实现类提供自己的方法实现
                 ↓
通过父类或接口类型调用时，运行实际对象的实现
```

## 1. 继承：表达“是一种”关系

```java
public class Teacher extends Person {
}
```

这里表示 `Teacher` 是一种 `Person`。因此，要求 `Person` 的地方可以接收 `Teacher` 对象。

继承不只是为了少写几行代码。首先应该确认两种类型确实满足“子类是一种父类”的关系。

### 构造器中的 super

```java
public Teacher(String name, String subject) {
    super(name);
    this.subject = subject;
}
```

- 创建 `Teacher` 时，必须先初始化对象中的父类部分。
- `super(name)` 调用父类构造器，由 `Person` 初始化 `name`。
- 父类的 `private` 字段属于父类内部实现，子类不能直接访问；当前代码通过 `getName()` 读取。

## 2. 方法重写：子类提供自己的版本

父类已有：

```java
public void introduce()
```

子类用相同的方法签名提供自己的实现：

```java
@Override
public void introduce()
```

需要记住：

- 子类不重写时，继承父类版本。
- 子类正确重写后，`Teacher` 对象执行教师版本。
- `@Override` 用于让编译器检查是否真的重写成功。
- `@Override` 不是多态的开关；删除注解但保留正确的方法签名，重写仍然成立。

## 3. 多态：同一种声明类型，对应不同实际行为

最关键的一行：

```java
Person teacherAsPerson = new Teacher("李老师", "数据库");
```

从左右两边理解：

```text
左边 Person          = 声明类型
右边 new Teacher(...) = 实际对象类型
```

核心记忆：

```text
声明类型决定编译时可以调用哪些实例方法。
实际对象类型决定运行时执行哪个重写版本。
```

所以：

```java
teacherAsPerson.introduce();
```

编译时，`Person` 中存在 `introduce()`，因此允许调用；运行时，实际对象是 `Teacher`，因此执行 `Teacher.introduce()`。

但下面不能直接调用：

```java
teacherAsPerson.getSubject();
```

因为声明类型 `Person` 没有 `getSubject()`。即使右边实际创建了 `Teacher`，编译器也只按照左边的声明类型检查可调用方法。

### 方法参数中的多态

```java
public static void showIntroduction(Person person) {
    person.introduce();
}
```

同一个方法既能接收普通 `Person`，也能接收 `Teacher`：

```java
showIntroduction(new Person("张三"));
showIntroduction(new Teacher("王老师", "Java"));
```

方法内部只写一次 `person.introduce()`，运行时会根据实际对象选择正确版本。这就是多态带来的统一调用。

## 4. 接口：表达“具备某种能力”的约定

```java
public interface NotificationSender {
    void send(String message);
}
```

接口在这里规定：任何通知发送器都必须具备 `send` 行为。

实现类作出承诺：

```java
public class ConsoleNotificationSender
        implements NotificationSender {
}

public class EmailNotificationSender
        implements NotificationSender {
}
```

普通实现类遗漏接口要求的方法时，无法通过编译。

### 依赖接口，而不是某个具体实现

```java
public static void sendNotification(
        NotificationSender sender,
        String message
) {
    sender.send(message);
}
```

这个方法不关心通知最终发到控制台还是邮箱，只要求参数具备 `NotificationSender` 约定的能力。

```java
NotificationSender consoleSender =
        new ConsoleNotificationSender();

NotificationSender emailSender =
        new EmailNotificationSender("123@example.com");
```

这也是多态：声明类型相同，实际对象不同，`send()` 的运行结果不同。

## 5. extends 与 implements

| 写法 | 表达的关系 | 当前项目示例 |
| --- | --- | --- |
| `extends` | 子类是一种父类 | `Teacher extends Person` |
| `implements` | 类具备接口规定的能力 | `EmailNotificationSender implements NotificationSender` |

容易混淆的地方：

- 重写父类方法和实现接口方法都可以写 `@Override`。
- 判断术语时要看方法来源：来自父类的是重写父类方法；来自接口的是实现接口约定。
- `@Override` 只帮助编译器检查，不决定运行时多态。

## 6. 一句话记忆

```text
继承：Teacher 是一种 Person。
接口：EmailNotificationSender 能够发送通知。
重写或实现：不同类型提供自己的行为版本。
多态：用统一类型调用，运行实际对象的版本。
```

## 7. 用当前代码自检

1. 为什么 `Person teacherAsPerson = new Teacher(...)` 合法？
2. 为什么 `teacherAsPerson.introduce()` 执行教师版本？
3. 为什么不能直接调用 `teacherAsPerson.getSubject()`？
4. 为什么 `sendNotification()` 不需要分别接收控制台和邮件类型？
5. 删除 `@Override` 后，如果方法签名仍然正确，多态是否还会生效？

对应练习源码：

- `src/main/java/com/ljf/learning/day06/Person.java`
- `src/main/java/com/ljf/learning/day06/Teacher.java`
- `src/main/java/com/ljf/learning/day06/InheritanceDemo.java`
- `src/main/java/com/ljf/learning/day06/NotificationSender.java`
- `src/main/java/com/ljf/learning/day06/NotificationDemo.java`
