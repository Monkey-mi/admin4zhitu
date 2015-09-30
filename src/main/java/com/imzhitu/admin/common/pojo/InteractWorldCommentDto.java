package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;

public class InteractWorldCommentDto  extends AbstractNumberDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4274146467483527599L;

	private Integer id;
	private Integer interactId;
	private Integer worldId; // 织图id
	private Integer userId;
	private String userName;
	private String userAvatar;
	private Integer sex = Tag.SEX_UNKNOWN;
	private Integer commentId;
	private Date dateAdded;
	private Date dateSchedule;
	private Integer valid;
	private Integer finished;
	
	private Integer commentValid;
	private String content;

	public InteractWorldCommentDto() {
		super();
	}
	
	public InteractWorldCommentDto(Integer id, Integer interactId,
			Integer worldId, Integer userId, Integer commentId, Date dateAdded,
			Date dateSchedule, Integer valid, Integer finished, Integer commentValid, 
			String content, String userName, String userAvatar, Integer sex) {
		this.id = id;
		this.interactId = interactId;
		this.worldId = worldId;
		this.userId = userId;
		this.commentId = commentId;
		this.dateAdded = dateAdded;
		this.dateSchedule = dateSchedule;
		this.valid = valid;
		this.finished = finished;
		this.commentValid = commentValid;
		this.content = content;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.sex = sex;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInteractId() {
		return interactId;
	}

	public void setInteractId(Integer interactId) {
		this.interactId = interactId;
	}
	
	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getDateSchedule() {
		return dateSchedule;
	}

	public void setDateSchedule(Date dateSchedule) {
		this.dateSchedule = dateSchedule;
	}
	

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getFinished() {
		return finished;
	}

	public void setFinished(Integer finished) {
		this.finished = finished;
	}

	public Integer getCommentValid() {
		return commentValid;
	}

	public void setCommentValid(Integer commentValid) {
		this.commentValid = commentValid;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userAvatar
	 */
	public String getUserAvatar() {
		return userAvatar;
	}

	/**
	 * @param userAvatar the userAvatar to set
	 */
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	/**
	 * @return the sex
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	
	
}
