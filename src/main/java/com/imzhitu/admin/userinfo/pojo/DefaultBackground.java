package com.imzhitu.admin.userinfo.pojo;

import java.io.Serializable;

/**
 * @author zhangbo	2015年12月25日
 *
 */
public class DefaultBackground implements Serializable {

	/**
	 * @author zhangbo	2015年12月25日
	 */
	private static final long serialVersionUID = 5091837072592685337L;
	
	private Integer id;
	
	private String background;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the background
	 */
	public String getBackground() {
		return background;
	}

	/**
	 * @param background the background to set
	 */
	public void setBackground(String background) {
		this.background = background;
	}
	
}
