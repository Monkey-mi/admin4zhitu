package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class OpChannelWorldDto extends ZTWorldBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6359000393993868716L;

	private Integer channelWorldId;
	private Integer channelId;
	private Integer worldId;
	private Integer authorId;
	private Date channelWorldDateAdded;
	private Integer channelWorldValid;
	private Integer notified;
	private Integer weight;	// 权重，即置顶，0为不置顶，1为置顶
	private Integer superb;	// 加精，0为不加精，1为加精
	
	/**
	 * 有效性计划完成情况
	 * 1、null：未做有效性计划，2、0：做了有效性计划，计划未完成，3：1：做了有效性计划，计划已经完成
	 * @author zhangbo	2015年9月15日
	 */
	private Integer validSchedula;
	
	/**
	 * 加精计划完成情况
	 * 1、null：未做加精计划，2、0：做了加精计划，计划未完成，3：1：做了加精计划，计划已经完成
	 * @author zhangbo	2015年9月15日
	 */
	private Integer superbSchedula;

	/**
	 * 织图存在于多个频道
	 * @author zhangbo	2015年11月2日
	 */
	private String multiple;
	
	public Integer getChannelWorldId() {
		return channelWorldId;
	}

	public void setChannelWorldId(Integer channelWorldId) {
		this.channelWorldId = channelWorldId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getChannelWorldValid() {
		return channelWorldValid;
	}

	public void setChannelWorldValid(Integer channelWorldValid) {
		this.channelWorldValid = channelWorldValid;
	}

	public Integer getNotified() {
		return notified;
	}

	public void setNotified(Integer notified) {
		this.notified = notified;
	}

	/**
	 * @return the weight
	 */
	public Integer getWeight() {
	    return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Integer weight) {
	    this.weight = weight;
	}

	/**
	 * @return the superb
	 */
	public Integer getSuperb() {
	    return superb;
	}

	/**
	 * @param superb the superb to set
	 */
	public void setSuperb(Integer superb) {
	    this.superb = superb;
	}
	
	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getChannelWorldDateAdded() {
		return channelWorldDateAdded;
	}

	public void setChannelWorldDateAdded(Date channelWorldDateAdded) {
		this.channelWorldDateAdded = channelWorldDateAdded;
	}

	public Integer getValidSchedula() {
		return validSchedula;
	}

	public void setValidSchedula(Integer validSchedula) {
		this.validSchedula = validSchedula;
	}

	public Integer getSuperbSchedula() {
		return superbSchedula;
	}

	public void setSuperbSchedula(Integer superbSchedula) {
		this.superbSchedula = superbSchedula;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

}
