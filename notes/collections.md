# Java 集合速记

## Map 的三种视图

最容易记忆的总结：

```text
keySet()   = 把键拿出来组成 Set
entrySet() = 把键值对拿出来组成 Set
values()   = 把值拿出来组成 Collection
```

对应的返回类型：

```java
Set<K> keySet()
Set<Map.Entry<K, V>> entrySet()
Collection<V> values()
```

### 为什么 keySet() 返回 Set？

Map 的 key 不能重复。相同 key 再次调用 `put` 时，只会替换原来的 value，不会新增键值对。

### 为什么 entrySet() 返回 Set？

`Map.Entry<K, V>` 表示一组完整的键值对。Map 中每个 key 只能对应一个当前 value，因此完整条目也不会出现重复 key。

遍历时：

```java
for (Map.Entry<String, String> entry : contacts.entrySet()) {
    System.out.println(entry.getKey() + "：" + entry.getValue());
}
```

- `entry.getKey()` 取得当前 key。
- `entry.getValue()` 取得当前 value。
- 同时需要 key 和 value 时，优先使用 `entrySet()`。

### 为什么 values() 只返回 Collection？

Map 的 value 可以重复，因此不能用要求元素唯一的 Set 表示。

例如：

```text
张三 -> 101
李四 -> 101
```

两个不同的 key 可以对应相同的 value。

## 当前记忆结论

```text
Map：key 唯一，value 可以重复。
Set：元素不可重复。
List：有顺序，元素可以重复。
```

`HashMap` 不保证遍历顺序，因此验证结果时应检查键和值的对应关系，不依赖输出先后顺序。
