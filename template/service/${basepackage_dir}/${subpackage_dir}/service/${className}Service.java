<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.${subpackage}.service;


public interface ${className}Service extends BaseService<${className},${table.idColumn.javaType}>{

		
}
