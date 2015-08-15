package com.imzhitu.admin.op;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.op.service.OpChannelMemberService;

public class OpChannelMemberAction extends BaseCRUDAction {
	private static final long serialVersionUID = 140236345038477644L;

	@Autowired
	private OpChannelMemberService channelMemberService;

	/**
	 * 被操作的id
	 * 
	 * @author zhangbo 2015年8月14日
	 */
	private Integer id;

	/**
	 * 频道id
	 * 
	 * @author zhangbo 2015年8月14日
	 */
	private Integer channelId;

	/**
	 * 用户id
	 * 
	 * @author zhangbo 2015年8月14日
	 */
	private Integer userId;

	/**
	 * 用户等级
	 * 
	 * @author zhangbo 2015年8月14日
	 */
	private Integer degree;

	/**
	 * 达人认证的id
	 * 
	 * @author zhangbo 2015年8月13日
	 */
	private Integer userStarId;

	/**
	 * 通知标记，已通知：1，未通知：0
	 * 
	 * @author zhangbo 2015年8月14日
	 */
	private Integer notified;
	
	/**
	 * 屏蔽标记，未屏蔽：0，屏蔽：1
	 * 
	 * @author zhangbo	2015年8月15日
	 */
	private Integer shield;

	/**
	 * 被操作的id集合，以","分隔
	 * 
	 * @author zhangbo 2015年8月14日
	 */
	private String ids;

	public void setId(Integer id) {
		this.id = id;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public void setUserStarId(Integer userStarId) {
		this.userStarId = userStarId;
	}

	public void setNotified(Integer notified) {
		this.notified = notified;
	}
	
	public void setShield(Integer shield) {
		this.shield = shield;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 查询频道成员
	 * 
	 * @return
	 * @author zhangbo 2015年8月14日
	 */
	public String queryChannelMember() {
		try {
			// 若用户认证为空则证明，没有根据认证查询，所以用通用查询
			channelMemberService.queryChannelMember(channelId, userId, userStarId, shield, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 通过用户名查找频道成员
	 * 
	 * @return
	 * @author zhangbo 2015年8月14日
	 */
	public String queryChannelMemberByUserName() {
		try {
			channelMemberService.queryChannelMemberByUserName(channelId, userId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

}
