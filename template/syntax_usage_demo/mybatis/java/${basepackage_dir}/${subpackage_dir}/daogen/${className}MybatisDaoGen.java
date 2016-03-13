<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.${subpackage}.dao.daogen;


import com.weijinsuo.core.repository.mybatis.BaseDao;
import com.weijinsuo.core.repository.mybatis.MyBatisRepository;

public interface ${className}MybatisDaoGen extends BaseDao<${className},${table.idColumn.javaType}>{

	<#list table.columns as column>
	<#if column.unique && !column.pk>
    // 接口不会有方法实现
    // 此处仅作示例,如何取值
	public ${className} getBy${column.columnName}(${column.javaType} v) {
		return (${className})getSqlSessionTemplate().selectOne("${className}.getBy${column.columnName}",v);
	}	
	
	</#if>
	</#list>

}
