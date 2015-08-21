package com.imzhitu.admin.op.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.OpChannelUserDto;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.op.mapper.ChannelUserMapper;
import com.imzhitu.admin.op.service.OpChannelIdVerifyIdService;
import com.imzhitu.admin.op.service.OpChannelUserService;
import com.imzhitu.admin.userinfo.mapper.UserInfoMapper;

@Service
public class OpChannelUserServiceImpl extends BaseServiceImpl implements OpChannelUserService{
	
	private static Integer RANK_TOP_N = 15;		//频道红人数量
	
	@Autowired
	private ChannelUserMapper channelUserMapper;
	
	@Autowired
	private OpChannelIdVerifyIdService channelIdVerifyIdService;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	/**
	 * 查询前n个频道用户，计算公式：平均几天上精选*3+平均几天上频道*3-注册多少个周/2+最近发图时间*2。
	 * @param dto
	 * @return
	 */
	@Override
	public List<Integer> queryChannelUserRankTopN(Integer channelId)throws Exception{
		OpChannelUserDto dto = new OpChannelUserDto();
		dto.setLimit(RANK_TOP_N);
		dto.setChannelId(channelId);
		try{
			return channelUserMapper.queryChannelUserRankTopN(dto);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	/**
	 * 插入
	 * @param dto
	 */
	@Override
	public void insertChannelUser(Integer userId,Integer channelId,Integer valid,Integer operatorId)throws Exception{
		Date now = new Date();
		UserInfo userInfo = userInfoMapper.selectById(userId);
		OpChannelUserDto dto = new OpChannelUserDto();
		dto.setChannelId(channelId);
		dto.setOperatorId(operatorId);
		dto.setUserId(userId);
		dto.setValid(valid);
		dto.setAddDate(now);
		dto.setModifyDate(now);
		dto.setRegisterDate(userInfo.getRegisterDate());
		channelUserMapper.insertChannelUser(dto);
	}
	
	@Override
	public void addChannelUserByVerifyId(Integer userId,Integer verifyId,Integer valid,Integer operatorId)throws Exception{
		// TODO 这块入口不用了，要整改，要删除掉
		Integer channelId = channelIdVerifyIdService.queryChannelIdByVerifyId(verifyId);
		if(channelId == null) return;
		Date now = new Date();
		UserInfo userInfo = userInfoMapper.selectById(userId);
		OpChannelUserDto dto = new OpChannelUserDto();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		//检查是否已经存在
		long r = channelUserMapper.queryChannelUserCount(dto);
		if(r != 0)return ;
		
		
		dto.setOperatorId(operatorId);
		dto.setAddDate(now);
		dto.setModifyDate(now);
		dto.setValid(valid);
		dto.setRegisterDate(userInfo.getRegisterDate());
		channelUserMapper.insertChannelUser(dto);
	}
	
	@Override
	public void addChannelUserByWorldId(Integer worldId,Integer channelId,Integer valid,Integer operatorId)throws Exception{
		Integer userId = channelUserMapper.queryUserIdByWorldId(worldId);
		if(channelId == null) return;
		Date now = new Date();
		UserInfo userInfo = userInfoMapper.selectById(userId);
		OpChannelUserDto dto = new OpChannelUserDto();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		//检查是否已经存在
		long r = channelUserMapper.queryChannelUserCount(dto);
		if(r != 0)return ;
		
		
		dto.setOperatorId(operatorId);
		dto.setAddDate(now);
		dto.setModifyDate(now);
		dto.setValid(valid);
		dto.setRegisterDate(userInfo.getRegisterDate());
		channelUserMapper.insertChannelUser(dto);
	}
	
	/**
	 * 批量插入
	 * @param userIdsStr
	 * @param channelId
	 * @param operatorId
	 * @throws Exception
	 */
	public void batchInsertChannelUser(String userIdsStr,Integer channelId,Integer operatorId)throws Exception{
		Integer userIds[] = StringUtil.convertStringToIds(userIdsStr);
		for(int i=0; i<userIds.length; i++){
			insertChannelUser(userIds[i], channelId, Tag.TRUE, operatorId);
		}
	}
	
	/**
	 * 删除
	 * @param dto
	 */
	@Override
	public void deleteChannelUser(Integer id,Integer userId,Integer channelId,Integer valid,Integer operatorId)throws Exception{
		OpChannelUserDto dto = new OpChannelUserDto();
		dto.setId(id);
		dto.setChannelId(channelId);
		dto.setOperatorId(operatorId);
		dto.setUserId(userId);
		dto.setValid(valid);
		channelUserMapper.deleteChannelUser(dto);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @throws Exception
	 */
	public void deleteChannelUserByIds(String idsStr)throws Exception{
		Integer ids[] = StringUtil.convertStringToIds(idsStr);
		channelUserMapper.deleteChannelUserByIds(ids);
	}
	
	/**
	 * 更新Valid
	 * @param dto
	 */
	@Override
	public void updateChannelUserValid(Integer id,Integer userId,Integer channelId,Integer valid,Integer operatorId)throws Exception{
		Date now = new Date();
		OpChannelUserDto dto = new OpChannelUserDto();
		dto.setId(id);
		dto.setChannelId(channelId);
		dto.setOperatorId(operatorId);
		dto.setUserId(userId);
		dto.setValid(valid);
		dto.setModifyDate(now);
		channelUserMapper.updateChannelUserValid(dto);
	}
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	@Override
	public void queryChannelUserForList(OpChannelUserDto dto,int maxId,int start,int limit,Map<String ,Object> jsonMap)throws Exception{
		buildNumberDtos(dto,start,limit,jsonMap,new NumberDtoListAdapter<OpChannelUserDto>(){
			@Override
			public List< ? extends AbstractNumberDto> queryList(OpChannelUserDto dto){
				try{
					return channelUserMapper.queryChannelUserForList(dto);
				}catch(EmptyResultDataAccessException e){
					return null;
				}
			}
			
			@Override
			public long queryTotal(OpChannelUserDto dto){
				return channelUserMapper.queryChannelUserCount(dto);
			}
			
		});
	}
	
	
	/**
	 *  查询用户平均几天上精选*3 对应的分数 
	 * @param userId
	 * @return
	 */
	public Integer querySuperbScore(Integer userId)throws Exception{
		return channelUserMapper.querySuperbScore(userId);
	}
	
	/**
	 * 平均几天上频道*3 对应的分数
	 * @param userId
	 * @return
	 */
	public Integer queryChannelScore(Integer userId)throws Exception{
		return channelUserMapper.queryChannelScore(userId);
	}
	
	/**
	 * 注册多少个周/2
	 * @param userId
	 * @return
	 */
	public Integer queryRegisterScore(Integer userId)throws Exception{
		return channelUserMapper.queryRegisterScore(userId);
	}
	
	/**
	 * 最近发图时间*2
	 * @param userId
	 * @return
	 */
	public Integer queryLastWorldScore(Integer userId)throws Exception{
		return channelUserMapper.queryLastWorldScore(userId);
	}
}
