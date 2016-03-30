package com.cncounter.cncounter.mvc.controller.base;


import com.cncounter.cncounter.dao.redis.api.RedisBaseDAO;
import com.cncounter.cncounter.model.user.User;
import com.cncounter.util.string.StringNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
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
	 * 获取当前登录的用户
	 * @param request
	 * @return
	 */
	public  User getLoginUser(HttpServletRequest request) {
		User user = null;
		Object obj = getSessionAttribute(request, SESSION_USER_KEY);
		if(obj instanceof User){
			user = (User)obj;
		}
		return user;
	}
	
	/**
	 * 在线用户; 弱引用,不影响Session的回收; 
	 */
	private static WeakHashMap<HttpSession, User> onlineUsers = new WeakHashMap<HttpSession, User>(100);
	/**
	 * 获取在线用户数量
	 * @return
	 */
	public static int getOnlineUserCount(){
		if(null != onlineUsers){
			int count = onlineUsers.entrySet().size();
			if(count < 1){
				count = 1;
			}
			return count;
		} else {
			return 1;
		}
	}
	/**
	 * 添加在线用户,主要由 LoginController使用.
	 * @param request
	 * @param user
	 */
	protected static void addOnlineUser(HttpServletRequest request, User user) {
		if(null == request || null == user){
			return;
		}
		//
		if(null != onlineUsers){
			//
			HttpSession session = request.getSession(true);
			// 添加
			onlineUsers.put(session, user);
		}
	}
	/**
	 * 删除在线用户
	 * @param request
	 */
	protected static void removeOnlineUser(HttpServletRequest request) {
		if(null == request){
			return;
		}
		//
		if(null != onlineUsers){
			//
			HttpSession session = request.getSession(true);
			// 添加
			onlineUsers.remove(session);
		}
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

