/**
 * 
 */
package com.imzhitu.admin.op.mapper;

import java.util.List;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpWorldOfCannelDto;

/**
 * 织图在频道中相关联操作映射类
 * 
 * @author zhangbo 2015年5月19日
 */
public interface OpWorldOfCannelMapper {

    /**
     * 根据织图与频道关联表主键id，在cache表中查询结果 若有结果，证明当前信息已经做过相应操作，若无结果，证明第一次操作
     *
     * @param id
     *            织图与频道关联表主键id
     * @return
     * @author zhangbo 2015年5月22日
     */
    @DataSource("slave")
    public OpWorldOfCannelDto queryFromCacheById(Integer id);

    /**
     * 根据频道id，在cache表中查询结果
     *
     * @param channelId
     *            频道id
     * @return
     * @author zhangbo 2015年5月22日
     */
    @DataSource("slave")
    public List<OpWorldOfCannelDto> queryFromCacheByCannelId(Integer channelId);

    /**
     * 在中间表中插入频道与织图关联关系的数据
     *
     * @param dto
     *            织图与频道关联表数据传输对象
     * 
     * @author zhangbo 2015年5月20日
     */
    @DataSource("master")
    public void insertDataToCacheByDto(OpWorldOfCannelDto dto);

    /**
     * 更新中间表频道中对织图置顶的设置
     *
     * @param dto
     *            织图与频道关联表数据传输对象
     * 
     * @author zhangbo 2015年5月20日
     */
    @DataSource("master")
    public void updateDataToCacheByDto(OpWorldOfCannelDto dto);

    /**
     * 在织图与频道关联关系表中将valid设置为0，置为失效
     *
     * @param dto
     *            织图与频道关联表数据传输对象
     * 
     * @author zhangbo 2015年5月20日
     */
    @DataSource("master")
    public void setValidById(OpWorldOfCannelDto dto);

    /**
     * 根据id删除织图与频道的关联关系
     *
     * @param id
     *            织图与频道关联表主键id
     * 
     * @author zhangbo 2015年5月20日
     */
    @DataSource("master")
    public void deleteById(Integer id);
    
    /**
     * 根据id集，删除织图与频道的关联关系
     *
     * @param ids
     *            织图与频道关联表主键id集合
     * 
     * @author zhangbo 2015年5月20日
     */
    @DataSource("master")
    public void deleteByIds(Integer[] ids);

    /**
     * 同步中间表数据到主表，根据频道id来同步
     *
     * @author zhangbo 2015年5月22日
     */
    @DataSource("master")
    public void syncDataToMainTable(OpWorldOfCannelDto dto);
}
