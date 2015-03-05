package com.imzhitu.admin.common;


/**
 * <p>
 * 有互动用户信息标记接口
 * </p>
 * 
 * 创建时间：2014-2-20
 * @author tianjie
 *
 */
public interface UserWithInteract {

	/**
	 * 获取用户id
	 * @return
	 */
	public Integer getUserId();
	
	/**
	 * 设置互动标记位
	 * 
	 * @param interacted
	 */
	public void setInteracted(Integer interacted);
}
