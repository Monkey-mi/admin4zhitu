package com.imzhitu.admin.op;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.op.service.OpMsgService;

public class ChannelNoticeConfigAction extends BaseCRUDAction {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1844920769848520753L;
	
	/**
	 * 频道id
	 * 
	 * @author zhangbo	2015年9月7日
	 */
	private Integer channelId;
	
	/**
	 * 频道通知类型，由前台传递过来
	 * 1、add	织图被选入频道的通知
	 * 2、superb	频道织图被选为加精
	 * 3、star	频道成员被选为红人
	 * 
	 * @author zhangbo	2015年9月7日
	 */
	private String channelNoticeType;
	
	/**
	 * 通知信息模板内容
	 * 
	 * @author zhangbo	2015年9月7日
	 */
	private String noticeTpmlContent;
	
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public void setChannelNoticeType(String channelNoticeType) {
		this.channelNoticeType = channelNoticeType;
	}

	public void setNoticeTpmlContent(String noticeTpmlContent) {
		this.noticeTpmlContent = noticeTpmlContent;
	}

	@Autowired
	private OpMsgService opMsgService;

	/**
	 * 根据频道id查询不用类型的通知信息模板内容
	 * 
	 * @return
	 * @author zhangbo	2015年9月7日
	 */
	public String queryNoticeTpmlContentByChannelId() {
		try {
			String noticeType = null;
			if ( channelNoticeType.equals("add") ) {
				noticeType = Admin.NOTICE_WORLD_INTO_CHANNEL;
			} else if ( channelNoticeType.equals("superb") ) {
				noticeType = Admin.NOTICE_CHANNELWORLD_TO_SUPERB;
			} else if ( channelNoticeType.equals("star") ) {
				noticeType = Admin.NOTICE_CHANNELMEMBER_TO_STAR;
			}
			String noticeTemplateContent = opMsgService.getChannelNoticeTemplateByType(channelId, noticeType);
			
			JSONUtil.optSuccess(noticeTemplateContent, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存频道通知信息模板
	 * 
	 * @return
	 * @author zhangbo	2015年9月7日
	 */
	public String saveNoticeTpmlContentByChannelId() {
		try {
			String noticeType = null;
			if ( channelNoticeType.equals("add") ) {
				noticeType = Admin.NOTICE_WORLD_INTO_CHANNEL;
			} else if ( channelNoticeType.equals("superb") ) {
				noticeType = Admin.NOTICE_CHANNELWORLD_TO_SUPERB;
			} else if ( channelNoticeType.equals("star") ) {
				noticeType = Admin.NOTICE_CHANNELMEMBER_TO_STAR;
			}
			opMsgService.saveChannelNoticeTemplate(channelId, noticeTpmlContent, noticeType);
			JSONUtil.optSuccess("修改成功！",jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

}
