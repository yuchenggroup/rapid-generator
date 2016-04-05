# rapid-generator
半自动化Java代码生成器[利用freemarker模板生成]

本项目Github地址: [https://github.com/yuchenggroup/rapid-generator](https://github.com/yuchenggroup/rapid-generator)


项目最初来自于 [https://code.google.com/p/rapid-generator/](https://code.google.com/p/rapid-generator/)

增加一些定制和扩展, 修改为基于MAVEN的格式。 整体架构保持不变。

增加的特性为:

1. 支持**表名前缀**去除,参考配置文件中的 `tableRemovePrefixes`
2. 支持**列名前缀**去除,参考配置文件中的 `rowRemovePrefixes`
3. 支持需要**忽略**的表,参考配置文件中的 `skipTablePrefixes`
4. 支持子包,参考配置文件中的  `subpackage`,其实原版就支持, 目录为属性KEY加上 `_dir` 即可,例如 "`${xxxKEY_dir}`"
5. 其他一些模板使用上的修正,参考 template 目录



需要一个依赖: FreeMarker, 手册地址: [http://yuchenggroup.github.io/rapid-generator/FreeMarker_2.3.23_Manual_zh_CN/index.html](http://yuchenggroup.github.io/rapid-generator/FreeMarker_2.3.23_Manual_zh_CN/index.html)

开始日期: 2016年1月21日


# 使用说明

## 1. 构建项目/编译

执行 `maven_clean_package.cmd` 文件或者以下命令进行编译:

	mvn clean package -DskipTests

编译后的文件在 `target/bin` 目录下。 目录结构为:

	|--lib\				: 依赖库
	|--template\		: 模板文件
	|--generator.xml	: 配置文件
	|--rapid-gen.bat	: Win32脚本文件


## 2. 代码生成

### 2.1 启动生成器

将`bin`目录拷贝到需要的地方, 这就是生成文件需要的东西。

拷贝好之后, 修改`generator.xml`,需要修改的 **entry** 包括:

	basepackage
	subpackage
	jdbc.url
	jdbc.username
	jdbc.password



双击执行`rapid-gen.bat`文件,或者执行命令:

Windows下:

	set classpath=%classpath%;.;.\lib\*

	java -Xms128m -Xmx384m cn.org.rapid_framework.generator.ext.CommandLine -DtemplateRootDir=template

Linux 系统使用 **export** 设置环境变量。

Linux 系统(**未验证!!**):

	export CLASSPATH=$CLASSPATH:.:./lib/*

	java -Xms128m -Xmx384m cn.org.rapid_framework.generator.ext.CommandLine -DtemplateRootDir=template


### 2.2 使用生成器

启动之后如果不报错,则提示信息为:

	templateRootDir:E:\00_GIT_ALL\rapid-generator\target\bin\template
	Usage:
	        gen table_name [include_path]: generate files by table_name
	        del table_name [include_path]: delete files by table_name
	        gen * [include_path]: search database all tables and generate files
	        del * [include_path]: search database all tables and delete files
	        quit : quit
	        [include_path] subdir of templateRootDir,example: 1. dao  2. dao/**,service/**
	please input command:_

根据提示, 输入 `gen *` 回车,则对所有表进行生成。

输入 `gen 表名` 则是生成单个表。

生成之后的目录默认为 `generator-output`

生成完毕之后,因为这是一个半自动生成器,所以需要手工拷贝代码。

这也是合理的,因为代码开发是程序员的事。 这不是自动编译或者发布,所以。。。



### 2.3 其他

默认的模板是为项目 cncounter-web 生成的, 项目地址为:

[https://github.com/cncounter/cncounter](https://github.com/cncounter/cncounter)

Controller层需要依赖的文件存放于 `template/cnc_main/other_java_file`目录下,请根据需要进行使用。


- 高级用法请自己摸索。
- template 下可以自己进行修改,可以参考源码,以及 Freemarker的官方文档(见上方)。
- 配置文件信息,请参考`generator.xml`里面的注释。


#### freemarker中key有特殊字符，如(.-等)

	root key 使用：
	.vars["aaaa.bbb"]
	.vars["aaaa-bbb"]

	非root key 使用：
	ccc["ddd.eee"]
	ccc["ddd-eee"]


联系方式: `renfufei@qq.com`



