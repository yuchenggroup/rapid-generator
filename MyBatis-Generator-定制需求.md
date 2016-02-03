中微2.0平台MyBatis-Generator-定制需求
==

> 当前版本: V0.0.3
> 
> 日期: 2016年1月20日


<del>1.生成Model时,对应数据库中的表名前缀自动去除,例如: **OK!!**<del>

	T_AR,T_BD,T_CD,T_PD,T_CL,T_IP,T_LO,T_RI,T_EV

2.如果前缀去除后表名会有冲突,则需要支持手工指定类名。

<del>3.生成字段时列名的通用前缀也需要去除, 例如:(**OK!!)**<del>

	 I, F, M, C, S, D, T, DT, TS, B, E, TE

4.DAO层:

- <del>生成的XML分为2个`***GenMapper.xml` 以及 `***ManualMapper.xml` ,  MyBatis 会根据同一个 `<mapper namespace=""/>` 进行组合。(**OK!!)**<del>

-   <del>生成的DAO接口类,分为2个: `XxxDao extends XxxDaoGen` (**OK!!)**<del>

- 手动增加的SQL实现放到 `***ManualMapper.xml` 文件之中。重新生成时不进行覆盖(或者人工处理)。
- 手动增加的DAO方法放到 **XxxDao.java** 文件中, 重新生成时存在则不进行覆盖(或者人工处理)。

<del>5.生成的代码中,去除 lombok 相关的注解。</del>

<del>6.生成的 `Example` 需要保留,部分程序员需要依赖此类文件。</del>

7.需要生成的代码部分包括:

- DAO(xml, java)及基础实现
- Model, Java类
- <del>其他(不紧急, Service 之类的代码)</del>

<del>8.避免原有生成器中,在DAO接口有声明,但 XML没有对应实现的BUG。</del>

<del>9.附加: 最好以官方的生成器为主,方便维护以及架构调整,避免耦合。</del>
