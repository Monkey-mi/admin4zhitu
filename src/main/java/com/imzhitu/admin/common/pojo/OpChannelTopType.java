package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * <p>
 * 频道top-one类型POJO
 * </p>
 * 
 * 创建时间:2014-11-03
 * 
 * @author lynch
 *
 */
public class OpChannelTopType extends AbstractNumberDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7563815184988636686L;

	private Integer id;
	private String topTitle;
	private String topSubTitle;
	private String topDesc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTopDesc() {
		return topDesc;
	}

	public void setTopDesc(String topDesc) {
		this.topDesc = topDesc;
	}
	
	public String getTopTitle() {
		return topTitle;
	}

	public void setTopTitle(String topTitle) {
		this.topTitle = topTitle;
	}

	public String getTopSubTitle() {
		return topSubTitle;
	}

	public void setTopSubTitle(String topSubTitle) {
		this.topSubTitle = topSubTitle;
	}

}
