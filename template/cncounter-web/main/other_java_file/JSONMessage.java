package com.cncounter.cncounter.mvc.msg;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * MVC层与前端的AJAX交互, 如果有不同的消息类型需求,可以继承此类,也可以重新编写,但应尽可能与此类在同一个包内.
 */
public class JSONMessage {
	/**
	 * 成功状态，值为1
	 */
	public static final int STATUS_SUCCESS = 1;
	/**
	 * 失败状态, 代表有错误发生, 值为0
	 */
	public static final int STATUS_FAILURE = 0;

	/**
	 * 为了兼容表格的记录总数, 需要用户手动设置
	 */
	private int total = 0;
	/**
	 * 数据、类型为List,JS的Array
	 */
	private List<?> data = new ArrayList<Object>();
	/**
	 * 元数据、额外信息、可以存储任意类型的映射 <br/>
	 * 如,分页数据、总条数等等.
	 */
	private Map<String, Object> meta = new HashMap<String, Object>();

	/**
	 * 是否成功,默认0为不成功,1为成功
	 */
	private int status = STATUS_FAILURE;
	/**
	 * 错误码, 如果执行成功（status=1）,则应该忽略此字段<br/>
	 * 建议使用4位数字,如果需求很特殊,也可以使用其他字符串. 默认0000 表示没错误.
	 */
	private String errorcode = "0000";
	/**
	 * 提示信息,如果需要则提供
	 */
	private String info = "";
	/**
	 * 特殊字段,如果需要通知客户端执行某些操作,可以设置此字段,如要求重新登录,重定向URL地址等<br/>
	 * 大多数情况下,客户端应该先检查 status再决定是否解析此字段.
	 */
	private String clientaction = CLIENT_ACTION.EMPTY;
	/**
	 * 与 clientaction 配套, 如果需要简单的 clientaction 操作,则客户端可使用此值进行操作.
	 */
	private String actionvalue = "";
	
	/**
	 * 新创建一个空消息对象
	 * @return
	 */
	public static JSONMessage newMessage(){
		return new JSONMessage();
	}
	public static JSONMessage successMessage(){
		JSONMessage jsonMessage = new JSONMessage();
		jsonMessage.setSuccess();
		return jsonMessage;
	}
	public static JSONMessage failureMessage(){
		JSONMessage jsonMessage = new JSONMessage();
		jsonMessage.setFailure();
		return jsonMessage;
	}
	
	public static final class CLIENT_ACTION {
		/**
		 * 表示无特殊消息,OK
		 */
		public static final String OK = "OK";
		/**
		 * 表示需要重新登录、
		 */
		public static final String LOGIN = "LOGIN";
		/**
		 * 客户端需要重定向、将浏览器地址替换
		 */
		public static final String REDIRECT = "REDIRECT";
		/**
		 * 默认是空action
		 */
		public static final String EMPTY = "";
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		if(null == data){
			return;
		}
		this.data = data;
	}

	public Map<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(Map<String, Object> meta) {
		if(null == meta){
			return;
		}
		this.meta = meta;
	}
	/**
	 * 添加 meta信息,
	 */
	public void addMeta( String key, Object value) {
		if(null == meta){
			// 非线程安全, 不考虑使用多线程操作 
			this.meta = new HashMap<String, Object>();
		}
		//
		this.meta.put(key, value);
	}

	public int getStatus() {
		return status;
	}

	/**
	 * 设置执行成功
	 */
	public void setSuccess(){
		this.setStatus(STATUS_SUCCESS);
	}
	public void setFailure(){
		this.setStatus(STATUS_FAILURE);
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getClientaction() {
		return clientaction;
	}

	public void setClientaction(String clientaction) {
		this.clientaction = clientaction;
	}

	public String getActionvalue() {
		return actionvalue;
	}

	public void setActionvalue(String actionvalue) {
		this.actionvalue = actionvalue;
	}
	
}
