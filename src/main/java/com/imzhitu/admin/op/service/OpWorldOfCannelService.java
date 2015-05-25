/**
 * 
 */
package com.imzhitu.admin.op.service;

import com.hts.web.common.service.BaseService;

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
     * 根据id在织图与频道的关联关系表中设置失效，即valid设置为0
     * 
     * @param id
     *            频道与织图关联关系表的id
     * @param isValid
     *            是否生效
     * 
     * @throws Exception
     * @author zhangbo 2015年5月20日
     */
    public void setValidById(Integer id, boolean isValid) throws Exception;

    /**
     * 根据id删除织图与频道的关联关系
     * 
     * @param id
     *            频道与织图关联关系表的id
     * 
     * @throws Exception
     * @author zhangbo 2015年5月20日
     */
    public void deleteById(Integer id) throws Exception;

    /**
     * 同步织图与频道关联关系中间表数据到主表（包括置顶、加精、删除信息）
     * 
     * @param channelId
     *            频道id
     * 
     * @throws Exception
     * @author zhangbo 2015年5月22日
     */
    public void syncDataFromCache(Integer channelId) throws Exception;
}
