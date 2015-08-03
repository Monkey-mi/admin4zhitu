package com.imzhitu.admin.op;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.op.service.ChatService;

/**
 * @author zhangbo 2015年8月3日
 *
 */
public class OpChatAction extends BaseCRUDAction {

	/**
	 * 序列号
	 * 
	 * @author zhangbo 2015年8月3日
	 */
	private static final long serialVersionUID = -6531096030241287976L;

	@Autowired
	private ChatService chatService;
	
	/**
	 * 骚扰私聊主键id集合
	 * 
	 * @author zhangbo	2015年8月3日
	 */
	private String ids;

	/**
	 * @param ids the ids to set
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 查询骚扰私聊
	 * 
	 * @return
	 * @author zhangbo 2015年8月3日
	 */
	public String queryChatList() {
		try {
			chatService.buildChatList(page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String deleteChats() {
		try {
			Integer[] id = StringUtil.convertStringToIds(ids);
			chatService.deleteChats(id);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

}
