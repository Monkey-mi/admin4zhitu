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
	private Integer topicType;
	private Integer isWorld;
	private String shareBanner;
	private String  bannerPic;
	private String title;
	private String introduceHead;
	private String introduceFoot;
	private String stickerButton;
	private String shareButton;
	private String link;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public Integer getTopicType() {
		return topicType;
	}
	public void setTopicType(Integer topicType) {
		this.topicType = topicType;
	}
	public Integer getIsWorld() {
		return isWorld;
	}
	public void setIsWorld(Integer isWorld) {
		this.isWorld = isWorld;
	}
	public String getShareBanner() {
		return shareBanner;
	}
	public void setShareBanner(String shareBanner) {
		this.shareBanner = shareBanner;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getBannerPic() {
		return bannerPic;
	}
	public void setBannerPic(String bannerPic) {
		this.bannerPic = bannerPic;
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
	
	public String getStickerButton() {
		return stickerButton;
	}
	public void setStickerButton(String stickerButton) {
		this.stickerButton = stickerButton;
	}
	
	public String getShareButton() {
		return shareButton;
	}
	public void setShareButton(String shareButton) {
		this.shareButton = shareButton;
	}
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	
}
