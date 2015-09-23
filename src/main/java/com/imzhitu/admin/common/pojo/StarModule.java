package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;


/**
 * <p>
 * 系统角色信息POJO
 * </p>
 * 
 * 创建时间：2013-2-17
 * 
 * @author ztj
 * 
 */
public class StarModule extends AbstractNumberDto{


	/**
	 * 
	 */
	private static final long serialVersionUID = -8989404256705818104L;

	private Integer  id;
	private String  title1;
	private String  title2;
	private Integer userId;
	private String pics;
	private String intro;
	private Integer topicId;
	
	
	public Integer getTopicId() {
		return topicId;
	}
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle1() {
		return title1;
	}
	public void setTitle1(String title1) {
		this.title1 = title1;
	}
	public String getTitle2() {
		return title2;
	}
	public void setTitle2(String title2) {
		this.title2 = title2;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getPics() {
		return pics;
	}
	public void setPics(String pics) {
		this.pics = pics;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	@Override
	public String toString() {
		return "StarModule [id=" + id + ", title1=" + title1 + ", title2=" + title2 + ", userId=" + userId + ", pics="
				+ pics + ", intro=" + intro + ", topicId=" + topicId + "]";
	}
	
	
}
