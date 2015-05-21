/**
 * 
 */
package com.imzhitu.admin.op.mapper;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.OpWorldOfCannelDto;

/**
 * 织图在频道中相关联操作映射类
 * 
 * @author zhangbo 2015年5月19日
 */
public interface OpWorldOfCannelMapper {

    /**
     * 设置置顶权重
     *
     * @param dto
     *            织图与频道关联表数据传输对象
     * 
     * @throws Exception
     * @author zhangbo 2015年5月20日
     */
    @DataSource("master")
    public void setTopByDto(OpWorldOfCannelDto dto);

    /**
     * 设置加精
     *
     * @param dto
     *            织图与频道关联表数据传输对象
     * 
     * @throws Exception
     * @author zhangbo 2015年5月20日
     */
    @DataSource("master")
    public void setSuperbByDto(OpWorldOfCannelDto dto);

    /**
     * 删除织图与频道关联关系
     *
     * @param id
     *            频道与织图关联关系表的id
     * 
     * @throws Exception
     * @author zhangbo 2015年5月20日
     */
    @DataSource("master")
    public void deleteById(Integer id);
}
