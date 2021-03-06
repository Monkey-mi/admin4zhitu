package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.pojo.ObjectWithUserVerify;

/**
 * <p>
 * 评论管理数据访问对象
 * </p>
 * 
 * 创建时间：2013-8-9
 * 
 * @author ztj
 * 
 */
public class ZTWorldCommentDto extends AbstractNumberDto implements Serializable, ObjectWithUserVerify {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1946668579751972023L;

	private Integer id;
	private Integer authorId; // 作者id
	private String authorName; // 作者名称
	private String authorAvatar; // 作者头像
	private Integer star = Tag.FALSE; // 明星标记
	private String verifyName;
	private String verifyIcon;
	private Integer trust; // 信任标记
	private String content; // 内容
	private Date commentDate; // 评论时间
	private Integer worldId; // 世界ID
	private Integer worldAuthorId; // 织图作者id
	private Integer reId; // 被回复评论id
	private Integer reAuthorId; // 被回复作者id

	public ZTWorldCommentDto() {
		super();
	}

	public ZTWorldCommentDto(Integer id, Integer authorId, String authorName,
			String authorAvatar, String content, Date commentDate,
			Integer worldId, Integer worldAuthorId, Integer reId, Integer reAuthorId) {
		super();
		this.id = id;
		this.authorId = authorId;
		this.authorName = authorName;
		this.authorAvatar = authorAvatar;
		this.content = content;
		this.commentDate = commentDate;
		this.worldId = worldId;
		this.worldAuthorId = worldAuthorId;
		this.reId = reId;
		this.reAuthorId = reAuthorId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorAvatar() {
		return authorAvatar;
	}

	public void setAuthorAvatar(String authorAvatar) {
		this.authorAvatar = authorAvatar;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}
	
	public String getVerifyName() {
		return verifyName;
	}

	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	public String getVerifyIcon() {
		return verifyIcon;
	}

	public void setVerifyIcon(String verifyIcon) {
		this.verifyIcon = verifyIcon;
	}

	public Integer getTrust() {
		return trust;
	}

	public void setTrust(Integer trust) {
		this.trust = trust;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getReId() {
		return reId;
	}

	public void setReId(Integer reId) {
		this.reId = reId;
	}

	public Integer getReAuthorId() {
		return reAuthorId;
	}

	public void setReAuthorId(Integer reAuthorId) {
		this.reAuthorId = reAuthorId;
	}


	@Override
	public Integer getVerifyId() {
		return star;
	}

	public Integer getWorldAuthorId() {
		return worldAuthorId;
	}

	public void setWorldAuthorId(Integer worldAuthorId) {
		this.worldAuthorId = worldAuthorId;
	}
	
}
