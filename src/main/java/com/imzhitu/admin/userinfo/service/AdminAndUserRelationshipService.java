package com.imzhitu.admin.userinfo.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;

/**
 * 管理员账号与织图用户绑定关系业务逻辑接口
 * 
 * @author zhangbo 2015-05-13
 *
 */
public interface AdminAndUserRelationshipService extends BaseService {

    /**
     * 创建管理员账号（当前登陆）与织图用户之间的关联关系，即绑定
     * 
     * @param adminId	管理员账号ID
     * @param userId	织图用户ID
     * 
     * @return jsonMap
     * @throws Exception
     * @author zhangbo 2015-05-13
     */
    public void createAdminAndUserRelationship(Integer adminId, Integer userId) throws Exception;
    
    /**
     * 更新管理员账号与织图用户之间的关联关系
     * 
     * @author zhangbo 2015-05-13
     * 
     * @return jsonMap
     * @throws Exception
     */
    public Map<String, Object> updateAdminAndUserRelationship() throws Exception;
    
    /**
     * 查询管理员账号与织图用户之间的关联关系
     * 
     * @param adminId	管理员账号ID
     * 
     * @return jsonMap
     * @throws Exception
     * @author zhangbo 2015-05-13
     */
    public Map<String, Object> queryAdminAndUserRelationship(Integer adminId) throws Exception;
    
    /**
     * 删除管理员账号与织图用户之间的关联关系，为硬删除
     * 
     * @author zhangbo 2015-05-13
     * 
     * @return jsonMap
     * @throws Exception
     */
    public Map<String, Object> deleteAdminAndUserRelationship() throws Exception;

}
