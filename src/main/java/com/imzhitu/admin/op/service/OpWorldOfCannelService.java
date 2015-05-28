/**
 * 
 */
package com.imzhitu.admin.op.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.ChannelWorldRecyclerDto;
import com.imzhitu.admin.common.pojo.OpWorldOfCannelDto;

/**
 * 织图在频道中相关联操作的业务逻辑接口
 * 
 * @author zhangbo 2015年5月19日
 */

public interface OpWorldOfCannelService extends BaseService {

    /**
     * 通过id置顶
     *
     * @param id
     *            频道与织图关联关系表的id
     * @param channelId
     *            频道id
     * @param worldId
     *            织图id
     * @param isTop
     *            是否置顶
     * 
     * @throws Exception
     * @author zhangbo 2015年5月19日
     */
    public void setTopToCache(Integer id, Integer channelId, Integer worldId,
	    boolean isTop) throws Exception;

    /**
     * 通过id加精
     * 
     * @param id
     *            频道与织图关联关系表的id
     * @param channelId
     *            频道id
     * @param worldId
     *            织图id
     * @param isSuperb
     *            是否加精
     * 
     * @throws Exception
     * @author zhangbo 2015年5月20日
     */
    public void setSuperbById(Integer id, Integer channelId, Integer worldId,
	    boolean isSuperb) throws Exception;

    /**
     * 在中间表中，根据id删除织图与频道的关联关系
     * 
     * @param ids
     *            频道与织图关联关系表的id集合
     * 
     * @throws Exception
     * @author zhangbo 2015年5月20日
     */
    public void deleteByIdsFromCache(Integer[] ids) throws Exception;

    /**
     * 同步织图与频道关联关系中间表数据到主表（包括置顶、加精）
     * 
     * @param channelId
     *            频道id
     * 
     * @throws Exception
     * @author zhangbo 2015年5月22日
     */
    public void syncDataFromCache(Integer channelId) throws Exception;

    /**
     * 设置数据到中间表
     * 
     * @param dto
     *            织图与频道关联表数据传输对象
     * 
     * @throws Exception
     * @author zhangbo 2015年5月27日
     */
    public void setDataToCache(OpWorldOfCannelDto dto) throws Exception;

    /**
     * 构建回收站列表
     * 
     * @param dto
     *            频道织图回收站数据传输对象
     * @param page
     *            当前页数
     * @param rows
     *            每页查询的数量
     * @param jsonMap
     *            返回的结果集
     * 
     * @throws Exception
     * @author zhangbo 2015年5月27日
     */
    public void buildRecyclerList(ChannelWorldRecyclerDto dto, Integer page,
	    Integer rows, Map<String, Object> jsonMap) throws Exception;
}
