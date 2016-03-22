/*
 * 文 件 名:  ${table.className}Dao.java
 * 创 建 人:  tangqian
 * 创建时间:  <#if now??>${now?string('yyyy-MM-dd')}</#if>
 */
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.dao.api.${subpackage};

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import ${basepackage}.model.${subpackage};

@Repository
public interface ${className}Dao {
    
    ${className} getById(Integer id);
    
    List<${className}> getPageList(@Param("page") Pageable<${className}> page, @Param("dto") BaseSearchDto dto);
    
    void insert(${className} ${shortName});
    
    void update(${className} ${shortName});
    
    void delete(Map<String, Object> map);
}