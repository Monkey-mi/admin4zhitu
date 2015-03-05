package com.imzhitu.admin.op.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.OpChannelIdVerifyIdDto;
import com.imzhitu.admin.op.mapper.ChannelIdVerifyIdMapper;
import com.imzhitu.admin.op.service.OpChannelIdVerifyIdService;

@Service
public class OpChannelIdVerifyIdServiceImpl extends BaseServiceImpl implements OpChannelIdVerifyIdService{
	
	@Autowired
	private ChannelIdVerifyIdMapper channelIdVerifyIdMapper;
	
	/**
	 * 插入
	 * @param dto
	 */
	@Override
	public void insertChannelIdVerifyId(Integer channelId,Integer verifyId)throws Exception{
		OpChannelIdVerifyIdDto dto = new OpChannelIdVerifyIdDto();
		dto.setChannelId(channelId);
		dto.setVerifyId(verifyId);
		long result = channelIdVerifyIdMapper.checkIsExistByVerifyId(dto);
		if(result == 0){
			channelIdVerifyIdMapper.insertChannelIdVerifyId(dto);
		}else{
			throw new Exception("插入失败，该认证与频道的对应关系已经存在");
		}
	}
	
	/**
	 * 检查是否存在与verifyId对应的channelId
	 * @param dto
	 * @return
	 */
	@Override
	public boolean checkIsExistByVerifyId(Integer verifyId)throws Exception{
		OpChannelIdVerifyIdDto dto = new OpChannelIdVerifyIdDto();
		dto.setVerifyId(verifyId);
		long result = channelIdVerifyIdMapper.checkIsExistByVerifyId(dto);
		return result != 0;
	}
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@Override
	public void batchDeleteChannelIdVerifyId(String idsStr)throws Exception{
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		channelIdVerifyIdMapper.batchDeleteChannelIdVerifyId(ids);
	}
	
	
	/**
	 * 根据verifyId来查询channelId
	 * @param dto
	 * @return
	 */
	@Override
	public Integer queryChannelIdByVerifyId(Integer verifyId)throws Exception{
		try{
			OpChannelIdVerifyIdDto dto = new OpChannelIdVerifyIdDto();
			dto.setVerifyId(verifyId);
			return channelIdVerifyIdMapper.queryChannelIdByVerifyId(dto);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
		
	}
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@Override
	public void queryChannelIdVerifyIdForList(int maxId, int start, int limit,Map<String, Object> jsonMap)throws Exception{
		OpChannelIdVerifyIdDto dto = new OpChannelIdVerifyIdDto();
		buildNumberDtos(dto,start,limit,jsonMap,new NumberDtoListAdapter<OpChannelIdVerifyIdDto>(){
			@Override
			public List< ? extends AbstractNumberDto> queryList(OpChannelIdVerifyIdDto dto){
				try{
					return channelIdVerifyIdMapper.queryChannelIdVerifyIdForList(dto);
				}catch(EmptyResultDataAccessException e){
					return null;
				}
			}
			
			@Override
			public long queryTotal(OpChannelIdVerifyIdDto dto){
				return channelIdVerifyIdMapper.queryChannelIdVerifyIdCount(dto);
			}
		});
	}
}
