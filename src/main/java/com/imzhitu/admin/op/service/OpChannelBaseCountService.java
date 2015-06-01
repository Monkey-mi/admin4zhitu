/**
 * 
 */
package com.imzhitu.admin.op.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.ChannelBaseCountDto;

/**
 * 频道基数管理操作的业务逻辑接口
 * @author zhangbo 2015年5月29日
 */
public interface OpChannelBaseCountService extends BaseService {

    /**
     * 保存频道基数
     * 
     * @param dto	频道基数管理数据传输对象
     * @author zhangbo 2015年5月29日
     */
    void saveChannelBaseCount(ChannelBaseCountDto dto) throws Exception;
    
    /**
     * 查询频道基数构造前台列表
     *
     * @param dto	频道基数管理数据传输对象
     * @param page	分页查询，当前页
     * @param rows	分页查询，每页数量
     * @param jsonMap	返回结果集
     * @throws Exception
     * @author zhangbo 2015年6月1日
     */
    void buildChannelBaseCountList(ChannelBaseCountDto dto, Integer page,
	    Integer rows, Map<String, Object> jsonMap) throws Exception;
    
    /**
     * 查询频道基数
     * 
     * @param channelIds	频道id集合 
     * @author zhangbo 2015年5月29日
     */
    void deleteChannelBaseCount(Integer[] channelIds) throws Exception;

}
