<#include "/macro.include"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.${subpackage}.model;

import java.util.*;
import com.weijinsuo.core.model.BaseModel;
<#if table.columns?seq_contains("PK_I_SID") && table.columns?seq_contains("I_VERSION")>import com.weijinsuo.core.repository.optimistlock.OptimisticEntity;</#if>

// ${table.remarks}
public class ${className} extends BaseModel <#if table.columns?seq_contains("PK_I_SID") && table.columns?seq_contains("I_VERSION")>implements OptimisticEntity</#if> {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "${table.tableAlias}";
	<#list table.columns as column>
	public static final String ALIAS_${column.constantName} = "${column.columnAlias}";
	</#list>
	
	
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	<#list table.columns as column>
    // ${column.columnAlias}
	private ${column.javaType} ${column.columnNameLower};
	</#list>
	//columns END

    <#list table.columns as column>
    public ${column.javaType} get${column.columnName}(){
        return this.${column.columnNameLower};
    }
    public void set${column.columnName}(${column.javaType} ${column.columnNameLower}){
        this.${column.columnNameLower} = ${column.columnNameLower};
    }

    </#list>
}
