package com.imzhitu.admin.op.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.OpChannelMemberDto;
import com.imzhitu.admin.op.mapper.OpChannelMemberMapper;
import com.imzhitu.admin.op.service.OpChannelMemberService;

@Service
public class OpChannelMemberServiceImpl extends BaseServiceImpl implements OpChannelMemberService{
	
	@Autowired
	private OpChannelMemberMapper channelMemberMapper;
	
	@Autowired
	private com.hts.web.operations.service.ChannelService webChannelService;
	
	@Override
	public void insertChannelMember(Integer channelId, Integer userId,
			Integer degree) throws Exception {
		// TODO Auto-generated method stubd
		OpChannelMemberDto dto = new OpChannelMemberDto();
		Date now = new Date();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		
		long total = channelMemberMapper.queryChannelMemberTotalCount(dto);
		dto.setDegree(degree);
		dto.setSubTime(now.getTime());
		if(0 == total){
			channelMemberMapper.insertChannelMember(dto);
			webChannelService.updateMemberCount(channelId);
		}else{
			channelMemberMapper.updateChannelMemberDegree(dto);
		}			
	}

	@Override
	public void updateChannelMemberDegree(Integer id,Integer channelId, Integer userId,
			Integer degree) throws Exception {
		// TODO Auto-generated method stub
		if(id == null && (channelId == null || userId == null)){
			return ;
		}
		OpChannelMemberDto dto = new OpChannelMemberDto();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		dto.setDegree(degree);
		dto.setId(id);
		channelMemberMapper.updateChannelMemberDegree(dto);
		
	}

	@Override
	public long queryChannelMemberTotalCount(Integer id, Integer channelId,
			Integer userId, Integer degree) throws Exception {
		// TODO Auto-generated method stub
		OpChannelMemberDto dto = new OpChannelMemberDto();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		dto.setDegree(degree);
		dto.setId(id);
		return channelMemberMapper.queryChannelMemberTotalCount(dto);
	}

}
