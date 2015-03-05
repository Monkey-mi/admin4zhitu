package com.imzhitu.admin.common;

/**
 * <p>
 * 有互动标记织图接口
 * </p>
 * 
 * 创建时间：2014-2-27
 * @author tianjie
 *
 */
public interface WorldWithInteract {

	/**
	 * 获取织图id
	 * @return
	 */
	public Integer getWorldId();
	
	/**
	 * 设置互动标记位
	 * 
	 * @param interacted
	 */
	public void setInteracted(Integer interacted);
}
