# `Ant Server`

`Ant Server`是 `ABTest`/定投/灰度的后台服务应用。

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->


- [核心概念](#%E6%A0%B8%E5%BF%83%E6%A6%82%E5%BF%B5)
- [`Maven`模块依赖关系图](#maven%E6%A8%A1%E5%9D%97%E4%BE%9D%E8%B5%96%E5%85%B3%E7%B3%BB%E5%9B%BE)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# 核心概念

## 面向业务的概念

支持三种使用方式：

- `ABTest`/实验（`experiment`/`expr`）  
    这是大家说到数据驱动/`Growth Hacking`的使用方式中提的最多的，感知度最高的使用方式。  
    重点关注的是 AB桶对比，即ABTest/实验。  
    会包含多个 实验组(`experiment group`)。
- 定投（`delivery`/`dy`）  
    指定人群生效一个功能。  
    重点关注的是 生效条件，比如人群（即定投到指定人群）。
- 灰度（`gray`)  
    灰度生效一个功能。  
    重点关注的是 在一个时间段内慢慢提升生效的设备比例。

## 面向技术的概念

`Ant`是上面三种业务使用方在设备命中流程上的统一抽象，即`Allocation And Targeting`。

对应的是 实验组（注意不是实验）、定投、灰度。

# `Maven`模块依赖关系图

![](docs/maven-module.jpg)
