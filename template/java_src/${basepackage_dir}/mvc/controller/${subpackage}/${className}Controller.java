<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.mvc.controller.${subpackage};

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ${basepackage}.mvc.controller.base.BaseController;
import ${basepackage}.model.${subpackage}.${className};
import ${basepackage}.service.${subpackage}.${className}Service;

/**
 * @version 1.0
 * @author 
 */
@Controller
@RequestMapping("/${classNameLower}")
public class ${className}Controller extends BaseController {
    
    @Resource
    private ${className}Service ${shortName}Service;
    
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public JSONMessage list(HttpServletRequest request) {
		// get params
		Map<String, Object> params = WebUtils.parseParam(request);
		//
		Integer count = ${shortName}Service.countBy(params);
		List<${className}> ${shortName}List = ${shortName}Service.listBy(params);
		//
		JSONMessage jsonMessage = JSONMessage.successMessage();
		jsonMessage.setCount(count);
		jsonMessage.setData(${shortName}List);

		return jsonMessage;
	}

	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONMessage doAdd(HttpServletRequest request) {
		// get params
		Map<String, Object> params = WebUtils.parseParam(request);
		//
		${className} ${shortName} = new ${className}();
		//
		BeanUtils.map2Bean(params, ${shortName});
		//
		Integer rows = ${shortName}Service.add(${shortName});

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
		Map<String, Object> params = WebUtils.parseParam(request);
		//
		${className} ${shortName} = new ${className}();
		//
		BeanUtils.map2Bean(params, ${shortName});
		//
		Integer rows = ${shortName}Service.update(${shortName});

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
		Map<String, Object> params = WebUtils.parseParam(request);
		//
		Integer id = params.get("id");
		//
		Integer rows = ${shortName}Service.delete(id);

		//
		JSONMessage jsonMessage = JSONMessage.successMessage();
		if(rows < 1){
			jsonMessage = JSONMessage.failureMessage();
		}
		return jsonMessage;
	}

}
