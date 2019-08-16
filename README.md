# Spring-Boot-SpringMVC-MyBaits
Spring Boot+SpringMVC+MyBaits实现数据库的基本操作
## 创建数据库
在Navicat中的deptmanage数据库新建dept表设计如下,并插入相关的部门信息，其中id属性选择为自增：
![](https://raw.githubusercontent.com/butalways1121/img-Blog/master/19.png)
![](https://raw.githubusercontent.com/butalways1121/img-Blog/master/20.png)
<!-- more -->
## 创建项目
### 新建Maven项目
Eclipse中创建Maven Project，输入相应的包名及项目名，这里的项目名是DeptManage，包名为com,接着使用如下代码替换pom.xml里面的内容:
```
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>1.0.0</groupId>
	<artifactId>dms-get-ecall</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>springboot-restful</name>
	<url>http://maven.apache.org</url>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<java.version>1.7</java.version>
		<mybatis-spring-boot>1.2.0</mybatis-spring-boot>
	</properties>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.5.9.RELEASE</version>
		<relativePath />
	</parent>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<optional>true</optional>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<!-- Spring Boot Mybatis 依赖 -->
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>${mybatis-spring-boot}</version>
		</dependency>
		<!-- MySQL 连接驱动依赖 -->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
		</dependency>
	</dependencies>
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<fork>true</fork>
				</configuration>
			</plugin>
		</plugins>
		<resources>
            <resource>
                <directory>src/main/java</directory>
                <!-- 此配置不可缺，否则mybatis的Mapper.xml将会丢失 -->
                <includes>
                    <include>**/*.xml</include>
                </includes>
            </resource>
            <!--指定资源的位置-->
            <resource>
                <directory>src/main/resources</directory>
            </resource>
        </resources>
	</build>
	
</project>
```
在src/main下新建resources文件夹，新建application.properties文件，将如下内容复制：
```
banner.charset=UTF-8
server.tomcat.uri-encoding=UTF-8
spring.http.encoding.charset=UTF-8
spring.http.encoding.enabled=true
spring.http.encoding.force=true
spring.messages.encoding=UTF-8

server.port=8083
spring.datasource.url=jdbc:mysql://数据库地址:3306/DeptManage?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=数据库名
spring.datasource.password=数据库密码
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

mybatis.typeAliasesPackage=com.DeptManage
mybatis.mapperLocations=classpath\:mapper/*.xml
```
将com.DeptManage下的App.java代码更新如下：
```
package com.DeptManage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
        System.out.println( "Hello World!" );
    }
}
```
完成之后右键项目，选择Maven/Update Projects更新项目。
## 创建项目
### entity层
在src/main/java下新建entity包，创建Dept实体类，该实体类封装的变量对应dept数据库表中的属性，代码如下:
```
package com.DeptManage.entity;

public class Dept {
	private int id;
	private String name;
	private String address;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
```
### dao层
在src/main/java下新建dao包,新建DeptDao接口，声明对数据库的增删改查方法：
```
package com.DeptManage.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.DeptManage.entity.Dept;

@Mapper
public interface DeptDao {
	List<Dept> findAll(); //查询所有数据
	Dept findByName(String name);//根据部门名称查询数据
	void update(Dept dept);//修改部门信息
	void add(Dept dept);//增加部门信息
	void delete(int id);//删除部门信息
}

```
在src/main/resources下创建mapper文件夹，在创建DeptMapper.xml文件，在其中添加对数据库的具体操作：
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.DeptManage.dao.DeptDao">
	<!-- 查询所有数据 -->
	<select id="findAll" resultType="Dept" >
		select * from dept;
	</select>
	<!-- 根据部门名称查询数据 -->
	<select id="findByName" resultType="Dept" parameterType="String">
		select id,name,address from dept where name=#{name};
	</select>
	<!-- 修改部门信息 -->
	<select id="update" resultType="Dept" parameterType="Dept">
		update dept set name=#{name},address=#{address} where id=#{id};
	</select>
	<!-- 增加部门信息 -->
	<select id="add" resultType="Dept" parameterType="Dept">
		insert into dept(name,address) values(#{name},#{address});
	</select>
	<!-- 删除部门信息 -->
	<select id="delete" resultType="Dept" parameterType="int">
		delete from dept where id=#{id};
	</select>
</mapper>
```
### service层
在src/main/java下新建service包，新建DeptService接口，添加要用到的方法：
```
package com.DeptManage.service;

import java.util.List;

import com.DeptManage.entity.Dept;

public interface DeptService {
	List<Dept> findAll();//查询所有数据
	Dept findByName(String name);//根据部门名称查询数据
	boolean update(Dept dept);//修改部门信息
	boolean add(Dept dept);//增加部门信息
	boolean delete(int id);//删除部门信息
}
```
在src/main/java下新建serviceimpl包,新建DeptServiceImpl类，重写DeptService中声明的方法：
```
package com.DeptManage.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DeptManage.dao.DeptDao;
import com.DeptManage.entity.Dept;
import com.DeptManage.service.DeptService;

@Service
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptDao deptDao;

	@Override
	//查询所有数据
	public List<Dept> findAll() {
		// TODO 自动生成的方法存根
		return deptDao.findAll();
	}

	@Override
	//根据部门名称查询数据
	public Dept findByName(String name) {
		// TODO 自动生成的方法存根
		return deptDao.findByName(name);
	}

	@Override
	//修改部门信息
	public boolean update(Dept dept) {
		// TODO 自动生成的方法存根
		boolean flag=false;
		try {
			deptDao.update(dept);
			flag=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	//增加部门信息
	public boolean add(Dept dept) {
		// TODO 自动生成的方法存根
		boolean flag=false;
		try {
			deptDao.add(dept);
			flag=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	//删除部门信息
	public boolean delete(int id) {
		// TODO 自动生成的方法存根
		boolean flag=false;
		try {
			deptDao.delete(id);
			flag=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

}
```
### controller层
在src/main/java下新建controller包，新建DeptController类来处理请求返回数据:
```
package com.DeptManage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.DeptManage.entity.Dept;
import com.DeptManage.service.DeptService;

@RestController
@RequestMapping(value = "/api")
public class DeptController {
	@Autowired
	private DeptService deptService;
	
	//查询所有信息
	@RequestMapping(value = "/findAll",method = RequestMethod.GET)
	public List<Dept> findAll(){
		System.out.println("开始查询所有信息！");
		return deptService.findAll();
	}
	
	
	//根据部门名称查询信息
	@RequestMapping(value = "/findByName",method = RequestMethod.GET)
	public Dept findByName(@RequestParam(value = "name",required = true) String name){
		System.out.println("开始根据部门名称查询信息！");
		return deptService.findByName(name);
	}
	
	//修改部门信息
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public boolean update(Dept dept){
		System.out.println("开始修改部门信息！");
		return deptService.update(dept);
	}
	
	//增加部门信息
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public boolean add(Dept dept){
		System.out.println("开始增加部门信息！");
		return deptService.add(dept);
	}
	
	//删除部门信息
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public boolean delete(@RequestParam(value = "id",required = true) int id){
		System.out.println("开始删除部门信息！");
		return deptService.delete(id);
	}
}
```
***
至此，整个项目已搭建完毕，整体的框架如下：
![](https://raw.githubusercontent.com/butalways1121/img-Blog/master/21.png)
## 测试
运行程序：右键App.java，选择运行方式-Java应用程序，如果不出意外控制台会出现Hello World！，接着到postman中就可以进行测试了：
### 1.findAll-查询所有信息
在postman中设置如下信息，点击send之后就可以查看到所有的部门信息：
![](https://raw.githubusercontent.com/butalways1121/img-Blog/master/22.png)
### 2.findByName-根据部门名称查询信息
postman中传入name参数，这里以"name=技术科"为例:
![](https://raw.githubusercontent.com/butalways1121/img-Blog/master/23.png)
### 3.update-修改部门信息
对于"id=1"的部门信息进行修改,postman中传入参数:
![](https://raw.githubusercontent.com/butalways1121/img-Blog/master/24.png)
到数据库查看,"id=1"的部门信息已经修改成功：
![](https://raw.githubusercontent.com/butalways1121/img-Blog/master/25.png)
### 4.add-增加部门信息
增加"name=测试部&address=312"的部门信息：
![](https://raw.githubusercontent.com/butalways1121/img-Blog/master/26.png)
到数据库查看，该条信息已成功插入：
![](https://raw.githubusercontent.com/butalways1121/img-Blog/master/27.png)
### 5.delete-删除部门信息
以删除"id=8"的部门信息：
![](https://raw.githubusercontent.com/butalways1121/img-Blog/master/28.png)
到数据库查看，该条信息已被删除。
***
## OVER！
