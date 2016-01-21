<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import ${basepackage}.entity.${className};

import com.tq.management.base.system.entity.User;
import com.tq.management.base.utils.CrudUtils;
import com.tq.management.base.utils.DataTables;
import com.tq.management.base.utils.PatternEnum;
import com.tq.management.base.utils.WebDto;

/**
 * @version 1.0
 * @author tangqian
 */
@Service
public class ${className}Service {
    
	@Resource
	private SqlSessionTemplate template;
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> list(WebDto dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		String search = dto.getString("keyword");
		if (StringUtils.isNotBlank(search)) {
			dto.put("keyword", "%" + search + "%");
		}
		Integer totalNum = template.selectOne("${className}Mapper.count", dto);
		
		List<${className}> lists;
		if(totalNum > 0){
			lists = template.selectList("${className}Mapper.list", dto);
		}else{
			lists = Collections.EMPTY_LIST;
		}
		
		DataTables.map(map, dto, totalNum, totalNum, lists);
		return map;
	}
	
	public void add(WebDto dto) {
		CrudUtils.beforeAdd(dto);
		template.insert("${className}Mapper.add", dto);
	}
    
	public void delete(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		CrudUtils.beforeUpdate(map);
		template.delete("${className}Mapper.delete", map);
	}

	public boolean batchDelete(String ids) {
		boolean success = false;
		if (StringUtils.isNotBlank(ids) && PatternEnum.IDS.isValid(ids)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ids", ids);
			CrudUtils.beforeUpdate(map);
			template.delete("${className}Mapper.batchDelete", map);
			success = true;
		}
		return success;
	}

	public ${className} get(Integer id) {
		return template.selectOne("${className}Mapper.get", id);

	}

	public void update(WebDto dto) {
		CrudUtils.beforeUpdate(dto);
		template.update("${className}Mapper.update", dto);
	}
}
