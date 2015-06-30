package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * 贴纸系列POJO
 * 
 * @author lynch
 *
 */
public class ZTWorldStickerSet extends AbstractNumberDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1750428960983341679L;
	private Integer id;
	private String setName;
	private Integer weight;
	private Integer serial;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

}
