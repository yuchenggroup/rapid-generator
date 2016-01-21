/*
 * 文 件 名:  ${table.className}ServiceImpl.java
 * 创 建 人:  tangqian
 * 创建时间:  <#if now??>${now?string('yyyy-MM-dd')}</#if>
 */
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.base.common.dto.BaseSearchDto;
import com.app.base.common.dto.ServiceResult;
import ${basepackage}.dao.${className};
import ${basepackage}.dao.${className}Dao;
import com.app.base.security.SessionUser;
import com.app.base.utils.CodeGenerator;
import com.gv.app.epg.util.page.Pageable;

/**
 * <一句话功能简述>
 */
@Service
public class ${className}ServiceImpl implements ${className}Service {
    
    @Resource
    private ${className}Dao ${classNameLower}Dao;
    
    @Override
    public Pageable<${className}> getList(BaseSearchDto dto) {
        Pageable<${className}> page = new Pageable<${className}>(dto.getNumPerPage(), dto.getPageNum());
        List<${className}> ${shortName}s = ${classNameLower}Dao.getPageList(page, dto);
        page.setList(${shortName}s);
        return page;
    }
    
    @Override
    public ServiceResult save(${className} ${shortName}) {
        ${shortName}.setCode(CodeGenerator.getUniqueCode(CODE));
        ${classNameLower}Dao.insert(${shortName});
        return ServiceResult.SUCCESS;
    }
    
    @Override
    public ServiceResult update(${className} ${shortName}) {
        ${classNameLower}Dao.update(${shortName});
        return ServiceResult.SUCCESS;
    }
    
    @Override
    public ServiceResult delete(String ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ids", ids);
        map.put("operator", SessionUser.getOperator());
        map.put("lastUpdatedDate", new Date());
        ${classNameLower}Dao.delete(map);
        return ServiceResult.SUCCESS;
    }
    
    @Override
    public ${className} getById(Integer id) {
        return ${classNameLower}Dao.getById(id);
    }
    
    @Override
    public ${className} view(Integer id) {
        return ${classNameLower}Dao.getById(id);
    }
}
