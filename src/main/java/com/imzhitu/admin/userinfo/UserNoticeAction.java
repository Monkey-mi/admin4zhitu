package com.imzhitu.admin.userinfo;

import com.hts.web.base.StrutsKey;
import com.imzhitu.admin.common.BaseCRUDAction;

public class UserNoticeAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5934958542656096002L;
	
	/**
	 * 查询公告列表
	 * 
	 * @return
	 */
	public String queryNotice() {
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新公告
	 * 
	 * @return
	 */
	public String updateNotice() {
		return StrutsKey.JSON;
	}

	/**
	 * 删除公告
	 * 
	 * @return
	 */
	public String deleteNotice() {
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据手机代号查询公告
	 * 
	 * @return
	 */
	public String queryNoticeByPhoneCode() {
		return StrutsKey.JSON;
	}
}
