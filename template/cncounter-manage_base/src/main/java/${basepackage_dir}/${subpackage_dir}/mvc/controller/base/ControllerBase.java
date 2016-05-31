package ${basepackage}.${subpackage}.mvc.controller.base;


import ${basepackage}.${subpackage}.dao.redis.api.RedisBaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;


/**
 * Spring MVC控制器的基类, 对Session访问提供统一方法,<br/>
 * 子类应该使用基类提供的方法,以方便今后的集群部署[届时只需要修改此类中的实现即可]。
 */
public abstract class ControllerBase {
	/**
	 * 会话中存储user信息的KEY
	 */
	public static final String SESSION_USER_KEY = "session_user_key";
	public static final String UTF_8 = "UTF-8";

    protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private RedisBaseDAO redisBaseDAO;

	/**
	 * 获取基于sessionid的key
	 * @param request
	 * @param oKey
	 * @return
	 */
	public static String getSessionKey(HttpServletRequest request, String oKey){
		// 获取会话?
		HttpSession session = request.getSession(true);
		// 获取会话ID
		String sessionid = session.getId();
		//
		String nKey = "sessionid:"+ sessionid +":"+oKey;
		//
		return nKey;
	}
	/**
	 * 获取UUID的key
	 * @param uuid
	 * @return
	 */
	public static String getUUIDKey(String uuid){
		String nKey = "uuid:"+uuid;
		return nKey;
	}
	/**
	 * 获取UUID
	 * @return
	 */
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}
	
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
	 * 保存到缓存. 使用Redis实现
	 * @param request 使用是为了使用app缓存的方式
	 * @param name
	 * @param value
	 */
	public void saveToCache(HttpServletRequest request, String name, Serializable value) {
		// 基于单容器的实现
		// ServletContext application = request.getSession().getServletContext();
		// application.setAttribute(name, value);
		// 基于Redis的实现
		saveToCache(name, value);
	}
	public void saveToCache(String name, Serializable value) {
		// 基于单容器的实现
		// ServletContext application = request.getSession().getServletContext();
		// application.setAttribute(name, value);
		// 基于Redis的实现
		redisBaseDAO.saveObject(name, value);
	}
	/**
	 * 从Cache获取对象
	 * @param request 使用是为了使用app缓存的方式
	 * @param name
	 * @return
	 */
	public  Object getFromCache(HttpServletRequest request, String name) {
		// 基于单容器的实现
		// ServletContext application = request.getSession().getServletContext();
		// return application.getAttribute(name);
		// 基于Redis的实现
		return getFromCache(name);
	}
	public  Object getFromCache(String name) {
		// 基于单容器的实现
		// ServletContext application = request.getSession().getServletContext();
		// return application.getAttribute(name);
		// 基于Redis的实现
		return redisBaseDAO.getObject(name);
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
		if(notEmpty(value)){
			return value;
		}
		// 2. 获取 cookie
		value = getCookie(request, name);
		if(notEmpty(value)){
			return value;
		}
		//
		return value;
	}
    /**
     * 不为empty(空)
     * @param str
     * @return
     */
    public static boolean notEmpty(Object str){
        boolean result = false;
        if(null != str && !str.toString().trim().isEmpty()){
            result = true;
        }
        return result;
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
		return parseInt(value, defValue);
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
            pageSize = parseInt(_pageSize.toString(), pageSize);
        }
        if(_page instanceof Integer){
            page = (Integer)_page;
        } else if(_page instanceof String){
            page = parseInt(_page.toString(), page);
        }
        //
        page = page - 1;
		if(page< 0){
			page = 0;
		}
        Integer start = page * pageSize;
        //
        params.put("_start", start);
        params.put("_pageSize", pageSize);
    }

    public static int parseInt(String str, int defValue){
        int result = defValue;
        if(null != str && str.matches("^[\\+\\-]?\\d+$")){
            str = str.replaceAll("^\\+", "");
            result = Integer.parseInt(str);
        }
        return result;
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
            try{
                value = tran2TargetType(value, pClazz[0]);
            } catch (Exception ex){
                // 吃掉异常
                value = null;
            }

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
    public static Object tran2TargetType(Object value, Class<?> pClazz) {
        Object result = null;
        // 为 null
        if(null == value || null == pClazz){
            return  result;
        }
        // 同一类型,不需要转换
        if(pClazz.isInstance(value)){
            result = value;
            return result;
        }
        // 如果值不是为 String
        if(String.class != value.getClass()){
            // 暂时没有转换器, 留 null
            return  result;
        }
        //
        String str = value.toString();
        if(str.isEmpty()){
            if (pClazz == String.class) {
                result = str;
            }
            return  result;// 拦截空字符串
        }
        if (pClazz == int.class || pClazz == Integer.class) {
            result = Integer.parseInt(str);
        } else if (pClazz == Double.class || pClazz == double.class) {
            result = Double.parseDouble(str);
        } else if (pClazz == Float.class  || pClazz == float.class) {
            result = Float.parseFloat(str);
        } else if (pClazz == Long.class || pClazz == long.class) {
            result = Long.parseLong(str);
        } else if (pClazz == Boolean.class  || pClazz == boolean.class) {
            result = Boolean.parseBoolean(str);
        } else if (pClazz == Short.class || pClazz == short.class) {
            result = Short.parseShort(str);
        } else if (pClazz == Date.class) {
            result = parseStrToDate(str);
        } else if (pClazz == String.class) {
            result = str;
        }
        //
        return  result;
    }

    private static Date parseStrToDate(String str) {
        // 需要解析各种格式,或者统一规范
        return null;
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
			Enumeration enumeration = request.getParameterNames();
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
				if(empty2null && !notEmpty(paraValue)){
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

