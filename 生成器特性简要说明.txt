<?xml version="1.0" encoding="UTF-8"?><desc>

一. 生成器模板路径可以引用相关变量
	如 ${basepackage}/${className}.java,根据该变量生成输出文件

二. 自动搜索某个目录所有模板文件,无需配置


三.代码生成器模板可以引用的相关变量
	1. g.generateByTable("table_name") 方法可以引用的变量
		table : cn.org.rapid_framework.generator.provider.db.table.model.Table
	
	2. g.generateByClass(UserInfo.class) 方法可以引用的变量
		clazz : cn.org.rapid_framework.generator.provider.java.model.JavaClass

	3. g.generateBySql("select * from user_info where pwd=:pwd") 方法可以引用的变量
		sql : cn.org.rapid_framework.generator.provider.db.sql.model.Sql

	4.公共变量
		env : 系统环境变量
		System.getProperties() :  直接引用,没有前缀
		generator.properties 文件中的所有属性,直接引用,没有前缀
		gg : 模板控制变量, cn.org.rapid_framework.generator.GeneratorControl


四.每个模板有gg变量可以控制自身的自定义配置 (每一个模板都会创建新的gg实例)
	如是否生成,是否覆盖目标文件,甚至是生成其它文件
	${gg.setIgnoreOutput(true)}: 如果为true则不生成输出文件
	${gg.generateFile(outputFile,content)} 在模板中生成其它文件
	${gg.getProperty(key,defaultValue)}: 得到proproty,如果没有找到，则返回默认值
	${gg.getInputProperty(key)}: 会弹出一个输入框，提示用户输入值
	具体参考: http://code.google.com/p/rapid-framework/wiki/rapid_generator_gg

五.支持生成(gen)及删除操作(del),即生成的代码也可以很方便的删除


六. 自动删除模板扩展名: .ftl,.vm
      举例:  如你有一个模板 SqlMap.xml.ftl  将变为 SqlMap.xml 
             所以你要生成ftl扩展名的文件,应该将文件名从 list.ftl => list.ftl.ftl 
               
               
七. 模板自动include所有父目录的:macro.include文件,可以存放公共的macro
	示例: 如你的模板为 com/project/UserDao.java, 将自动include: com/project/macro.include, com/macro.include, macro.include


八. generator.xml (或者generator.properties)配置文件
	1.类似ant可以变量引用,引用环境变量使用${env.JAVA_HOME}, 引用System.getProperties()直接引用
	2.自动替换generator.properties中的句号(.)为反斜杠,设置key为key+"_dir"后缀
		示例: pkg=com.company => pkg_dir=com/company

九.自动拷贝二进制文件至输出目录
	如模板目录下的 zip,rar,doc文件将会自动拷贝至输出目录,不会破坏文件格式   (通过扩展名自动识别)

	
十. 数据库表配置,用于自定义生成器模板引用的table变量,配置文件必须存放在classpath: generator_config/table/table_name.xml 
   (该文件生成器可以生成，自己再自定义修改)
   
	<!--
	<数据库表名 className="类名称" tableAlias="表的别名">
		<数据库列名 columnAlias="列的别名"
		javaType="自定义javaType"
		unique="是否唯一性约束" nullable="是否可以为空" pk="是否主键,在表没有主键的情况下,可以指定一个代理主键"
		updatable="是否可以更新" insertable="是否插入"	
		enumString="枚举值,以分号分隔,示例值:M(1,男);F(0,女) 或者是:M(男);F(女)"
		enumClassName="如果枚举有值,生成的类名称将是这个,没有枚举值，该配置无用.示例值:Sex"
		/>
	</数据库表名>
	 -->
	<user_info className="UserInfo" tableAlias="用户信息" >
		<username columnAlias="用户名"
			javaType="String"
			unique="false" nullable="true" pk="false"
			updatable="true" insertable="true"
			enumString="F(1,Female);M(0,Male)" enumClassName="用户枚举"
		/>
		<password columnAlias="密码"
			javaType="String"
			unique="false" nullable="true" pk="false"
			updatable="true" insertable="true"
			enumString="" enumClassName="PasswordEnum"
		/>
	</user_info>

参考:
	模板引擎为freemarker,语法参考: http://freemarker.sourceforge.net/docs/index.html
 	生成器在线文档: http://code.google.com/p/rapid-framework/wiki/rapid_generator













</desc>