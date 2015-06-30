/**
 * 
 */
package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ChannelBaseCountDto;

/**
 * 频道基数映射类
 * @author zhangbo 2015年5月29日
 */
public interface OpChannelBaseCountMapper {

    /**
     * 查询频道基数管理表数量
     *
     * @param dto
     *            频道基数管理数据传输对象
     * @return
     * @author zhangbo 2015年5月29日
     */
    @DataSource("slave")
    long queryChannelBaseCount(ChannelBaseCountDto dto);

    /**
     * 查询频道基数管理结果集
     *
     * @param dto
     *            频道基数管理数据传输对象
     * @return
     * @author zhangbo 2015年6月1日
     */
    @DataSource("slave")
    List<ChannelBaseCountDto> queryChannelBaseCountList(ChannelBaseCountDto dto);

    /**
     * 插入数据到频道基数管理表
     *
     * @param dto
     *            频道基数管理数据传输对象
     * @author zhangbo 2015年5月29日
     */
    @DataSource("master")
    void insertChannelBaseCount(ChannelBaseCountDto dto);

    /**
     * 更新数据到频道基数管理表
     *
     * @param dto
     *            频道基数管理数据传输对象
     * @author zhangbo 2015年5月29日
     */
    @DataSource("master")
    void updateChannelBaseCount(ChannelBaseCountDto dto);

    /**
     * 通过channelId删除频道基数管理表数据
     *
     * @param channelIds
     *            频道id集合
     * @author zhangbo 2015年5月29日
     */
    @DataSource("master")
    void deleteChannelBaseCountByChannelIds(Integer[] channelIds);
    
    /**
     * 查询频道是否存在于频道基数管理表，不为0，即存在
     *
     * @param channelId	频道id
     * @return
     * @author zhangbo 2015年6月1日
     */
    @DataSource("slave")
    long isExistChannelBaseCount(Integer channelId);

}