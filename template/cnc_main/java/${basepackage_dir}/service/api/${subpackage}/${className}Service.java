<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.service.api.${subpackage};

import java.util.List;
import java.util.Map;


import ${basepackage}.model.${className};

/**
 * @version 1.0
 * @author 
 */
public interface ${className}Service {
	
	public int add(${className} ${shortName});

	public int update(${className} ${shortName});
    
	public int delete(Integer id);

	public ${className} getById(Integer id);

	public Integer countBy(Map<String, Object> params);

	public List<${className}> listBy(Map<String, Object> params);

}
