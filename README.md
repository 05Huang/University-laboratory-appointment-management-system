# laboratory

### 介绍

一个简单的实验室管理系统，可用于毕设，入门教学。

没有用很多新技术，加之业务逻辑简单，所以拓展和修改都很方便。

> 轻喷，本人菜鸟，项目还在完善中。本来打算直接使用开源项目 [若依](http://www.ruoyi.vip/) 或者 [eladmin](https://gitee.com/elunez/eladmin) 修改的，但是为了素材没那么复杂，就自己写了个超简单的实验室管理系统。

#### 目录结构：

![image-20210313003831699](https://wts-blog.oss-cn-beijing.aliyuncs.com/typora/202103/13/003832-574587.png)

#### commons

一些工具和配置。

#### page

管理前端页面。

#### system

主要业务逻辑都在这里了。



###  软件架构

**SpringBoot + JPA + MySQL + Thymeleaf**

其中前端模板特别感谢：[笔下光年/Light Year Admin Using Iframe](https://gitee.com/yinqi/Light-Year-Admin-Using-Iframe)

### 账号

管理员：2017250082 

老师：2017250083 

学生：2017250084 

密码都是 123456

### 运行教程

> 使用IDEA打开项目，因为引入了Lombok， 所以需要下载Lombok插件

1. 导入SQL
2. 启动项目
3. 浏览器输入地址，**127.0.0.1:8080/index**

### 项目截图

**登录页**

![image-20210313005852340](https://wts-blog.oss-cn-beijing.aliyuncs.com/typora/202102/21/image-20210313005232992.png)



**管理员**

![image-20210313005339962](https://wts-blog.oss-cn-beijing.aliyuncs.com/typora/202103/13/005341-813709.png)

![image-20210313005409762](https://wts-blog.oss-cn-beijing.aliyuncs.com/typora/202103/13/005410-707592.png)

**老师**

![image-20210313005454692](https://wts-blog.oss-cn-beijing.aliyuncs.com/typora/202103/13/005456-825782.png)

![image-20210313005518988](https://wts-blog.oss-cn-beijing.aliyuncs.com/typora/202103/13/005520-650254.png)

**学生**

![image-20210313005556656](https://wts-blog.oss-cn-beijing.aliyuncs.com/typora/202103/13/005605-174489.png)

![image-20210313005606834](https://wts-blog.oss-cn-beijing.aliyuncs.com/typora/202103/13/005607-811252.png)

### 特别感谢

**EL-ADMIN 后台管理系统**  https://gitee.com/elunez/eladmin

**若依** http://www.ruoyi.vip/

**Light Year Admin Using Iframe 光年后台模板的iframe版本** https://gitee.com/yinqi/Light-Year-Admin-Using-Iframe

 