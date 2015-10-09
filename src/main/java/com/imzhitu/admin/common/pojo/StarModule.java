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
	private String  title;
	private String  subtitle;
	private Integer userId;
	private String pics;
	private String pic02;
	private String pic03;
	private String pic04;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
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
	
	public String getPic02() {
		return pic02;
	}
	public void setPic02(String pic02) {
		this.pic02 = pic02;
	}
	public String getPic03() {
		return pic03;
	}
	public void setPic03(String pic03) {
		this.pic03 = pic03;
	}
	public String getPic04() {
		return pic04;
	}
	public void setPic04(String pic04) {
		this.pic04 = pic04;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	@Override
	public String toString() {
		return "StarModule [id=" + id + ", title=" + title + ", subtitle=" + subtitle + ", userId=" + userId + ", pics="
				+ pics + ", intro=" + intro + ", topicId=" + topicId + "]";
	}
	
	
}
