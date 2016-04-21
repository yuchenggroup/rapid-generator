<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.service;

import java.util.List;
import java.util.Map;


import ${basepackage}.model.${className};

/**
 * @version 1.0
 * @author 
 */
public interface ${className}Service {
	
	public int add(${className} ${classNameLower});

	public int update(${className} ${classNameLower});
    
	public int delete(Integer id);

	public ${className} getById(Integer id);

	public Integer countBy(Map<String, Object> params);

	public List<${className}> listPage(Map<String, Object> params);

}
