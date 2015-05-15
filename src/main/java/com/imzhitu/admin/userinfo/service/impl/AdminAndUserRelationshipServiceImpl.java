package com.imzhitu.admin.userinfo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.AdminAndUserRelationshipDto;
import com.imzhitu.admin.common.pojo.AdminUser;
import com.imzhitu.admin.common.pojo.AdminUserPrivileges;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.constant.Permission;
import com.imzhitu.admin.privileges.mapper.AdminUserInfoMapper;
import com.imzhitu.admin.privileges.mapper.AdminUserPrivilegesMapper;
import com.imzhitu.admin.userinfo.mapper.AdminAndUserRelationshipMapper;
import com.imzhitu.admin.userinfo.mapper.UserInfoMapper;
import com.imzhitu.admin.userinfo.service.AdminAndUserRelationshipService;

/**
 * 管理员账号与织图用户关联关系Service实现类
 *
 * @author zhangbo 2015年5月14日
 */
@Service
public class AdminAndUserRelationshipServiceImpl extends BaseServiceImpl implements AdminAndUserRelationshipService{

    /**
     * 管理员账号与织图用户关联关系映射对象
     * 
     * @author zhangbo 2015年5月14日
     */
    @Autowired
    private AdminAndUserRelationshipMapper relationshipMapper;
    
    /**
     * 织图用户映射对象
     * 
     * @author zhangbo 2015年5月15日
     */
    @Autowired
    private UserInfoMapper userMapper;
    
    /**
     * 管理员账号映射对象
     * 
     * @author zhangbo 2015年5月15日
     */
    @Autowired
    private AdminUserInfoMapper adminMapper;
    
    /**
     * 权限映射对象
     * 
     * @author zhangbo 2015年5月15日
     */
    @Autowired
    private AdminUserPrivilegesMapper permissionMapper;
    
    @Override
    public boolean createAdminAndUserRelationship(Integer adminId, Integer userId) throws Exception{
	
	// 通过用户Id查询
	UserInfo userInfo = userMapper.selectById(userId);
	
	// 若查询用户存在，不为空，则执行插入，否则返回false
	if ( userInfo != null ) {
	    // 定义输入的DTO
	    AdminAndUserRelationshipDto dto = new AdminAndUserRelationshipDto();
	    dto.setAdminUserId(adminId);
	    dto.setUserId(userInfo.getId());
		
	    // 通过DTO创建管理员账号与织图用户的关联关系
	    relationshipMapper.addByDTO(dto);
	    
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public Map<String, Object> updateAdminAndUserRelationship()
	    throws Exception {
	return null;
    }
    
    @Override
    public List<AdminAndUserRelationshipDto> getAllAdminAndUserRelationshipByRole(
	    Integer adminId) throws Exception {
	// 根据id获取传入管理员的权限列表
	AdminUserPrivileges permission = new AdminUserPrivileges();
	permission.setUserId(adminId);
	List<AdminUserPrivileges> permissionList = permissionMapper.queryUserPrivilegesByUID(permission);
	
	// 声明是否具有超级管理员或运营管理员权限
	boolean flag = false;
	
	for (AdminUserPrivileges adminUserPrivileges : permissionList) {
	    if ( adminUserPrivileges.getPrivilegeName().equals(Permission.SUPER_ADMIN) 
		    || adminUserPrivileges.getPrivilegeName().equals(Permission.OP_ADMIN) ) {
		flag = true;
		break;
	    }
	}
	
	// 若具有超级管理员或运营管理员权限，则返回全表查询的数据，否则只返回与传入用户有关联关系的用户信息结果集
	if (flag) {
	    return relationshipMapper.queryAllResults();
	} else {
	    // 定义输入的DTO
	    AdminAndUserRelationshipDto dto = new AdminAndUserRelationshipDto();
	    dto.setAdminUserId(adminId);
	    
	    // 通过DTO获得管理员账号与织图用户的关联关系结果集
	    return relationshipMapper.queryResultsByDTO(dto);
	}
    }

    @Override
    public Map<String, Object> queryAdminAndUserRelationship(Integer adminId) throws Exception {
	
	// 定义输入的DTO
	AdminAndUserRelationshipDto dto = new AdminAndUserRelationshipDto();
	dto.setAdminUserId(adminId);
	
	// 通过DTO获得管理员账号与织图用户的关联关系结果集
	List<AdminAndUserRelationshipDto> result = relationshipMapper.queryResultsByDTO(dto);
	
	Map<String, Object> jsonMap = new HashMap<String, Object>();
	jsonMap.put(OptResult.ROWS, result);
	
	return jsonMap;
    }

    @Override
    public Map<String, Object> deleteAdminAndUserRelationship()
	    throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

}
