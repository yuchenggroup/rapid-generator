<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.${subpackage}.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import ${basepackage}.${subpackage}.service.${className}Service;
import ${basepackage}.${subpackage}.dao.${className}MybatisDao;

@Service
@Transactional
public class ${className}ServiceImpl extends BaseServiceImpl<${className},${table.idColumn.javaType}> implements ${className}Service{


	@Resource
	private ${className}MybatisDao ${classNameLower}MybatisDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void set${className}MybatisDao(${className}MybatisDao dao) {
		this.${classNameLower}MybatisDao = dao;
	}
	
	protected BaseDao<${className}, ${table.idColumn.javaType}> getDao() {
		return this.${classNameLower}MybatisDao;
	}
	
	
}
