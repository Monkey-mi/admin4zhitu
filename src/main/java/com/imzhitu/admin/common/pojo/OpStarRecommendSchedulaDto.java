package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class OpStarRecommendSchedulaDto extends AbstractNumberDto{

	private static final long serialVersionUID = -3797320954709844038L;
	private Integer  id;
	private Integer userId;
	private Integer operator;
	private String operatorName;
	private Integer osrId;		// operations_star_recommend表id
	private Integer valid;
	private Date schedula;
	private Date addDate;
	private Date modifyDate;
	private Integer top;
	private Integer finish;
	
	public Integer getFinish() {
		return finish;
	}
	public void setFinish(Integer finish) {
		this.finish = finish;
	}
	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public Integer getOsrId() {
		return osrId;
	}
	public void setOsrId(Integer osrId) {
		this.osrId = osrId;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
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
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
}
