/**
 * 
 */
package com.imzhitu.admin.op;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.ChannelBaseCountDto;
import com.imzhitu.admin.common.pojo.OpChannel;
import com.imzhitu.admin.op.service.ChannelService;
import com.imzhitu.admin.op.service.OpChannelBaseCountService;

/**
 * 频道基数管理Action层
 * @author zhangbo 2015年5月29日
 */
public class OpChannelBaseCountAction extends BaseCRUDAction {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6813398937925814369L;
    
    private Logger log = Logger.getLogger(OpChannelBaseCountAction.class);

    private ChannelBaseCountDto baseCountDto = new ChannelBaseCountDto();
    
    private String channelIds;
    
    /**
     * @return the baseCountDto
     */
    public ChannelBaseCountDto getBaseCountDto() {
        return baseCountDto;
    }

    /**
     * @param baseCountDto the baseCountDto to set
     */
    public void setBaseCountDto(ChannelBaseCountDto baseCountDto) {
        this.baseCountDto = baseCountDto;
    }

    /**
     * @return the channelIds
     */
    public String getChannelIds() {
        return channelIds;
    }

    /**
     * @param channelIds the channelIds to set
     */
    public void setChannelIds(String channelIds) {
        this.channelIds = channelIds;
    }

    @Autowired
    private OpChannelBaseCountService service;
    
    @Autowired
    private ChannelService channelService;
    
    @Autowired
    private com.hts.web.operations.service.ChannelService webChannelService;

    /**
     * 保存数据到频道基数管理表
     *
     * @return
     * @author zhangbo 2015年5月30日
     */
    public String saveChannelBaseCount() {
	try {
	    if (isExistChannel(baseCountDto.getChannelId())) {
		service.saveChannelBaseCount(baseCountDto);
		
		webChannelService.updateWorldAndChildCount(baseCountDto.getChannelId()); //更新织图和图片总数
		webChannelService.updateMemberCount(baseCountDto.getChannelId()); // 更新频道成员订阅总数
		webChannelService.updateSuperbCount(baseCountDto.getChannelId()); // 更新频道加精总数
		
		JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
	    } else {
		JSONUtil.optSuccess("频道id不存在，请填写正确的频道id", jsonMap);
	    }
	} catch (Exception e) {
	    log.error(e);
	    JSONUtil.optFailed(OptResult.ADD_FAILED, jsonMap);;
	}
	return StrutsKey.JSON;
    }
    
    /**
     * 查询频道基数页面表格展示数据
     *
     * @return
     * @author zhangbo 2015年5月30日
     */
    public String buildChannelBaseCountList() {
	try {
	    baseCountDto.setAdminId(getCurrentLoginUserId());
	    service.buildChannelBaseCountList(baseCountDto,page,rows,jsonMap);
	    JSONUtil.optSuccess(OptResult.QUERY_SUCCESS, jsonMap);
	} catch (Exception e) {
	    log.error(e);
	    JSONUtil.optFailed(OptResult.QUERY_SUCCESS, jsonMap);;
	}
	return StrutsKey.JSON;
    }
    
    /**
     * 批量删除数据到频道基数管理表
     *
     * @return
     * @author zhangbo 2015年5月30日
     */
    public String deleteChannelBaseCount() {
	try {
	    Integer[] ids = StringUtil.convertStringToIds(getChannelIds());
	    service.deleteChannelBaseCount(ids);
	    
	    for (Integer channelId : ids) {
		webChannelService.updateWorldAndChildCount(channelId); //更新织图和图片总数
		webChannelService.updateMemberCount(channelId); // 更新频道成员订阅总数
		webChannelService.updateSuperbCount(baseCountDto.getChannelId()); // 更新频道加精总数
	    }
	    
	    JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
	} catch (Exception e) {
	    log.error(e);
	    JSONUtil.optFailed(OptResult.DELETE_SUCCESS, jsonMap);;
	}
	return StrutsKey.JSON;
    }
    
    /**
     * 判断频道是否存在
     *
     * @param channelId
     * @return
     * @throws Exception
     * @author zhangbo 2015年6月1日
     */
    private boolean isExistChannel(Integer channelId) throws Exception {
	OpChannel queryChannelById = channelService.queryChannelById(channelId);
	if ( queryChannelById == null ) {
	    return false;
	} else {
	    return true;
	}
    }
    
}
