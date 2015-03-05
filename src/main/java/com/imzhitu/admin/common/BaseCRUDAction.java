package com.imzhitu.admin.common;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.imzhitu.admin.common.pojo.AdminUserDetails;

/**
 * <p>
 * 系统管理（增、删、改、查）基础类，配合easyui一起使用
 * </p>
 * 
 * 创建时间：2012-11-06
 * 
 * @author ztj
 * 
 */
public class BaseCRUDAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8268409095434852061L;
	/**
	 * 
	 */
	protected Map<String, Object> attrMap = new LinkedHashMap<String, Object>();
	protected Integer page = 1;
	protected Integer rows = 10;
	protected String sort = "id";
	protected String order = "desc";
	
	/**
	 * 获取当前登录用户id
	 * 
	 * @return
	 */
	protected Integer getCurrentLoginUserId() {
		Integer uid = -1;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			uid = ((AdminUserDetails)auth.getPrincipal()).getId();
		}
		return uid;
	}
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}
