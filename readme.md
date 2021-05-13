# 简介
<center>
	<img src ="https://img-blog.csdnimg.cn/20210421022916813.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70" width="800"/>
</center>
<br/>
<center>
	<img src="https://img-blog.csdnimg.cn/20210514005409814.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center" width="800"/>
</center>
<br/>
<br/>

<center>
	<img src="https://img-blog.csdnimg.cn/20210514005409827.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center" width="800"/>
</center>

<hr/>

<font face="楷体" size="+1" color="#000033">本文基于**SpringBoot+MyBatisPlus+Redis+Thymeleaf+Echarts**实现一个简单的学生成绩管理系统。它在上一[Spring+SpringMVC+Mybatis版本]((https://blog.csdn.net/qq_36937684/article/details/115924220))基础上使用了SpringBoot框架，优化了相关功能，增加了Echarts可视化功能。


# 任务
<center><img src="https://img-blog.csdnimg.cn/20200923171232277.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center"/></center>
<br/>

# 相关工作

 - [MySQL+java:   实现学生成绩管理系统（1.0版本）](https://blog.csdn.net/qq_36937684/article/details/108757156)
- [整合Spring+Mybatis    学生成绩管理系统（完整代码）](https://blog.csdn.net/qq_36937684/article/details/113622364)
- [JavaWeb+MySQL实现学生成绩管理系统（1.0版本完整代码）](https://blog.csdn.net/qq_36937684/article/details/114846331?spm=1001.2014.3001.5501)   
- [SSM 实现学生成绩管理系统（完整代码）](https://blog.csdn.net/qq_36937684/article/details/115924220)

<font face="楷体" size="+1" color="#000033">本项目基于以上[项目](https://github.com/AsajuHuishi/StudentScoreManagementSystem_SSM)进行改进。主要内容有：
- 使用SpringBoot一站式框架实现，代替Spring+SpringMVC+Mybatis多个框架，减少配置文件数量；
- 使用SpringBoot的自定义错误页面、使用拦截器判断用户是否登录、使用注解式事务管理；
- 前端使用HTML+Thymeleaf模板引擎，不再使用jsp；
- 在注册页面使用Kaptcha验证码；
- 使用Redis实现用户注册、登录功能；
- 使用MyBatisPlus实现分页模型显示所有学生信息；
- 使用Echarts对统计结果实现可视化；
- 使用Slf4j实现日志功能。

<b>更新日志（已经在github上更新）</b>
- 暂无

# 项目结构
<font face="楷体" size="+1" color="#000033">这是一个maven工程。

```bash
├─main
│  ├─java
│  │  └─indi
│  │      └─huishi
│  │          └─shizuo
│  │              ├─config		配置类，包括WebMvcConfig MybatisPlusConfig
│  │              ├─controller	表示层
│  │              ├─dao			数据访问层
│  │              │  └─impl
│  │              ├─interceptor	拦截器
│  │              ├─pojo		实体类
│  │              ├─service		业务逻辑层
│  │              │  └─impl
│  │              └─util		工具包
│  └─resources
│      ├─static					静态资源
│      │  ├─css
│      │  ├─img
│      │  └─script				jquery echarts
│      └─templates				HTML页面
│          ├─common	
│          ├─error
│          ├─menu
│          ├─test
│          ├─useless
│          └─user
└─test
    └─java
        └─indi
            └─huishi
                └─shizuo
                    ├─controller
                    ├─service
                    └─util

```
<hr/>

# 数据库

<font face="楷体" size="+1" color="#000033">1.使用MySQL实现，和上一版本一致。

```sql
USE student_score_ssm;

CREATE TABLE student_score(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NO VARCHAR(10) UNIQUE NOT NULL,
	NAME VARCHAR(20) NOT NULL,
	score FLOAT(20),
	class_name INT
);


CREATE TABLE USER(
	id INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(20) UNIQUE,
	PASSWORD VARCHAR(20) NOT NULL,
	email VARCHAR(20)
);

```
<font face="楷体" size="+1" color="#000033">2.Redis实现用户注册和登录

- <font face="楷体" size="+1" color="#000033"> set：保存用户名，确保用户名唯一
```bash
169.254.0.1:6379> SMEMBERS uname
1) "Linwenti"
2) "huishi"
169.254.0.1:6379>
```
- <font face="楷体" size="+1" color="#000033"> key-value: 保存用户名-密码

```bash
169.254.0.1:6379> get huishi
"123456"
169.254.0.1:6379> get Linwenti
"123456"
```

# 结果页面
## 主页
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210421021004637.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center)
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021042102104968.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center)

## 查询
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210421021214493.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210421021257903.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center)
<font face="楷体" size="+1" color="#000033">查询异常
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210421021228421.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center)
## 增加
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210421021731287.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center)

## 修改
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021042102170056.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center)
## 删除
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210421021712884.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center)


## 统计
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210428015715371.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center)

## 登录
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210421170512593.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center)

##  注册

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210514012258769.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTM3Njg0,size_16,color_FFFFFF,t_70#pic_center)

