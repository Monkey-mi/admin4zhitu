package com.imzhitu.admin.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 控制器基类，提取控制器公用方法
 * 
 * @author ztj
 *
 */
public abstract class BaseAction extends ActionSupport {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4080138304604885220L;
	
	protected Integer start = 1;
	protected Integer limit = 10;
	protected Integer sinceId = 0;
	protected Integer maxId = 0;
	protected Integer minId = 0;
	protected Integer maxSerial = 0;
	protected Boolean trimTotal = true; // 过滤总数标记位
	protected Boolean trimExtras = true; // 过滤附加信息标记位
	protected Integer commentLimit = 3;
	protected Integer likedLimit = 12;
	
	/**
	 * 返回的结果集KEY值
	 */
	protected static final String RES = "res";
	
	protected Map<String, Object> jsonMap = new HashMap<String, Object>();
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> session;
	protected ServletContext servletContext;

	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}

	public BaseAction() {
		super();
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = ActionContext.getContext().getSession();
		servletContext = ServletActionContext.getServletContext();
		response.setContentType("text/plain;charset=utf-8");
	}
	
	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getSinceId() {
		return sinceId;
	}

	public void setSinceId(Integer sinceId) {
		this.sinceId = sinceId;
	}

	public Integer getMaxId() {
		return maxId;
	}

	public void setMaxId(Integer maxId) {
		this.maxId = maxId;
	}

	public Integer getMinId() {
		return minId;
	}

	public void setMinId(Integer minId) {
		this.minId = minId;
	}

	public Integer getMaxSerial() {
		return maxSerial;
	}

	public void setMaxSerial(Integer maxSerial) {
		this.maxSerial = maxSerial;
	}

	public Boolean getTrimTotal() {
		return trimTotal;
	}

	public void setTrimTotal(Boolean trimTotal) {
		this.trimTotal = trimTotal;
	}

	public Boolean getTrimExtras() {
		return trimExtras;
	}

	public void setTrimExtras(Boolean trimExtras) {
		this.trimExtras = trimExtras;
	}

	public Integer getCommentLimit() {
		return commentLimit;
	}

	public void setCommentLimit(Integer commentLimit) {
		this.commentLimit = commentLimit;
	}

	public Integer getLikedLimit() {
		return likedLimit;
	}

	public void setLikedLimit(Integer likedLimit) {
		this.likedLimit = likedLimit;
	}
	
	
	
}
