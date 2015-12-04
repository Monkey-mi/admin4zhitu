package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 *  附近城市组
 * @author zxx 2015-12-4 16:17:23
 *
 */
public class OpNearCityGroupDto extends AbstractNumberDto{

	private static final long serialVersionUID = 7670147681475010933L;
	
	private Integer id;
	private String description;
	private Integer serial;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}

}
