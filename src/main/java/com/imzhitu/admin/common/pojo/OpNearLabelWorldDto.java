package com.imzhitu.admin.common.pojo;


/**
 *  附近标签织图
 * @author zxx
 *
 */
public class OpNearLabelWorldDto extends ZTWorldDto{

	private static final long serialVersionUID = -5124380453654977751L;
	private Integer nearLabelId;
	private String nearLabelName;
	private Integer serial;
	public Integer getNearLabelId() {
		return nearLabelId;
	}
	public void setNearLabelId(Integer nearLabelId) {
		this.nearLabelId = nearLabelId;
	}
	public String getNearLabelName() {
		return nearLabelName;
	}
	public void setNearLabelName(String nearLabelName) {
		this.nearLabelName = nearLabelName;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	

}
