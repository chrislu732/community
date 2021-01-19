# Stranger

[English](README.md) / 中文

## 简介

**Stranger** 是一个基于 **Spring Boot** 开发的web应用。它是一个社区论坛的demo。主要的功能包括用户的注册和登录，发帖，回复，实时更新的热门话题排行和消息提醒。数据库使用的是 **h2 database** ，并使用**MyBatis**进行数据库的读写。

## 功能介绍

#### 注册 / 登录

此应用实现了基本的用户管理系统。用户可以通过账号密码或者 **github** 这两种方式进行注册登录。使用token认证机制保持用户的登录状态。

<img src="snapshot/snapshot8.png" width="48%" style="float: left">
<img src="snapshot/snapshot1.png" width="48%" style="float: right">

#### 话题发布

用户可以在此应用轻松地发布话题。话题的主要内容可以使用普通的文本或者**markdown** 语法进行书写，使用的插件为 ***pandao*** 的开源markdown在线编辑器 ***Editor.md***  。用户可以随时对已经发布的话题进行修改和调整。

<img src="snapshot/snapshot5.png" width="48%" style="float: left">
<img src="snapshot/snapshot4.png" width="48%" style="float: right">

#### 回复 / 点赞 / 消息中心

用户可以在任一话题下进行评论或点赞，并且可以在任一评论下进行二级评论及点赞。被评论或者点赞的用户可以通过消息中心查看这些回复和点赞。

<img src="snapshot/snapshot3.png" width="48%" style="float: left">
<img src="snapshot/snapshot7.png" width="48%" style="float: right">

#### 搜索话题 / 相关话题 / 热门话题

用户可以使用导航栏的搜索框对想要了解的话题进行搜索。每一个话题的右边栏都会显示与当前话题相关的话题。此外，在主页和一些其他页面的右边栏会实时显示当前的热门话题排行。此排行使用 **Spring Boot** 的定时任务功能实现。

<img src="snapshot/snapshot2.png" width="48%" style="float: left">
<img src="snapshot/snapshot6.png" width="48%" style="float: right">

## 环境

- **IntelliJ IDEA 2019 +**

- **Java 1.8 +**

- **Maven 3.6 +**

- **Spring Boot 2.4 +**

- **H2 1.4 +**

- **MyBatis 3 +**
