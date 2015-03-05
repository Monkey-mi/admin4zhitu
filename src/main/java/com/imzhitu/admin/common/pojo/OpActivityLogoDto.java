package com.imzhitu.admin.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 活动LOGO数据传输对象
 * </p>
 * 
 * 创建时间：2014-4-9
 * @author tianjie
 *
 */
public class OpActivityLogoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7984184496761633902L;
	private Integer id;
	private String logoPath;
	private Integer activityId;
	private String titlePath;
	private String titleThumbPath;
	private String activityName;
	private String activityTitle;
	private String activityDesc;
	private String activityLink;
	private String activityLogo;
	private Date activityDate;
	private Integer objType;
	private Integer objId;
	private Integer commercial;
	private Integer serial;
	private Integer valid;

	public OpActivityLogoDto() {
		super();
	}

	public OpActivityLogoDto(Integer id, String logoPath, Integer activityId, String titlePath,
			String titleThumbPath, String activityName, String activityTitle,
			String activityDesc, String activityLink, String activityLogo,
			Date activityDate, Integer objType, Integer objId,
			Integer commercial, Integer serial, Integer valid) {
		super();
		this.id = id;
		this.logoPath = logoPath;
		this.activityId = activityId;
		this.titlePath = titlePath;
		this.titleThumbPath = titleThumbPath;
		this.activityName = activityName;
		this.activityTitle = activityTitle;
		this.activityDesc = activityDesc;
		this.activityLink = activityLink;
		this.activityLogo = activityLogo;
		this.activityDate = activityDate;
		this.objType = objType;
		this.objId = objId;
		this.commercial = commercial;
		this.serial = serial;
		this.valid = valid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getTitlePath() {
		return titlePath;
	}

	public void setTitlePath(String titlePath) {
		this.titlePath = titlePath;
	}

	public String getTitleThumbPath() {
		return titleThumbPath;
	}

	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityTitle() {
		return activityTitle;
	}

	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public String getActivityLink() {
		return activityLink;
	}

	public void setActivityLink(String activityLink) {
		this.activityLink = activityLink;
	}

	public String getActivityLogo() {
		return activityLogo;
	}

	public void setActivityLogo(String activityLogo) {
		this.activityLogo = activityLogo;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public Integer getObjType() {
		return objType;
	}

	public void setObjType(Integer objType) {
		this.objType = objType;
	}

	public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public Integer getCommercial() {
		return commercial;
	}

	public void setCommercial(Integer commercial) {
		this.commercial = commercial;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
}
