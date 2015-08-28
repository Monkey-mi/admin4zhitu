package com.imzhitu.admin.common.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.base.constant.Tag;

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
	private Integer superbNotified; //加精通知，0为没有通知，1为已经通知
	
	private String channelName;
	private String channelTitle;
	private String channelIcon;
	private Integer beSchedula;				//是否被计划
	private Integer schedulaComplete;		//计划完成情况
	
	private Integer isCover = Tag.FALSE;

	// 存在于多个频道
	private List<String> multiple = new ArrayList<String>();
	
	private Integer channelWorldSchedulaSuperb;	// 计划中加精，0为不加精，1为加精
	
	public Integer getBeSchedula() {
		return beSchedula;
	}

	public void setBeSchedula(Integer beSchedula) {
		this.beSchedula = beSchedula;
	}

	public Integer getSchedulaComplete() {
		return schedulaComplete;
	}

	public void setSchedulaComplete(Integer schedulaComplete) {
		this.schedulaComplete = schedulaComplete;
	}

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
	
	public Integer getSuperbNotified() {
		return superbNotified;
	}

	public void setSuperbNotified(Integer superbNotified) {
		this.superbNotified = superbNotified;
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

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelTitle() {
		return channelTitle;
	}

	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}

	public String getChannelIcon() {
		return channelIcon;
	}

	public void setChannelIcon(String channelIcon) {
		this.channelIcon = channelIcon;
	}

	public Integer getIsCover() {
		return isCover;
	}

	public void setIsCover(Integer isCover) {
		this.isCover = isCover;
	}

	public List<String> getMultiple() {
		return multiple;
	}

	public void setMultiple(List<String> multiple) {
		this.multiple = multiple;
	}

	/**
	 * @return the channelWorldSchedulaSuperb
	 */
	public Integer getChannelWorldSchedulaSuperb() {
	    return channelWorldSchedulaSuperb;
	}

	/**
	 * @param channelWorldSchedulaSuperb the channelWorldSchedulaSuperb to set
	 */
	public void setChannelWorldSchedulaSuperb(Integer channelWorldSchedulaSuperb) {
	    this.channelWorldSchedulaSuperb = channelWorldSchedulaSuperb;
	}

}
