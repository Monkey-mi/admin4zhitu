package com.imzhitu.admin.ztworld.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.ADKeywordDTO;
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
     * 根据广告评论表主键id，查询广告评论
     * 
     * @param id	广告评论表主键id
     * @return
     * @author zhangbo	2015年9月28日
     */
    @DataSource("slave")
    public ZTWorldCommentDto queryADCommentById(Integer id);
    
    /**
     * 更新广告评论
     * 
     * @param id	广告评论表主键id
     * @param valid	有效性
     * @author zhangbo	2015年9月28日
     */
    @DataSource("master")
    public void updateADComment(@Param("id")Integer id, @Param("valid")Integer valid);

    /**
     * 批量删除广告评论
     * 
     * @param ids	广告评论主键id集合
     * @author zhangbo	2015年7月16日
     */
    @DataSource("master")
    public void deleteADCommentByIds(Integer[] ids);

    /**
     * 查询广告关键词集合
     * 
     * @param dto	广告关键词数据传输对象
     * @return
     * @author zhangbo	2015年7月17日
     */
    @DataSource("slave")
    public List<ADKeywordDTO> queryADKeywordList(ADKeywordDTO dto);
    
    /**
     * 查询广告关键词总数
     * 
     * @param dto	广告关键词数据传输对象
     * @return
     * @author zhangbo	2015年7月17日
     */
    @DataSource("slave")
    public long queryADKeywordTotalCount(ADKeywordDTO dto);

    /**
     * 批量删除广告关键词
     * 
     * @param ids	广告关键词主键id集合
     * @author zhangbo	2015年7月17日
     */
    @DataSource("master")
    public void deleteADKeywordByIds(Integer[] ids);
    
    /**
     * 插入广告关键词
     * 
     * @param dto	广告关键词数据传输对象
     * @author zhangbo	2015年7月17日
     */
    @DataSource("master")
    public void insertADKeyword(ADKeywordDTO dto);

    /**
     * 更新广告关键词击中次数
     * 
     * @param dto	广告关键词数据传输对象
     * @author zhangbo	2015年7月17日
     */
    @DataSource("master")
    public void updateADKeyword(ADKeywordDTO dto);

}
