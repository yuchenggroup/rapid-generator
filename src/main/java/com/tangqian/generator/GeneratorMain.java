/*
 * 文 件 名:  GeneratorMain.java
 * 创 建 人:  tangqian
 * 创建时间:  2014-4-2
 */
package com.tangqian.generator;

import cn.org.rapid_framework.generator.GeneratorFacade;

/**
 * <一句话功能简述>
 */
public class GeneratorMain {
    public static void main(String[] args) throws Exception {
        GeneratorFacade g = new GeneratorFacade();
        
        g.deleteOutRootDir();   //删除生成器的输出目录
              g.generateByTable("sys_menu","template");     //通过数据库表生成文件,template为模板的根目录

//        g.generateByAllTable("template");       //自动搜索数据库中的所有表并生成文件,template为模板的根目录
//              g.generateByClass(Blog.class,"template_clazz");
        
//              g.deleteByTable("table_name", "template"); //删除生成的文件
        
        
        /*Sql sql = new SqlFactory().parseSql("select * from base_role where role_name=#{param} ");  //同时支持 #param# $param$ #{param} ${param} :param 几种占位符
        sql.setTableSqlName("base_role");
        sql.setMultiplicity("many");  //many or one,用于控制查询结果是one,many
        sql.setOperation("findByName"); 
        sql.setRemarks("根据用户名及密码进行查询");
        new GeneratorFacade().generateBySql(sql, "template");*/
    }
}
