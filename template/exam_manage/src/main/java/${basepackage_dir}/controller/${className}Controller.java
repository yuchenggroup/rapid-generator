<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import ${basepackage}.controller.base.ControllerBase;
import ${basepackage}.common.utils.StringNumberUtil;
import ${basepackage}.entity.vo.JSONMessage;
import ${basepackage}.entity.po.${className};
import ${basepackage}.service.${className}Service;

/**
 * @version 1.0
 * @author 
 */
@Controller
@RequestMapping("/${classNameLower}")
public class ${className}Controller extends ControllerBase {
    
    @Autowired
    private ${className}Service ${classNameLower}Service;
    
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public JSONMessage list(HttpServletRequest request) {
		// get params
		Map<String, Object> params = parseParamMapObject(request);
        processPageParams(params);
		//
		Integer count = ${classNameLower}Service.countBy(params);
		List<${className}> ${classNameLower}List = ${classNameLower}Service.listPage(params);
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
		map2Bean(params, ${classNameLower});
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
		map2Bean(params, ${classNameLower});
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
	public JSONMessage delete(HttpServletRequest request) {
		// get params
		Map<String, Object> params = parseParamMapObject(request);
		//
		Integer id = 0;
		Object _id = params.get("id");
		if(null != _id && StringNumberUtil.isLong(_id.toString())){
			id = StringNumberUtil.parseInt(_id.toString(), 0);
		}
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
