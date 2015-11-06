package com.imzhitu.admin.channel;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.channel.service.ChannelWorldService;
import com.imzhitu.admin.common.BaseCRUDAction;

/**
 * @author zhangbo	2015年11月5日
 *
 */
public class ChannelWorldAction extends BaseCRUDAction {

	/**
	 * 序列号
	 * @author zhangbo	2015年11月5日
	 */
	private static final long serialVersionUID = 4669641902973129685L;
	
	/**
	 * 频道id
	 * @author zhangbo	2015年11月5日
	 */
	private Integer channelId;

	/**
	 * 织图id
	 * @author zhangbo	2015年11月5日
	 */
	private Integer worldId;
	
	/**
	 * 生效标志，生效：1，失效（未生效）：0
	 * @author zhangbo	2015年11月5日
	 */
	private Integer valid;
	
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	@Autowired
	private ChannelWorldService service;

	/**
	 * 更新频道织图有效性
	 * 
	 * @return
	 * @author zhangbo	2015年11月6日
	 */
	public String updateChannelWorldValid() {
		try {
			if ( valid == 1 ) {
				service.setChannelWorldValidByOperator(channelId, worldId);
			} else if ( valid == 0 ) {
				service.setChannelWorldInvalidByOperator(channelId, worldId);
			}
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
}
