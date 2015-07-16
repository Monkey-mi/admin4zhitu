package com.imzhitu.admin.ztworld.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ZTWorldCommentDto;

/**
 * 广告评论数据映射接口
 * 
 * @author zhangbo 2015年7月14日
 *
 */
public interface ADCommentMapper {

    /**
     * 查询广告评论集合
     * 
     * @param dto	评论管理数据访问对象 
     * @author zhangbo 2015年7月14日
     * @return List<ZTWorldCommentDto> 广告评论集合
     */
    @DataSource("slave")
    public List<ZTWorldCommentDto> queryADComment(ZTWorldCommentDto dto);

    /**
     * 查询广告评论总数
     * 
     * @param dto	评论管理数据访问对象 
     * @return
     * @author zhangbo	2015年7月14日
     */
    @DataSource("slave")
    public long queryADCommentTotalCount(ZTWorldCommentDto dto);
    
    /**
     * 更新广告评论
     * 
     * @param dto	评论管理数据访问对象
     * @author zhangbo	2015年7月15日
     */
    @DataSource("master")
    public void updateADComment(ZTWorldCommentDto dto);

    /**
     * 批量删除广告评论
     * 
     * @param toIds	广告评论主键id集合
     * @author zhangbo	2015年7月16日
     */
    @DataSource("master")
    public void deleteADCommentByIds(Integer[] toIds);
}
