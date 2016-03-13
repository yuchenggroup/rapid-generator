/*
 * 文 件 名:  ${table.className}ServiceImpl.java
 * 创 建 人:  
 * 创建时间:  <#if now??>${now?string('yyyy-MM-dd')}</#if>
 */
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ${basepackage}.entity.${className};
import ${basepackage}.dao.${className}Dao;

/**
 * <一句话功能简述>
 */
@Service
public class ${className}ServiceImpl implements ${className}Service {
	
    @Resource
    private ${className}Dao ${shortName}Dao;
    
	public int add(${className} ${shortName}) {
        int rows = ${shortName}Dao.insert(${shortName});
        return rows;
	}

    
    public int update(${className} ${shortName}) {
        int rows = ${shortName}Dao.update(${shortName});
        return rows;
    }
    
    
    public int delete(Integer id) {
        int rows = ${shortName}Dao.delete(id);
        return rows;
    }
    
    
    public ${className} getById(Integer id) {
		${className} ${shortName} = ${shortName}Dao.get(id);
		//
        return ${shortName};
    }
	
	public Integer countBy(Map<String, Object> params){
		processPageParams(params);
		Integer rows = ${shortName}Dao.countBy(params);
		return rows;
	}
	
	public List<${className}> list(Map<String, Object> params){
		processPageParams(params);
		List<${className}> lists = ${shortName}Dao.listBy(params);
		
		return lists;
	}

	
	private static void processPageParams(Map<String, Object> params){
		// 此段代码可以迁移到工具类之中
		if(null == params){
			return;
		}
		Integer pageSize = 20;
		Integer page = 0;
		Object _pageSize = params.get("pageSize");
		Object _page = params.get("page");
		if(null != _pageSize){
			pageSize = (Integer)_pageSize;
		}
		if(null != _page){
			page = (Integer)_page;
		}
		//
		Integer start = page * pageSize;
		//
		params.put("start", start);
		params.put("pageSize", pageSize);
	}
    
}
