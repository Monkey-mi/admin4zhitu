package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

/**
 * <p>
 * 操作日志POJO
 * </p>
 * 
 * 创建时间：2014-08-06
 * 
 * @author tianjie
 * 
 */
public class LoggerOperation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 63314631780787756L;
	private Integer id;
	private String optInterface;
	private String optName;
	private String optDesc;
	private Integer serial;

	public LoggerOperation() {
		super();
	}
	
	public LoggerOperation(Integer id, String optInterface, String optName, 
			String optDesc, Integer serial) {
		super();
		this.id = id;
		this.optInterface = optInterface;
		this.optName = optName;
		this.optDesc = optDesc;
		this.serial = serial;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOptInterface() {
		return optInterface;
	}

	public void setOptInterface(String optInterface) {
		this.optInterface = optInterface;
	}

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public String getOptDesc() {
		return optDesc;
	}

	public void setOptDesc(String optDesc) {
		this.optDesc = optDesc;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	
	
	
}
