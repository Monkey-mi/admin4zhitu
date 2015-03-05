package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

public class OpXiaoMiShuResponse extends AbstractNumberDto{
	private static final long serialVersionUID = 7552460923435993353L;
	private Integer responseId;		//小秘书回复id
	private String content;			//回复内容
	private Integer  keyId;			//键值id
	private String module;			//模块，没有实际用处，只在于分类查询
	private String key;				//键值
	private Integer keyLength;		//键值长度
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
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getKeyLength() {
		return keyLength;
	}
	public void setKeyLength(Integer keyLength) {
		this.keyLength = keyLength;
	}
	
	
}
