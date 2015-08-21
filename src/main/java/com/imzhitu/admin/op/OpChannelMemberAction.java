package com.imzhitu.admin.op;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.OpChannelMemberDTO;
import com.imzhitu.admin.op.service.OpChannelMemberService;

public class OpChannelMemberAction extends BaseCRUDAction {
	private static final long serialVersionUID = 140236345038477644L;

	@Autowired
	@Qualifier("com.imzhitu.admin.op.service.impl.OpChannelMemberServiceImpl")
	private OpChannelMemberService channelMemberService;
	
	/**
	 * 操作的主键id
	 * 
	 * @author zhangbo	2015年8月19日
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
	 * 用户名称
	 * 
	 * @author zhangbo	2015年8月18日
	 */
	private String userName;

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
	
	/**
	 * 计划频道红人排序生效时间 
	 * 
	 * @author zhangbo	2015年8月19日
	 */
	private Date schedulaDate;

	public void setId(Integer id) {
		this.id = id;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	 * 处理从前台传递过来的参数，转化为Date对象，因为前台只传递了时间格式的字符串
	 * 
	 * @param schedulaDate
	 * @throws ParseException
	 * @author zhangbo	2015年8月19日
	 */
	public void setSchedulaDate(String schedulaDate) throws ParseException {
		// 根据业务需要，只要精确到分钟就可以
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		this.schedulaDate = df.parse(schedulaDate);
	}

	/**
	 * 查询频道成员
	 * 
	 * @return
	 * @author zhangbo 2015年8月14日
	 */
	public String queryChannelMember() {
		try {
			channelMemberService.buildChannelMemberList(channelId, userId, userName, userStarId, notified, shield, maxId, page, rows, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 批量设置频道成员成为频道红人
	 * @return
	 * @author zhangbo	2015年8月17日
	 */
	public String addMembersToStar() {
		try {
			Integer[] channelMemberIds = StringUtil.convertStringToIds(ids);
			for (Integer channelMemberId : channelMemberIds) {
				channelMemberService.saveChannelStar(channelMemberId);
			}
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量删除频道红人
	 * 
	 * @return
	 * @author zhangbo	2015年8月17日
	 */
	public String deleteChannelStars() {
		try {
			Integer[] channelMemberIds = StringUtil.convertStringToIds(ids);
			channelMemberService.deleteChannelStars(channelMemberIds);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量推送消息给频道红人，通知其成为频道红人
	 * 
	 * @return
	 * @author zhangbo	2015年8月17日
	 */
	public String addChannelStarsRecommendMsg() {
		try {
			Integer[] channelStarIds = StringUtil.convertStringToIds(ids);
			for (Integer channelStarId : channelStarIds) {
				channelMemberService.addStarRecommendMsg(channelStarId);
			}
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 对频道红人进行重新排序
	 * 
	 * @return
	 * @author zhangbo	2015年8月18日
	 */
	public String sortChannelStarsSchedule() {
		try {
			// 转化从前台来的参数，前台传递的是userId的集合
			String[] userIds = request.getParameterValues("reIndexId");
			
			// 定义频道红人主键id集合
			List<Integer> csIds = new ArrayList<Integer>();
			
			for (String uId : userIds) {
				if ( !uId.isEmpty() ) {
					OpChannelMemberDTO dto = channelMemberService.getChannelStarByChannelIdAndUserId(channelId, Integer.valueOf(uId));
					csIds.add(dto.getChannelMemberId());
				}
			}
			
			channelMemberService.sortChannelStarsSchedule((Integer[])csIds.toArray(new Integer[csIds.size()]), schedulaDate);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 设置频道红人在排序的最新一位
	 * 
	 * @return
	 * @author zhangbo	2015年8月19日
	 */
	public String setChannelStarTop() {
		try {
			channelMemberService.setChannelStarTop(id);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
}
