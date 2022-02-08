# library
一个前后端分离的基于vue3的教材订购管理系统。

## 主要技术栈

* 前端：Vue3+Element-plus+axios
* 后端：Springboot+Mybaties-plus+Shiro
## 运行效果
![登录效果图](https://gitee.com/kyleslie/img/raw/master/images/imgs/login.png)  

## 资源目录

`frontend`为前端源代码，如果只看运行效果可以不管。

`backend`为后端源代码，整合了前端编译后的资源。可以直接运行（开发是前后端分离开发，运行时合到了一起）。

## 后端演示运行教程

1. 编辑器内导入`backend`文件夹，自动导入依赖。
2. 本地数据库新建一个数据库，数据库内执行`library.sql`文件。
3. 在配置文件内（路径：`src/main/resources/application.yml`），更改成自己数据库的信息:更改字段包括（`url,username,password`）。
4. 运行项目（执行`LibraryApplication.java`的main方法，或编辑器工具栏执行）。
5. 访问`localhost:8081`即可访问系统。

## 前后端分离教程

如需二次开发，后端如上运行。前端教程如下：

1. 前端编辑器中导入`frontend`文件夹。

2. 在文件夹根目录内，执行命令`npm install`安装依赖包。(根目录右键打开集成终端，执行命令脚本)。

3. 安装完后继续执行命令`npm run serve`,项目即启动成功。

4. 按照提示访问`localhost:8080`即可访问系统。

   > 因为热部署的存在，前端代码只需保存代码就能刷新页面，而无需重启服务。

