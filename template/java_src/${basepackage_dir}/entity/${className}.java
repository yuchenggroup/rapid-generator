<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.entity;

import java.io.Serializable;
import com.tq.management.base.entity.SuperEntity;

/**
 * @version 1.0
 * @author tangqian
 */
public class ${className} extends SuperEntity implements Serializable {

<@generateJavaColumns/>
	<#list table.columns as column>
	<@generateBycondition column.sqlName>
	
	private ${column.simpleJavaType} ${column.columnNameLower};
	</@generateBycondition>
	</#list>
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