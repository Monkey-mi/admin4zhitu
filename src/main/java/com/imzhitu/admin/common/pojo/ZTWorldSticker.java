package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * <p>
 * 贴纸POJO
 * </p>
 * 
 * 创建时间:2014-12-26
 * 
 * @author lynch
 *
 */
public class ZTWorldSticker extends AbstractNumberDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8253835480403941405L;

	private Integer id;
	private Integer typeId;
	private Integer setId;
	private String stickerPath;
	private String stickerThumbPath;
	private String stickerDemoPath;
	private String stickerName;
	private String stickerDesc;
	private Integer topWeight;
	private Integer weight;
	private Integer serial;
	private Integer valid;
	private Integer hasLock;
	private Integer labelId;
	private Integer fill;
	private String typeName;
	private String setName;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getStickerPath() {
		return stickerPath;
	}

	public void setStickerPath(String stickerPath) {
		this.stickerPath = stickerPath;
	}

	public String getStickerThumbPath() {
		return stickerThumbPath;
	}

	public void setStickerThumbPath(String stickerThumbPath) {
		this.stickerThumbPath = stickerThumbPath;
	}
	
	public String getStickerName() {
		return stickerName;
	}

	public void setStickerName(String stickerName) {
		this.stickerName = stickerName;
	}

	public String getStickerDesc() {
		return stickerDesc;
	}

	public void setStickerDesc(String stickerDesc) {
		this.stickerDesc = stickerDesc;
	}

	public Integer getTopWeight() {
		return topWeight;
	}

	public void setTopWeight(Integer topWeight) {
		this.topWeight = topWeight;
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

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getStickerDemoPath() {
		return stickerDemoPath;
	}

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}
	
	public Integer getFill() {
		return fill;
	}

	public void setFill(Integer fill) {
		this.fill = fill;
	}

	public void setStickerDemoPath(String stickerDemoPath) {
		this.stickerDemoPath = stickerDemoPath;
	}

	public Integer getHasLock() {
		return hasLock;
	}

	public void setHasLock(Integer hasLock) {
		this.hasLock = hasLock;
	}

	public Integer getSetId() {
		return setId;
	}

	public void setSetId(Integer setId) {
		this.setId = setId;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}
	
}
