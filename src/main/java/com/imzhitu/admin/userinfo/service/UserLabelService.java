package com.imzhitu.admin.userinfo.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.UserSexLabelDto;

/**
 * <p>
 * 用户标签业务逻辑访问接口
 * </p>
 * 
 * 创建时间：2014-1-17
 * @author ztj
 *
 */
public interface UserLabelService extends BaseService {

	/**
	 * 保存用户标签
	 * 
	 * @param typeId
	 * @param file
	 * @throws Exception
	 */
	public void saveLabel(Integer sex, File file) throws Exception;
	
	/**
	 * 查询所有用户标签
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<UserSexLabelDto> getAllUserLabel() throws Exception;
	
	/**
	 * 构建用户标签id列表
	 * 
	 * @param userId
	 * @param jsonMap
	 * @throws Exception
	 */
	public void buildLabelId(Integer userId, Map<String, Object> jsonMap) throws Exception;
	
	/**
	 * 保存标签用户关联
	 * 
	 * @param userId
	 * @param labelIdsStr
	 * @param labels
	 * @param display 是否显示在用户资料
	 * @throws Exception
	 */
	public void saveLabelUser(Integer userId, String labelIdsStr, String labels, Boolean display) throws Exception;
}
