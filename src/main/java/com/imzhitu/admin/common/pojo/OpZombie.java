package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * 马甲等级
 * @author zxx
 *
 */
public class OpZombie extends AbstractNumberDto{
	private static final long serialVersionUID = 7910265555721320924L;
	private Integer id;
	private Integer userId;
	private String userName;
	private Integer degreeId;
	private String degreeName;
	private Integer commentCount;
	private Integer concernCount;
	private Long lastModify;		//最后修改时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getDegreeId() {
		return degreeId;
	}
	public void setDegreeId(Integer degreeId) {
		this.degreeId = degreeId;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public Integer getConcernCount() {
		return concernCount;
	}
	public void setConcernCount(Integer concernCount) {
		this.concernCount = concernCount;
	}
	public Long getLastModify() {
		return lastModify;
	}
	public void setLastModify(Long lastModify) {
		this.lastModify = lastModify;
	}
	public String getDegreeName() {
		return degreeName;
	}
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
