package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

/**
 * 通知信息模板对象
 * 数据库的ORM对象，与DTO对象有区别，不包含其他表数据信息
 * 
 * @author zhangbo	2015年9月6日
 *
 */
public class NoticeMsgTemplate implements Serializable{
	
	/**
	 * 序列号 
	 * 
	 * @author zhangbo	2015年9月6日
	 */
	private static final long serialVersionUID = 62431961092425894L;

	/**
	 * 通知信息模板表主键id
	 * 
	 * @author zhangbo	2015年9月6日
	 */
	private Integer id;
	
	/**
	 * 通知信息模板内容
	 * 
	 * @author zhangbo	2015年9月6日
	 */
	private String contentTmpl;
	
	/**
	 * 操作者，存储为管理员账号id
	 * 
	 * @author zhangbo	2015年9月6日
	 */
	private Integer operator;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the contentTmpl
	 */
	public String getContentTmpl() {
		return contentTmpl;
	}

	/**
	 * @param contentTmpl the contentTmpl to set
	 */
	public void setContentTmpl(String contentTmpl) {
		this.contentTmpl = contentTmpl;
	}

	/**
	 * @return the operator
	 */
	public Integer getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(Integer operator) {
		this.operator = operator;
	}

}
