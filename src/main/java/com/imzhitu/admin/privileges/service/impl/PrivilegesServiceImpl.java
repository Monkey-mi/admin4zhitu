package com.imzhitu.admin.privileges.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.AdminNavMenu;
import com.imzhitu.admin.common.pojo.AdminPrivileges;
import com.imzhitu.admin.common.pojo.AdminPrivilegesGroup;
import com.imzhitu.admin.common.pojo.AdminRole;
import com.imzhitu.admin.common.pojo.AdminSubNavMenu;
import com.imzhitu.admin.common.pojo.AdminUser;
import com.imzhitu.admin.common.pojo.AdminUserPrivileges;
import com.imzhitu.admin.common.pojo.AdminUserRole;
import com.imzhitu.admin.privileges.dao.PrivilegesDao;
import com.imzhitu.admin.privileges.dao.PrivilegesGroupDao;
import com.imzhitu.admin.privileges.dao.RoleDao;
import com.imzhitu.admin.privileges.mapper.AdminUserInfoMapper;
import com.imzhitu.admin.privileges.mapper.AdminUserInfoRoleMapper;
import com.imzhitu.admin.privileges.mapper.AdminUserPrivilegesMapper;
import com.imzhitu.admin.privileges.service.PrivilegesService;

import net.sf.json.JSONArray;

/**
 * <p>
 * 权限管理业务逻辑访问对象
 * </p>
 * 
 * 创建时间：2013-2-16
 * @author ztj
 *
 */
@Service
public class PrivilegesServiceImpl extends BaseServiceImpl implements PrivilegesService{

	private static final String opAdmin= "op_admin";//运营管理员权限名字
	private static final String opRole = "op_role";	//运营角色权限名字，只有运营的权限 
	private static final String superAdmin = "super_admin";//超级管理员
	
	@Autowired
	private PrivilegesGroupDao privilegesGroupDao;
	
	@Autowired
	private PrivilegesDao privilegesDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private AdminUserPrivilegesMapper adminUserPrivilegesMapper;
	
	@Autowired
	private AdminUserInfoRoleMapper adminUserInfoRoleMapper;
	
	@Autowired
	private AdminUserInfoMapper adminUserInfoMapper;
	
	/*
	 * 权限分组管理业务逻辑
	 */
	
	@Override
	public void buildPrivilegesGroupPaginationJSON(Integer page, Integer rows, Map<String,Object> jsonMap) throws Exception {
		List<AdminPrivilegesGroup> groupList = new ArrayList<AdminPrivilegesGroup>();
		Integer total = privilegesGroupDao.queryTotal(Admin.ADMIN_PRIVILEGES_GROUP);
		String orderBy = "serial desc";
		List<Map<String, Object>> metaList = privilegesGroupDao.queryMetaList(page,rows,Admin.ADMIN_PRIVILEGES_GROUP,orderBy);
		for(Map<String, Object> meta : metaList) {
			AdminPrivilegesGroup group = buildPrivilegesGroupByMeta(meta);
			groupList.add(group);
			meta.clear();
		}
		metaList.clear();
		JSONUtil.queryListSuccess(jsonMap, total, groupList);
	}
	
	@Override
	public AdminPrivilegesGroup getPrivilegesGroupById(Integer id) throws Exception {
		AdminPrivilegesGroup group = null;
		Map<String, Object> meta = privilegesGroupDao.selectById(Admin.ADMIN_PRIVILEGES_GROUP, id);
		group = buildPrivilegesGroupByMeta(meta);
		meta.clear();
		return group;
	}

	@Override
	public List<AdminPrivilegesGroup> getAllPrivilegesGroup() throws Exception {
		List<AdminPrivilegesGroup> groupList = new ArrayList<AdminPrivilegesGroup>();
		String orderBy = "serial asc";
		List<Map<String, Object>> metaList = privilegesGroupDao.queryMetaList(Admin.ADMIN_PRIVILEGES_GROUP, orderBy);
		for(int i = 0; i < metaList.size(); i++) {
			Map<String, Object> meta = metaList.get(i);
			AdminPrivilegesGroup group = buildPrivilegesGroupByMeta(meta);
			if(i == metaList.size() - 1) { //默认选中第一个
				group.setSelected(true);
			}
			groupList.add(group);
			meta.clear();
		}
		metaList.clear();
		return groupList;
	}
	
	@Override
	public boolean checkPrivilegesGroupNameExist(String name) throws Exception {
		boolean flag = false;
		Map<String, Object> attrMap = new LinkedHashMap<String, Object>();
		attrMap.put("group_name", name);
		Map<String, Object> meta = privilegesGroupDao.query(attrMap, Admin.ADMIN_PRIVILEGES_GROUP);
		if(meta.size() > 0) {
			flag = true;
		}
		return flag;
	}
	
	@Override
	public Integer savePrivilegesGroup(AdminPrivilegesGroup group) throws Exception {
		Integer id = null;
		Map<String,Object> attrMap = new LinkedHashMap<String, Object>();
		attrMap.put("group_name", group.getGroupName());
		attrMap.put("group_desc", group.getGroupDesc());
		Integer maxSerial = privilegesGroupDao.queryMaxSerial();
		attrMap.put("serial", ++maxSerial);
		id = privilegesGroupDao.save(attrMap, Admin.ADMIN_PRIVILEGES_GROUP);
		return id;
	}
	
	@Override
	public void updatePrivilegesGroup(AdminPrivilegesGroup group, Integer id) throws Exception {
		Map<String,Object> attrMap = new LinkedHashMap<String, Object>();
		attrMap.put("group_name", group.getGroupName());
		attrMap.put("group_desc", group.getGroupDesc());
		attrMap.put("serial", group.getSerial());
		attrMap.put("id", id);
		privilegesGroupDao.updateById(attrMap, id, Admin.ADMIN_PRIVILEGES_GROUP);
	}
	
	@Override
	public void deletePrivilegesGroupById(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		privilegesGroupDao.deleteByIds(Admin.ADMIN_PRIVILEGES_GROUP, ids);
		for(Integer groupId : ids) { //删除该分组下的所有权限,以及从所有角色中清除该权限
			privilegesGroupDao.deleteRolePrivilegesGroupByGroupId(groupId);
			privilegesDao.deletePrivilegesByGroupId(groupId);
		}
	}
	
	/*
	 * 权限管理业务逻辑 
	 */
	
	@Override
	public void buildPrivilegesPaginationJSON(Integer groupId, Integer page, Integer rows, Map<String,Object> jsonMap) throws Exception {
		List<AdminPrivileges> priList = new ArrayList<AdminPrivileges>();
		
		/* 构造查询条件 */
		Map<String,Object> attrMap = new LinkedHashMap<String, Object>();
		if(groupId == null) {
			Map<String, Object> m = privilegesDao.queryLastPrivilegesMeta();
			groupId = (Integer)m.get("group_id");
		}
		attrMap.put("group_id", groupId);
		Integer total = privilegesDao.queryTotal(Admin.ADMIN_PRIVILEGES);
		String orderBy = "serial asc";
		List<Map<String, Object>> metaList = privilegesDao.queryMetaList(attrMap, page,rows,Admin.ADMIN_PRIVILEGES, orderBy);
		for(Map<String, Object> meta : metaList) {
			AdminPrivileges pri = buildPrivilegesByMeta(meta);
			priList.add(pri);
			meta.clear();
		}
		metaList.clear();
		JSONUtil.queryListSuccess(jsonMap, total, priList);
	}
	
	@Override
	public AdminPrivileges getPrivilegesById(Integer id) throws Exception {
		AdminPrivileges pri = null;
		Map<String, Object> meta = privilegesDao.selectById(Admin.ADMIN_PRIVILEGES, id);
		pri = buildPrivilegesByMeta(meta);
		meta.clear();
		return pri;
	}
	
	@Override
	public boolean checkPrivilegesNameExist(String name) throws Exception {
		boolean flag = false;
		Map<String, Object> attrMap = new LinkedHashMap<String, Object>();
		attrMap.put("privileges_name", name);
		Map<String, Object> meta = privilegesDao.query(attrMap, Admin.ADMIN_PRIVILEGES);
		if(meta.size() > 0) {
			flag = true;
		}
		return flag;
	}
	
	@Override
	public boolean checkPrivilegesURLExist(String url) throws Exception {
		boolean flag = false;
		Map<String, Object> attrMap = new LinkedHashMap<String, Object>();
		attrMap.put("privileges_url", url);
		Map<String, Object> meta = privilegesDao.query(attrMap, Admin.ADMIN_PRIVILEGES);
		if(meta.size() > 0) {
			flag = true;
		}
		return flag;
	}
	
	@Override
	public Integer savePrivileges(AdminPrivileges privileges) throws Exception {
		Integer id = null;
		Integer groupId = privileges.getGroupId();
		Map<String,Object> attrMap = new LinkedHashMap<String, Object>();
		attrMap.put("privileges_name", privileges.getPrivilegesName());
		attrMap.put("privileges_desc", privileges.getPrivilegesDesc());
		attrMap.put("privileges_url", privileges.getPrivilegesURL());
		attrMap.put("group_id", groupId);
		Integer maxSerial = privilegesDao.queryMaxSerialByGroupId(groupId);
		attrMap.put("serial", ++maxSerial);
		id = privilegesGroupDao.save(attrMap, Admin.ADMIN_PRIVILEGES);
		return id;
	}
	
	@Override
	public void updatePrivileges(AdminPrivileges privileges, Integer id) throws Exception {
		Map<String,Object> attrMap = new LinkedHashMap<String, Object>();
		attrMap.put("privileges_name", privileges.getPrivilegesName());
		attrMap.put("privileges_url", privileges.getPrivilegesURL());
		attrMap.put("privileges_desc", privileges.getPrivilegesDesc());
		attrMap.put("group_id", privileges.getGroupId());
		attrMap.put("serial", privileges.getSerial());
		attrMap.put("id", id);
		privilegesGroupDao.updateById(attrMap, id, Admin.ADMIN_PRIVILEGES);
	}
	
	@Override
	public void deletePrivilegesById(String idsStr) throws Exception {
		privilegesDao.deleteByIds(Admin.ADMIN_PRIVILEGES, idsStr);
	}
	
	@Override
	public void buildRolePaginationJSON(Integer page, Integer rows, Map<String,Object> jsonMap) throws Exception {
		List<AdminRole> pageList = new ArrayList<AdminRole>();
		Integer total = roleDao.queryTotal(Admin.ADMIN_ROLE);
		List<Map<String, Object>> metaList = roleDao.queryMetaList(page,rows, Admin.ADMIN_ROLE);
		for(Map<String, Object> meta : metaList) {
			AdminRole role = buildRoleFromMeta(meta);
			pageList.add(role);
			meta.clear();
		}
		metaList.clear();
		JSONUtil.queryListSuccess(jsonMap, total, pageList);
		
	}
	
	@Override
	public List<AdminRole> getAllRole() {
		List<AdminRole> roleList = new ArrayList<AdminRole>();
		String orderBy = "id asc";
		List<Map<String, Object>> metaList = roleDao.queryMetaList(Admin.ADMIN_ROLE, orderBy);
		for(int i = 0; i < metaList.size(); i++) {
			Map<String, Object> meta = metaList.get(i);
			AdminRole role = buildRoleFromMeta(meta);
			if(i == metaList.size() - 1) { //默认选中第一个
				role.setSelected(true);
			}
			roleList.add(role);
			meta.clear();
		}
		metaList.clear();
		return roleList;
	}
	
	@Override
	public AdminRole getRoleById(Integer id) throws Exception {
		AdminRole role = null;
		Map<String, Object> meta = roleDao.selectById(Admin.ADMIN_ROLE, id);
		role = buildRoleFromMeta(meta);
		Integer[] groupIds = roleDao.queryGroupIdsByRoleId(id);
		role.setGroupIds(groupIds);
		return role;
	}
	
	@Override
	public boolean checkRoleNameExist(String name) throws Exception {
		boolean flag = false;
		Map<String, Object> attrMap = new LinkedHashMap<String, Object>();
		attrMap.put("role_name", name);
		Map<String, Object> meta = roleDao.query(attrMap, Admin.ADMIN_ROLE);
		if(meta.size() > 0) {
			flag = true;
		}
		return flag;
	}
	
	@Override
	public Integer saveRole(AdminRole role, String[] groupIds) throws Exception {
		Integer id = null;
		Map<String,Object> attrMap = new LinkedHashMap<String,Object>();
		attrMap.put("role_name", role.getRoleName());
		attrMap.put("role_desc", role.getRoleDesc());
		id = roleDao.save(attrMap, Admin.ADMIN_ROLE);
		if(groupIds != null) {
			for(int i = 0 ; i < groupIds.length; i++) {
				Integer groupId = Integer.parseInt(groupIds[i]);
				roleDao.saveRolePrivilegesGroup(id, groupId);
			}
		}
		return id;
	}
	
	@Override
	public void deleteRoleByIds(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		roleDao.deleteByIds(Admin.ADMIN_ROLE, ids);
		for(Integer roleId : ids) { //删除角色拥有的权限分组
			roleDao.deleteRolePrivilegesGroupByRoleId(roleId);
		}
	}

	@Override
	public void updateRole(AdminRole role, String[] groupIds, Integer id) throws Exception {
		Map<String,Object> attrMap = new LinkedHashMap<String,Object>();
		attrMap.put("role_name", role.getRoleName());
		attrMap.put("role_desc", role.getRoleDesc());
		roleDao.updateById(attrMap, id, Admin.ADMIN_ROLE);
		roleDao.deleteRolePrivilegesGroupByRoleId(id);
		if(groupIds != null) {
			for(int i = 0 ; i < groupIds.length; i++) {
				Integer groupId = Integer.parseInt(groupIds[i]);
				roleDao.saveRolePrivilegesGroup(id, groupId);
			}
		}
	}

	@Override
	public void buildNavMenuJSONByRoleIds(Integer[] roleIds,Map<String,Object> jsonMap) throws Exception {
		List<AdminNavMenu> navMenuList = new ArrayList<AdminNavMenu>();
		if(roleIds.length > 0) {
			List<Map<String, Object>> metaList = roleDao.queryPrivilegesGroupByRoleIds(roleIds);
			for(Map<String, Object> meta : metaList) {
				List<AdminSubNavMenu> subMenuList = new ArrayList<AdminSubNavMenu>();
				Integer groupId = (Integer)meta.get("id");
				AdminNavMenu navMenu = buildNavMenuFromMeta(meta);
				navMenu.setMenus(subMenuList);
				List<Map<String, Object>> priMetaList = privilegesDao.queryPrivilegesByGroupId(groupId);
				for(Map<String, Object> priMeta : priMetaList) {
					AdminSubNavMenu subMenu = buildSubNavMenuFromMeta(priMeta);
					navMenu.addMenu(subMenu);
				}
				navMenuList.add(navMenu);
			}
		}
		JSONArray jsArray = JSONArray.fromObject(navMenuList);
		JSONUtil.querySuccess(jsArray, jsonMap);
		
	}
	
	
	
	@Override
	public void buildNavMenuJSONByUserId(Integer uid,Map<String,Object> jsonMap) throws Exception {
		List<AdminNavMenu> navMenuList = new ArrayList<AdminNavMenu>();
		AdminUserPrivileges dto = new AdminUserPrivileges();
		if(uid != null )dto.setUserId(uid);
		List<AdminUserPrivileges> aupList = adminUserPrivilegesMapper.queryUserPrivilegesByUID(dto);//查询该用户的所有权限
		List<AdminPrivilegesGroup> apgList = adminUserPrivilegesMapper.queryPrivilegesGroupList();	//查询所有的权限组
		for(AdminPrivilegesGroup apg:apgList){//遍历所有的权限组
			AdminNavMenu anm = new AdminNavMenu();
			for(AdminUserPrivileges aup:aupList){//遍历所有权限，若是符合对应的权限组id则，添加到该目录组下
				if(aup.getPrivilegeGroupId().equals(apg.getId())){
					AdminSubNavMenu asnm = new AdminSubNavMenu();
					asnm.setIcon(aup.getIcon());
					asnm.setUrl(aup.getPrivilegeUrl());
					asnm.setMenuname(aup.getPrivilegeName());
					asnm.setMenuid(aup.getId());
					anm.addMenu(asnm);
				}
			}
			//判断asnm是否添加了子权限，若没有添加，则表明该用户，在该用户组下没有权限，则抛弃该权限组。
			if(anm.getMenus().size()>0){
				anm.setIcon(apg.getIcon());
				anm.setMenuid(apg.getId());
				anm.setMenuname(apg.getGroupName());
				navMenuList.add(anm);
			}
		}
		JSONArray jsArray = JSONArray.fromObject(navMenuList);
		JSONUtil.querySuccess(jsArray, jsonMap);
		
	}
	
	/**
	 * 查询权限下属，例如，超级管理员可以看到所有的人员。运营管理员只能看到运营角色。
	 * @param uid
	 * @param jsonMap
	 * @throws Exception
	 */
	@Override
	public List<AdminUser> querySubordinatesByUserId(Integer uid)throws Exception{
		AdminUserRole dto = new AdminUserRole();
		dto.setUserId(uid);
		//查询用户所拥有的角色组
		List<AdminUserRole > userRoleList = adminUserInfoRoleMapper.queryRoleInfoByUserId(dto);
		//查询超级管理员角色id
		Integer superAdminId = adminUserInfoRoleMapper.queryRoleIdByRoleName(superAdmin);
		if(null == superAdminId){
			throw new Exception("查询超级管理员角色id失败");
		}
		//查询运营管理员角色id
		Integer opAdminId = adminUserInfoRoleMapper.queryRoleIdByRoleName(opAdmin);
		if(null == opAdminId){
			throw new Exception("查询运营管理员角色id失败");
		}
		//判断是否为超级管理员或者运营管理员。因为用户具有多个角色，所以同时具有超级管理员和运营管理员，则取前者
		boolean superAdminFlag=false;
		boolean opAdminFlag=false;
		for(AdminUserRole role : userRoleList){
			if(role.getRoleId().equals( superAdminId)){
				superAdminFlag = true;
				break;
			}else if(role.getRoleId().equals(opAdminId)){
				opAdminFlag = true;
			}
		}
		if(superAdminFlag == true){
			return adminUserInfoMapper.queryUserNameAndId();
		}else if(opAdminFlag == true){
			return adminUserInfoMapper.queryAllNotSuperAdminOrOpAdminUserInfo();
		}
		return null;
	}
	
	
	/**
	 * 查询用户所拥有的权限组
	 * @param uid
	 * @param jsonMap
	 * @throws Exception
	 */
	@Override
	public void queryUserPrivilegesByUserId(Integer uid,int page, int row,Map<String,Object> jsonMap)throws Exception{
		AdminUserPrivileges dto = new AdminUserPrivileges();
		dto.setUserId(uid);
		buildNumberDtos(dto,page,row,jsonMap,new NumberDtoListAdapter<AdminUserPrivileges>(){
			@Override
			public long queryTotal(AdminUserPrivileges dto){
				return adminUserPrivilegesMapper.queryUserPrivilegeCountByUID(dto);
			}
			
			@Override
			public List< ? extends AbstractNumberDto> queryList(AdminUserPrivileges dto){
				return adminUserPrivilegesMapper.queryUserPrivilegesByUID(dto);
			}
		});
	}
	
	
	/**
	 * 查询用户所拥有的权限组 用于分配权限
	 * @param uid
	 * @param jsonMap
	 * @throws Exception
	 */
	@Override
	public void queryUserPrivilegesByUserIdForTable(final Integer uid,int page, int row,Map<String,Object> jsonMap)throws Exception{
		
		AdminUserRole roleDto = new AdminUserRole();
		Integer superAdminId = adminUserInfoRoleMapper.queryRoleIdByRoleName(superAdmin);
		roleDto.setRoleId(superAdminId);
		roleDto.setUserId(uid);
		final long r = adminUserInfoRoleMapper.queryUserRoleCountByUserId(roleDto);
		long total;
		List<AdminPrivileges> list;
		if( r > 0){//超级管理员
			total = adminUserPrivilegesMapper.queryAllPrivilegesCount();
			list  = adminUserPrivilegesMapper.queryAllPrivileges(); 
		}else{//非超级管理员
			total = adminUserPrivilegesMapper.queryUserPrivilegesCountForTable(uid);
			list  = adminUserPrivilegesMapper.queryUserPrivilegesForTable(uid);
		}
		
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
	}
	
	/**
	 * 为用户增加权限
	 * @param uid
	 * @param privilegeIdsStr
	 * @param operatorId
	 * @throws Exception
	 */
	@Override
	public void addPrivilegesToUser(Integer uid,String privilegeIdsStr,Integer operatorId)throws Exception{
		if(privilegeIdsStr == null) return ;
		Integer[] privilegeIds = StringUtil.convertStringToIds(privilegeIdsStr);
		Date now  = new Date();
		for(int i = 0; i < privilegeIds.length; i++){
			AdminUserPrivileges dto = new AdminUserPrivileges();
			dto.setUserId(uid);
			dto.setAddDate(now);
			dto.setModifyDate(now);
			dto.setPrivilegeId(privilegeIds[i]);
			dto.setOperatorId(operatorId);
			dto.setValid(Tag.TRUE);
			if(0 ==adminUserPrivilegesMapper.queryCountByPrivilegeId(dto)){//若是用户不具有该权限，则添加该权限
				adminUserPrivilegesMapper.addUserPrivilege(dto);
			}else{//若用户具有该权限，则更新该权限的有效性
				adminUserPrivilegesMapper.updateValid(dto);
			}
		}
	}
	
	/**
	 * 删除权限
	 * @param uid
	 * @param privilegeIdsStr
	 * @param operatorId
	 * @throws Exception
	 */
	@Override
	public void deletePrivileges(Integer uid,String privilegeIdsStr,Integer operatorId)throws Exception{
		if(privilegeIdsStr == null) return ;
		Integer[] privilegeIds = StringUtil.convertStringToIds(privilegeIdsStr);
		Date now  = new Date();
		for(int i = 0; i < privilegeIds.length; i++){
			AdminUserPrivileges dto = new AdminUserPrivileges();
			dto.setUserId(uid);
			dto.setModifyDate(now);
			dto.setPrivilegeId(privilegeIds[i]);
			dto.setOperatorId(operatorId);
			dto.setValid(Tag.FALSE);
			if(0 !=adminUserPrivilegesMapper.queryCountByPrivilegeId(dto)){//若是用户具有该权限，则删除该权限
				adminUserPrivilegesMapper.updateValid(dto);
			}
		}
	}
	
	/**
	 * 从元数据构建权限分组信息POJO
	 * @param meta
	 * @return
	 */
	private AdminPrivilegesGroup buildPrivilegesGroupByMeta(Map<String, Object> meta) {
		AdminPrivilegesGroup group = new AdminPrivilegesGroup(
				(Integer)meta.get("id"),
				(String)meta.get("group_name"),
				(String)meta.get("group_desc"),
				(String)meta.get("icon"),
				(Integer)meta.get("serial"));
		return group;
	}


	/**
	 * 从元数据构建权限分组信息POJO
	 * @param meta
	 * @return
	 */
	private AdminPrivileges buildPrivilegesByMeta(Map<String, Object> meta) {
		AdminPrivileges pri = new AdminPrivileges(
				(Integer)meta.get("id"),
				(String)meta.get("privileges_name"),
				(String)meta.get("privileges_desc"),
				(String)meta.get("privileges_url"),
				(String)meta.get("icon"),
				(Integer)meta.get("group_id"),
				(Integer)meta.get("serial"));
		return pri;
	}
	
	/**
	 * 从元数据构建角色信息POJO
	 * 
	 * @param meta
	 * @return
	 */
	private AdminRole buildRoleFromMeta(Map<String, Object> meta) {
		AdminRole role = new AdminRole(
			(Integer)meta.get("id"),
			(String)meta.get("role_name"),
			(String)meta.get("role_desc"));
		return role;
	}

	/**
	 * 从元数据构建一级导航菜单信息
	 * @param meta
	 * @return
	 */
	private AdminNavMenu buildNavMenuFromMeta(Map<String, Object> meta) {
		AdminNavMenu navMenu = new AdminNavMenu(
			(Integer)meta.get("id"),
			(String)meta.get("group_name"),
			(String)meta.get("icon"));
		return navMenu;
	}
	
	/**
	 * 从元数据构建二级导航菜单信息
	 * @param meta
	 * @return
	 */
	private AdminSubNavMenu buildSubNavMenuFromMeta(Map<String, Object> meta) {
		AdminSubNavMenu subMenu = new AdminSubNavMenu(
			(Integer)meta.get("serial"),
			(String)meta.get("privileges_name"), 
			(String)meta.get("icon"),
			(String)meta.get("privileges_url"));
		return subMenu;
	}
	
}
