# TrackerPro

`TrackerPro` 是一个面向 `Paper 1.21.1` 的 Minecraft 插件，用来为高价值物品记录来源与使用数据，并将结果直接显示在物品 `lore` 中。

## 功能

- 记录战利品来源：结构箱战利品、钓鱼产物、村民交易物品
- 记录装备与工具数据：护甲承伤、方块挖掘、耕地次数、钓鱼次数
- 记录战斗数据：武器击杀怪物数、击杀玩家数
- 使用 `PersistentDataContainer` 保存追踪信息，随物品一起保留

## 运行环境

- Java 21
- Paper 1.21.1

## 构建

```bash
mvn clean package
```

构建产物会输出到 `target/` 目录。

## 安装

1. 将打包后的 JAR 放入服务器的 `plugins/` 目录
2. 启动或重启服务器

