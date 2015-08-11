package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * <p>
 * 织图分类POJO
 * </p>
 * 
 * 创建时间: 2015-08-11
 * @author lynch
 *
 */
public class ZTWorldType extends AbstractNumberDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4221661205869874455L;
	private Integer id;
	private String typeName;
	private String typePinyin;
	private String typeDesc;
	private Integer valid;
	private Integer serial;

	public ZTWorldType() {
		super();
	}

	public ZTWorldType(Integer id, String typeName, String typePinyin,
			String typeDesc, Integer valid, Integer serial) {
		super();
		this.id = id;
		this.typeName = typeName;
		this.typePinyin = typePinyin;
		this.typeDesc = typeDesc;
		this.valid = valid;
		this.serial = serial;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypePinyin() {
		return typePinyin;
	}

	public void setTypePinyin(String typePinyin) {
		this.typePinyin = typePinyin;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}
}
