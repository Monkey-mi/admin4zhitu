package com.imzhitu.admin.op.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.OpUserShieldDto;
import com.imzhitu.admin.op.mapper.OpUserShieldMapper;
import com.imzhitu.admin.op.service.OpUserShieldService;

@Service
public class OpUserShieldServiceImpl extends BaseServiceImpl implements OpUserShieldService{
	@Autowired
	private OpUserShieldMapper userShieldMapper;
	
	/**
	 * 增加
	 * @param userId
	 * @param operatorId
	 * @param valid
	 */
	@Override
	public void addUserShield(Integer userId,Integer operatorId,Integer valid)throws Exception{
		OpUserShieldDto dto = new OpUserShieldDto();
		Date now = new Date();
		dto.setUserId(userId);
		dto.setValid(valid);
		dto.setOperatorId(operatorId);
		dto.setAddDate(now);
		dto.setModifyDate(now);
		userShieldMapper.addUserShield(dto);
	}
	
	/**
	 * 批量删除
	 * @param idsStr
	 * @throws Exception
	 */
	@Override
	public void delUserShield(String idsStr)throws Exception{
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		userShieldMapper.delUserShield(ids);
	}
	
	/**
	 * 修改
	 * @param id
	 * @param userId
	 * @param valid
	 * @param operatorId
	 * @throws Exception
	 */
	@Override
	public void updateUserShield(Integer id,Integer userId,Integer valid,Integer operatorId)throws Exception{
		OpUserShieldDto dto = new OpUserShieldDto();
		Date now = new Date();
		dto.setId(id);
		dto.setUserId(userId);
		dto.setValid(valid);
		dto.setOperatorId(operatorId);
		dto.setModifyDate(now);
		userShieldMapper.updateUserShield(dto);
	}
	
	
	/**
	 * 分页查询
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param id
	 * @param userId
	 * @param valid
	 * @param jsonMap
	 * @throws Exception
	 */
	@Override
	public void queryUserShieldForList(Integer maxId, int start, int limit, 
			Integer id, Integer userId, Integer valid, Map<String, Object> jsonMap)throws Exception{
		OpUserShieldDto dto = new OpUserShieldDto();
		dto.setId(id);
		dto.setUserId(userId);
		dto.setValid(valid);
		buildNumberDtos(dto,start,limit,jsonMap,new NumberDtoListAdapter<OpUserShieldDto>(){
			@Override
			public List< ? extends AbstractNumberDto> queryList(OpUserShieldDto dto){
				return userShieldMapper.queryUserShieldForList(dto);
			}
			
			@Override
			public long queryTotal(OpUserShieldDto dto){
				return userShieldMapper.queryUserShieldCount(dto);
			}
		});
	}
	
}
