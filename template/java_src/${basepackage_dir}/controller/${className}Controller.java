<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tq.management.base.controller.BaseController;
import ${basepackage}.entity.${className};
import ${basepackage}.service.${className}Service;
import com.tq.management.base.utils.WebDto;

/**
 * @version 1.0
 * @author tangqian
 */
@Controller
@RequestMapping("/${classNameLower}")
public class ${className}Controller extends BaseController {
    
    private static final String LIST = "system/${classNameLower}/${classNameLower}_list";
    
    private static final String ADD = "system/${classNameLower}/${classNameLower}_add";
    
    private static final String EDIT = "system/${classNameLower}/${classNameLower}_edit";
    
    private static final String VIEW = "system/${classNameLower}/${classNameLower}_view";
    
    @Resource
    private ${className}Service ${classNameLower}Service;
    
	@RequestMapping
	public ModelAndView page() {
		${shortName}
		return new ModelAndView(LIST);
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list() {
		WebDto dto = new WebDto(getRequest());
		return ${classNameLower}Service.list(dto);
	}
    
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		return new ModelAndView(ADD);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doAdd() {
		Map<String, Object> map = new HashMap<String, Object>();
		WebDto dto = new WebDto(getRequest());
		${classNameLower}Service.add(dto);
		map.put("status", 1);
		return map;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = true) Integer id) {
		ModelAndView mv = new ModelAndView(EDIT);
		${className} ${classNameLower} = ${classNameLower}Service.get(id);
		mv.addObject("entity", ${classNameLower});
		return mv;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doEdit() {
		Map<String, Object> map = new HashMap<String, Object>();
		WebDto dto = new WebDto(getRequest());
		${classNameLower}Service.update(dto);
		map.put("status", 1);
		return map;
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(required = true) Integer id) {
		ModelAndView mv = new ModelAndView(VIEW);
		${className} ${classNameLower} = ${classNameLower}Service.get(id);
		mv.addObject("entity", ${classNameLower});
		return mv;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(required = true) Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		${classNameLower}Service.delete(id);
		map.put("status", 1);
		return map;
	}

	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchDelete(@RequestParam(required = true) String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = ${classNameLower}Service.batchDelete(ids);
		if (result) {
			map.put("status", 1);
		} else {
			map.put("status", 0);
			map.put("msg", "参数为空或者参数值非法");
		}
		return map;
	}
}
