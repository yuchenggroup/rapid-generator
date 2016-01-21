<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.dao;

import java.io.Serializable;
import com.app.base.common.model.CommonEntity;

/**
 * <一句话功能简述>
 */
public class ${className} extends CommonEntity implements Serializable {
	<#list table.columns as column>
	<@generateBycondition column.sqlName>
	
	private ${column.simpleJavaType} ${column.columnNameLower};
	</@generateBycondition>
	</#list>
	
<@generateJavaColumns/>
}
<#macro generateJavaColumns>
	<#list table.columns as column>
    <@generateBycondition column.sqlName>
	public void set${column.columnName}(${column.simpleJavaType} value) {
		this.${column.columnNameLower} = value;
	}
	
	public ${column.simpleJavaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	
	</@generateBycondition>
	</#list>
</#macro>