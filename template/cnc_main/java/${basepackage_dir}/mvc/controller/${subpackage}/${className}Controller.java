<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.mvc.controller.${subpackage};

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cncounter.cncounter.mvc.msg.JSONMessage;
import com.cncounter.cncounter.mvc.controller.base.ControllerBase;
import ${basepackage}.model.${subpackage}.${className};
import ${basepackage}.service.api.${subpackage}.${className}Service;

/**
 * @version 1.0
 * @author 
 */
@Controller
@RequestMapping("/${classNameLower}")
public class ${className}Controller extends ControllerBase {
    
    @Resource
    private ${className}Service ${classNameLower}Service;
    
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public JSONMessage list(HttpServletRequest request) {
		// get params
		Map<String, Object> params = parseParamMapObject(request);
		//
		Integer count = ${classNameLower}Service.countBy(params);
		List<${className}> ${classNameLower}List = ${classNameLower}Service.listBy(params);
		//
		JSONMessage jsonMessage = JSONMessage.successMessage();
		jsonMessage.setTotal(count);
		jsonMessage.setData(${classNameLower}List);

		return jsonMessage;
	}

	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONMessage doAdd(HttpServletRequest request) {
		// get params
		Map<String, Object> params = parseParamMapObject(request);
		//
		${className} ${classNameLower} = new ${className}();
		//
		BeanUtils.map2Bean(params, ${classNameLower});
		//
		Integer rows = ${classNameLower}Service.add(${classNameLower});

		//
		JSONMessage jsonMessage = JSONMessage.successMessage();
		if(rows < 1){
			jsonMessage = JSONMessage.failureMessage();
		}
		return jsonMessage;
	}
	

	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONMessage doEdit(HttpServletRequest request) {
		// get params
		Map<String, Object> params = parseParamMapObject(request);
		//
		${className} ${classNameLower} = new ${className}();
		//
		BeanUtils.map2Bean(params, ${classNameLower});
		//
		Integer rows = ${classNameLower}Service.update(${classNameLower});

		//
		JSONMessage jsonMessage = JSONMessage.successMessage();
		if(rows < 1){
			jsonMessage = JSONMessage.failureMessage();
		}
		return jsonMessage;
	}
	

	@RequestMapping(value = "/delete.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request) {
		// get params
		Map<String, Object> params = parseParamMapObject(request);
		//
		Integer id = params.get("id");
		//
		Integer rows = ${classNameLower}Service.delete(id);

		//
		JSONMessage jsonMessage = JSONMessage.successMessage();
		if(rows < 1){
			jsonMessage = JSONMessage.failureMessage();
		}
		return jsonMessage;
	}

}
