package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class ZombieWorldSchedulaDto extends AbstractNumberDto{

	private static final long serialVersionUID = 5123777379533729768L;
	
	private Integer id;
	private Integer  zombieWorldId;
	private Date schedula;
	private Date addDate;
	private Integer valid;
	private Integer finished;
	private Integer operator;
	private String operatorName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getZombieWorldId() {
		return zombieWorldId;
	}
	public void setZombieWorldId(Integer zombieWorldId) {
		this.zombieWorldId = zombieWorldId;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getSchedula() {
		return schedula;
	}
	public void setSchedula(Date schedula) {
		this.schedula = schedula;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public Integer getFinished() {
		return finished;
	}
	public void setFinished(Integer finished) {
		this.finished = finished;
	}
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}
