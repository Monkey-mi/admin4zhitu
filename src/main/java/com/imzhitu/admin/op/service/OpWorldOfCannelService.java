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
     * @param isTop
     *            是否置顶
     * 
     * @throws Exception
     * @author zhangbo 2015年5月19日
     */
    public void setTopById(Integer id, boolean isTop) throws Exception;

    /**
     * 通过id加精
     * 
     * @param id
     *            频道与织图关联关系表的id
     * @param isSuperb
     *            是否加精
     * 
     * @throws Exception
     * @author zhangbo 2015年5月20日
     */
    public void setSuperbById(Integer id, boolean isSuperb) throws Exception;

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
}
