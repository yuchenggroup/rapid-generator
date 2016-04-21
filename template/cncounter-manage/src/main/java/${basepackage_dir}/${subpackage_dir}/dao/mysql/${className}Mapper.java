<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.${subpackage}.dao.mysql;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ${basepackage}.${subpackage}.model.${className};

@Repository
public interface ${className}Mapper {
    
    ${className} getById(Integer id);
    
    int countBy(Map<String, Object> params);

    List<${className}> listPage(Map<String, Object> params);
    
    int insert(${className} ${classNameLower});
    
    int update(${className} ${classNameLower});
    
    int deleteById(Integer id);
}