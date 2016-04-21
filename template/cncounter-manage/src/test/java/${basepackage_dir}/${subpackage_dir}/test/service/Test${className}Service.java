<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.${subpackage}.test.service;

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
import ${basepackage}.${subpackage}.service.${className}Service;

/**
 * @version 1.0
 * @author 
 * 单元测试 ${table.tableAlias}: ${className}Service
 */
@ContextConfiguration("classpath:generated/testSpringContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional // 事务必须要Junit看得见才能回滚
public class Test${className}Service {
    
    private Log logger = LogFactory.getLog(this.getClass());
    
    @Autowired
    private ${className}Service ${classNameLower}Service;
    
    @Before
    public void setUp(){
        Assert.notNull(${classNameLower}Service, "${classNameLower}Service 不能为 null");
        // 此处可以做一些初始化操作
    }
    @After
    public void tearDown(){
    	${classNameLower}Service = null;
        // 此处可以做一些清理操作
    }
    
    @Test
    public  void testListBy(){
        logger.debug("开始测试 ${classNameLower}Service.listPage(params)");
        Map<String, Object> params = new HashMap<String, Object>();
        List<${className}> resultList =  ${classNameLower}Service.listPage(params);
        //
        Assert.notNull(resultList, "resultList 不能为 null");
        logger.debug("${classNameLower}Service.listPage(params)测试结束. resultList.size()=" + resultList.size());
    }

    @Test
    public  void testCountBy(){
        logger.debug("开始测试 ${classNameLower}Service.countBy(params)");
        Map<String, Object> params = new HashMap<String, Object>();
        int result =  ${classNameLower}Service.countBy(params);
        //
        Assert.isTrue(result >= 0, "result 不能为 负数");
        logger.debug("${classNameLower}Service.countBy(params)测试结束. result=" + result);
    }

    @Test
    @Rollback(true)
    public  void testSave(){
        logger.debug("开始测试 ${classNameLower}Service.add(null)");
        ${className} condition = new ${className}();
        int result =  ${classNameLower}Service.add(null);
        //
        Assert.isTrue(result >= 0, "result 不能为 负数");
        logger.debug("${classNameLower}Service.add(condition)测试结束. result=" + result);
    }

}
