rapid-generator 模板使用说明
==

1. 一个实体table，就会将 template 下面的所有模板生成一份。
2. 可以自定义属性 entry
3. table支持的属性有:




> 将文件系统的目录名称及文件名称作为生成器的一部分,模板文件的的名称与目录名称可以直接引用相关变量,如${basepackage}/${className}.java (${className}=Blog,则会生成Blog.java)

<br/>



>支持文件插入操作,如模板输出生成的地方已经有该同名的文件存在,并且文件中有包含"webapp-generator-insert-location"标记,则模板生成的内容会插入在该标记之后该特性对如生成的spring配置内容插入spring配置文件十分有用


<br/>


>以@testExpression结尾的模板文件为有条件忽略,如果testExpression的值在数据模型为true则生成该文件,生成的文件不会包含@testExpression,反之则不生成该文件





