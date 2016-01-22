<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
${gg.setOverride(false)}
package ${basepackage}.${subpackage}.dao;


import com.weijinsuo.core.repository.mybatis.BaseDao;
import com.weijinsuo.core.repository.mybatis.MyBatisRepository;

import ${basepackage}.${subpackage}.daogen.${className}MybatisDaoGen;

@MyBatisRepository
public interface ${className}MybatisDao extends ${className}MybatisDaoGen{
	

}
