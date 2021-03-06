package com.imzhitu.admin.userinfo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.AdminAndUserRelationshipDto;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.privileges.dao.RoleDao;
import com.imzhitu.admin.userinfo.mapper.AdminAndUserRelationshipMapper;
import com.imzhitu.admin.userinfo.mapper.UserInfoMapper;
import com.imzhitu.admin.userinfo.service.AdminAndUserRelationshipService;

/**
 * 管理员账号与织图用户关联关系Service实现类
 *
 * @author zhangbo 2015年5月14日
 */
@Service
public class AdminAndUserRelationshipServiceImpl extends BaseServiceImpl implements AdminAndUserRelationshipService {

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
	 * 角色映射对象
	 * 
	 * @author zhangbo 2015年5月15日
	 */
	@Autowired
	private RoleDao roleDao;

	@Override
	public void createAdminAndUserRelationship(Integer adminId, Integer userId) throws Exception {

		// 若查询用户存在，则执行插入，否则返回false
		if (isExistUser(userId)) {

			AdminAndUserRelationshipDto relationDto = relationshipMapper.queryByAdminIdAndUserId(adminId, userId);

			// 若不存在则创建，否则抛出异常
			if (relationDto == null) {
				// 定义输入的DTO
				AdminAndUserRelationshipDto dto = new AdminAndUserRelationshipDto();
				dto.setAdminUserId(adminId);
				dto.setUserId(userId);

				// 通过DTO创建管理员账号与织图用户的关联关系
				relationshipMapper.addByDTO(dto);

			} else {
				throw new Exception("输入的织图用户id已经与现在的管理员账号绑定了！");
			}

		} else {
			throw new Exception("绑定的用户Id不存在，请填写正确的用户Id");
		}
	}

	@Override
	public void updateAdminAndUserRelationship(Integer id, Integer userId) throws Exception {
		// 若查询用户存在，则执行更新，否则返回false
		if (isExistUser(userId)) {
			
			// 根据主键id获取数据，然后再查询此管理员账号与用户id是否存在
			AdminAndUserRelationshipDto relationDtoById = relationshipMapper.queryById(id);
			AdminAndUserRelationshipDto relationDto = relationshipMapper.queryByAdminIdAndUserId(relationDtoById.getAdminUserId(), userId);
			
			// 若不存在则更新 ，否则抛出异常
			if ( relationDto == null ) {
				// 定义输入的DTO
				AdminAndUserRelationshipDto dto = new AdminAndUserRelationshipDto();
				dto.setId(id);
				dto.setUserId(userId);
				
				// 通过DTO更新管理员账号与织图用户的关联关系
				relationshipMapper.updateByDTO(dto);
			} else {
				throw new Exception("输入的织图用户id已经与现在的管理员账号绑定了！");
			}
		} else {
			throw new Exception("绑定的用户Id不存在，请填写正确的用户Id");
		}
	}

	@Override
	public List<AdminAndUserRelationshipDto> getAllAdminAndUserRelationshipByRole(Integer adminId) throws Exception {

		// 定义输入的DTO，不设置分页查询用的属性，则不进行分页查询，不设置adminId则进行全表查询
		AdminAndUserRelationshipDto dto = new AdminAndUserRelationshipDto();

		// 若不具有超级管理员或运营管理员角色，返回与传入用户有关联关系的用户信息结果集，否则返回全表查询的数据
		if (!roleDao.isSuperOrOpAdmin(adminId)) {
			dto.setAdminUserId(adminId);
		}
		// 通过DTO获得管理员账号与织图用户的关联关系结果集
		return relationshipMapper.queryResultsByDTO(dto);
	}

	@Override
	public void queryAdminAndUserRelationship(Integer adminId, Integer maxId, Integer page, Integer rows, Map<String, Object> jsonMap) throws Exception {
		// 定义输入的DTO，使用分页查询则设置相关属性（firstRow、limit、maxid）
		AdminAndUserRelationshipDto dto = new AdminAndUserRelationshipDto();
		dto.setFirstRow(page);
		dto.setLimit(rows);
		dto.setMaxId(maxId);

		// 若不具有super_admin或op_admin权限，则设置adminId查询此管理员与织图用户的关联关系，若具有超级权限，则不设置adminId，进行全表查询
		if (!roleDao.isSuperOrOpAdmin(adminId)) {
			dto.setAdminUserId(adminId);
		}

		buildNumberDtos(dto, page, rows, jsonMap, new NumberDtoListAdapter<AdminAndUserRelationshipDto>() {
			@Override
			public long queryTotal(AdminAndUserRelationshipDto dto) {
				/*
				 * 通过管理员Id获得管理员账号与织图用户的关联关系结果集 单独传入管理员id，此方法内部重新定义了输入dto，没有定义分页查询的属性，所以查询的结果集为总数，转化为long型
				 */
				List<AdminAndUserRelationshipDto> result = null;
				try {
					result = getAllAdminAndUserRelationshipByRole(dto.getAdminUserId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return (long) result.size();
			}

			@Override
			public List<? extends AbstractNumberDto> queryList(AdminAndUserRelationshipDto dto) {
				return relationshipMapper.queryResultsByDTO(dto);
			}

		});
	}

	@Override
	public void deleteAdminAndUserRelationship(Integer[] ids) throws Exception {
		for (Integer id : ids) {
			AdminAndUserRelationshipDto dto = new AdminAndUserRelationshipDto();
			dto.setId(id);
			relationshipMapper.deleteByDTO(dto);
		}
	}

	/**
	 * 校验用户是否存在
	 *
	 * @param id	用户id
	 * @return
	 * @author zhangbo 2015年5月18日
	 */
	private boolean isExistUser(Integer id) {
		// 通过用户Id查询
		UserInfo userInfo = userMapper.selectById(id);
		return userInfo == null ? false : true;

	}

}
