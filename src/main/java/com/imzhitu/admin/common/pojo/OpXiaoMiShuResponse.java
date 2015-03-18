package com.imzhitu.admin.common.pojo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.hts.web.common.pojo.AbstractNumberDto;

public class OpXiaoMiShuResponse extends AbstractNumberDto{
	private static final long serialVersionUID = 7552460923435993353L;
	private Integer responseId;		//小秘书回复id,op_xiaomishu_response表的id，同时也是op_xiaomishu_response_key的response_id
	private String content;			//回复内容
	private Integer  keyId;			//键值id
	private String moduleName;		//模块，没有实际用处，只在于分类查询
	private Integer moduleId;		//模块id,op_xiaomishu_response_module的id,同时也是op_xiaomishu_response_key的module_id 
	private String key;				//键值
	private Integer keyValid;		//键值有效性
	private Integer moduleValid;	//模块的有效性
	private Integer operatorId;		//最后操作者id
	private String operatorName;	//最后操作者名字
	private Date modifyDate;		//最后操作时间
	public Integer getResponseId() {
		return responseId;
	}
	public void setResponseId(Integer responseId) {
		this.responseId = responseId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getKeyId() {
		return keyId;
	}
	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getKeyValid() {
		return keyValid;
	}
	public void setKeyValid(Integer keyValid) {
		this.keyValid = keyValid;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public Integer getModuleValid() {
		return moduleValid;
	}
	public void setModuleValid(Integer moduleValid) {
		this.moduleValid = moduleValid;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	
	
}
