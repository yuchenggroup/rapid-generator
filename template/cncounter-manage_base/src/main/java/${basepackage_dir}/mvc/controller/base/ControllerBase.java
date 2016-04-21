package ${basepackage}.mvc.controller.base;


import ${basepackage}.util.common.StringNumberUtil;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * Spring MVC控制器的基类, 对Session访问提供统一方法,<br/>
 * 子类应该使用基类提供的方法,以方便今后的集群部署[届时只需要修改此类中的实现即可]。
 */
public abstract class ControllerBase {
	
	public static final String UTF_8 = "UTF-8";

	/**
	 * 设置session属性
	 * @param request HttpServletRequest 请求对象
	 * @param name 属性名
	 * @param value 属性值, 可序列化对象
	 */
	public  void setSessionAttribute(HttpServletRequest request, String name, Serializable value) {
		// 当前是基于单容器的实现
		HttpSession session = request.getSession(true);
		session.setAttribute(name, value);
		//
	}

	/**
	 * 设置Session存活时间
	 * @param request
	 * @param aliveTimeSeconds
	 */
	public static void setSessionAliveTime(HttpServletRequest request, int aliveTimeSeconds) {
		// 当前是基于单容器的实现
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(aliveTimeSeconds);
	}
	
	/**
	 * 根据 request取得Session属性值
	 * @param request HttpServletRequest 请求对象
	 * @param name 属性名
	 * @return
	 */
	public  Object getSessionAttribute(HttpServletRequest request, String name) {
		// 当前是基于单容器的实现
		HttpSession session = request.getSession(true);
		return session.getAttribute(name);
	}
	

	/**
	 * 获取 上下文 path, 返回如 "/cncounter"
	 * @param request
	 * @return
	 */
	protected static String path(HttpServletRequest request){
		String path = request.getContextPath();
		return path;
	}
	/**
	 * 获取 basePath
	 * @param request
	 * @return
	 */
	protected static String basePath(HttpServletRequest request){
		String basePath = basePathLessSlash(request) + "/";
		return basePath;
	}
	/**
	 * 获取最后面少一个斜线的basePath
	 * @param request
	 * @return
	 */
	protected static String basePathLessSlash(HttpServletRequest request){
		String path = path(request);
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
		return basePath;
	}

	/**
	 * 获取参数或者Cookie
	 * @param request
	 * @param name
	 * @return
	 */
	protected String getStringValue(HttpServletRequest request, String name){
		// 1. 获取直接参数
		String value = getParameter(request, name);
		if(StringNumberUtil.notEmpty(value)){
			return value;
		}
		// 2. 获取 cookie
		value = getCookie(request, name);
		if(StringNumberUtil.notEmpty(value)){
			return value;
		}
		// 3. 获取session
//		Object v = getSessionValue(request, name);
//		if(StringNumberUtil.notEmpty(v)){
//			return String.valueOf(v);
//		}
		// 4. 获取缓存
//		v = getFromCache(name);
//		if(StringNumberUtil.notEmpty(v)){
//			return String.valueOf(v);
//		}
		//
		return value;
	}

	public static String getCookie(HttpServletRequest request, String name){
		Map<String, String> cookieMap = getCookies(request);
		//
		String value = cookieMap.get(name);
		return value;
	}

	public static Map<String, String> getCookies(HttpServletRequest request){
		//
		Map<String, String> cookieMap = new HashMap<String, String>();
				//
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			//
			String name = cookie.getName();
			String value = cookie.getValue();
			//
			name = name.trim();
			value = value.trim();
			cookieMap.put(name, value);
		}
		//
		return cookieMap;
	}
	/**
	 * 获取参数
	 * @param request
	 * @param name
	 * @return
	 */
	protected static String getParameter(HttpServletRequest request, String name){
		String value = request.getParameter(name);
		//
		return value;
	}
	/**
	 * 获取参数,如果为下面的值,则返回指定的默认值: <br/>
	 * 包括: null, "", "null", "undefined"
	 * @param request
	 * @param name 参数名
	 * @param defValue 指定默认值
	 * @return 如果为空或不存在,则返回默认值
	 */
	protected static String getParameterString(HttpServletRequest request, String name, String defValue){
		String value = request.getParameter(name);
		if(null == value){
			return defValue;
		} else {
			value = value.trim();
			if("".equals(value) || "null".equals(value) || "undefined".equals(value)){
				return defValue;
			}
		}
		//
		return value;
	}



	public static Object getSessionValue(HttpServletRequest request, String name){
		//
		HttpSession session = request.getSession();
		if(null == session){
			return null;
		}
		Object value = session.getAttribute(name);
		return value;
	}
	

	/**
	 * 获取int类型参数
	 * @param request
	 * @param name
	 * @param defValue
	 * @return
	 */
	protected static int getParameterInt(HttpServletRequest request, String name, int defValue){
		String value = request.getParameter(name);
		//
		return StringNumberUtil.parseInt(value, defValue);
	}


    public static void processPageParams(Map<String, Object> params){
        // 此段代码可以迁移到工具类之中
        if(null == params){
            return;
        }
        Integer pageSize = 20;
        Integer page = 0;
        Object _pageSize = params.get("pageSize");
        Object _page = params.get("page");
        if(_pageSize instanceof Integer){
            pageSize = (Integer)_pageSize;
        } else if(_pageSize instanceof String){
            pageSize = StringNumberUtil.parseInt(_pageSize.toString(), pageSize);
        }
        if(_page instanceof Integer){
            page = (Integer)_page;
        } else if(_page instanceof String){
            page = StringNumberUtil.parseInt(_page.toString(), page);
        }
        //
        Integer start = page * pageSize;
        //
        params.put("_start", start);
        params.put("_pageSize", pageSize);
    }



    /**
     * 将 Map 的值设置给Bean
     * @param map
     * @param clazz
     * @return
     */
    public static Object map2Bean(Map<String, ? extends Object> map, Class<?> clazz){
        if(null == clazz || clazz.isArray()){
            return null;
        }
        Object bean = null;
        try {
            bean = clazz.newInstance();
            map2Bean(map, bean);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bean;
    }
    /**
     * 将Map封装为bean
     * @param paramMap
     * @param target
     * @return
     */
    public static void map2Bean(Map<String, ? extends Object> paramMap, Object target){
        if(null == paramMap || null == target || paramMap.isEmpty()){
            return;
        }
        if(target instanceof Map<?,?> || target instanceof List<?>){
            return; // 不处理 Map,List 以及。。。
        }
        Class<?> targetClazz = target.getClass();
        // 依赖 Spring 的 BeanUtils
        PropertyDescriptor[] targetPds = org.springframework.beans.BeanUtils.getPropertyDescriptors(targetClazz);
        //
        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (null == writeMethod) { continue; }
            //
            Class<?>[] pClazz = writeMethod.getParameterTypes();
            if(null == pClazz || pClazz.length != 1){
                // 如果不是只有1个参数
                continue;
            }
            // 获取KEY和Value
            String keyName = targetPd.getName();
            Object value = paramMap.get(keyName);
            // 对比类型,如果类型不同,则进行解析转换, 主要是String转换
            value = tran2TargetType(value, pClazz[0]);
            if(null == value){ continue;}

            try {
                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                    writeMethod.setAccessible(true);
                }
                // 执行 set 方法
                writeMethod.invoke(target, value);
            } catch (Throwable e) {
                throw  new RuntimeException(e);
            }

        }
    }

	/**
	 * 解析request中的参数Map
	 * @param request
	 * @return
	 */
	public static Map<String, Object> parseParamMapObject(HttpServletRequest request){
		//
		Map<String, String> map = parseParamMap(request);
		Map<String, Object> map2 = new HashMap<String, Object>(map);
		//
		return map2;
	}

	public static Map<String, String> parseParamMap(HttpServletRequest request, boolean empty2null){
		//
		Map<String, String> map = new HashMap<String, String>();
		//
		if(null != request){
			Enumeration<String> enumeration = request.getParameterNames();
			// 遍历参数,其实有request的request.getParameterMap();但没泛型
			while (enumeration.hasMoreElements()) {
				String paraName = (String) enumeration.nextElement();
				//
				String paraValue = request.getParameter(paraName);
				//
				if(null != paraValue){
					paraValue = paraValue.trim();
				}
				if("".equals(paraValue) || "null".equals(paraValue) || "undefined".equals(paraValue)){
					paraValue = "";
				}
				if(empty2null && StringNumberUtil.isEmpty(paraValue)){
					// 不设置值
				} else {
					map.put(paraName, paraValue);
				}
			}
		}
		//
		return map;
	}
	/**
	 * 解析request中的参数Map
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected static Map<String, String> parseParamMap(HttpServletRequest request){
		Map<String, String> map = parseParamMap(request, false);
		return map;
	}
}

