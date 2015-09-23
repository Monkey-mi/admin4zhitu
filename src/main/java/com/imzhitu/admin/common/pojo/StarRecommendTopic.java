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
public class StarRecommendTopic extends AbstractNumberDto{


	/**
	 * 
	 */
	private static final long serialVersionUID = -8989404256705818104L;

	private Integer  id;
	private String  backgroundColor;
	private String  fileName;
	private String title;
	private String introduceHead;
	private String introduceFoot;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIntroduceHead() {
		return introduceHead;
	}
	public void setIntroduceHead(String introduceHead) {
		this.introduceHead = introduceHead;
	}
	public String getIntroduceFoot() {
		return introduceFoot;
	}
	public void setIntroduceFoot(String introduceFoot) {
		this.introduceFoot = introduceFoot;
	}
	
	@Override
	public String toString() {
		return "StarRecommendTopic [id=" + id + ", backgroundColor=" + backgroundColor + ", fileName=" + fileName
				+ ", title=" + title + ", introduceHead=" + introduceHead + ", introduceFoot=" + introduceFoot + "]";
	}
	
	
}
