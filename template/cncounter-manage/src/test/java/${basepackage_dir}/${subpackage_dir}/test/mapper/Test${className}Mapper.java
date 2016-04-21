<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.${subpackage}.test.mapper;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import ${basepackage}.${subpackage}.model.${className};
import ${basepackage}.${subpackage}.dao.mysql.${className}Mapper;

/**
 * @version 1.0
 * @author 
 * 单元测试 ${table.tableAlias}: ${className}Mapper
 */
@ContextConfiguration("classpath:generated/testSpringContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional // 事务必须要Junit看得见才能回滚
public class Test${className}Mapper {
    
    private Log logger = LogFactory.getLog(this.getClass());
    
    @Autowired
    private ${className}Mapper ${classNameLower}Mapper;
    
    @Before
    public void setUp(){
        Assert.notNull(${classNameLower}Mapper, "${classNameLower}Mapper 不能为 null");
        // 此处可以做一些初始化操作
    }
    @After
    public void tearDown(){
    	${classNameLower}Mapper = null;
        // 此处可以做一些清理操作
    }
    
    @Test
    public  void testListBy(){
        logger.debug("开始测试 ${classNameLower}Mapper.listPage(params)");
        Map<String, Object> params = new HashMap<String, Object>();
        List<${className}> resultList =  ${classNameLower}Mapper.listPage(params);
        //
        Assert.notNull(resultList, "resultList 不能为 null");
        logger.debug("${classNameLower}Mapper.listPage(params)测试结束. resultList.size()=" + resultList.size());
    }

    @Test
    public  void testCountBy(){
        logger.debug("开始测试 ${classNameLower}Mapper.countBy(params)");
        Map<String, Object> params = new HashMap<String, Object>();
        int result =  ${classNameLower}Mapper.countBy(params);
        //
        Assert.isTrue(result >= 0, "result 不能为 负数");
        logger.debug("${classNameLower}Mapper.countBy(params)测试结束. result=" + result);
    }

    @Test
    @Rollback(true)
    public  void testSave(){
        logger.debug("开始测试 ${classNameLower}Mapper.add(null)");
        ${className} condition = new ${className}();
        int result =  0;//${classNameLower}Mapper.insert(null);
        //
        Assert.isTrue(result >= 0, "result 不能为 负数");
        logger.debug("${classNameLower}Mapper.add(condition)测试结束. result=" + result);
    }

}
