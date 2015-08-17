package com.imzhitu.admin.op.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.OpChannelMemberDto;
import com.imzhitu.admin.op.mapper.OpChannelMemberMapper;
import com.imzhitu.admin.op.service.OpChannelMemberService;

@Service
public class OpChannelMemberServiceImpl extends BaseServiceImpl implements OpChannelMemberService {

	@Autowired
	private OpChannelMemberMapper channelMemberMapper;

	@Autowired
	private com.hts.web.operations.service.ChannelService webChannelService;

	@Override
	public void insertChannelMember(Integer channelId, Integer userId, Integer degree) throws Exception {
		OpChannelMemberDto dto = new OpChannelMemberDto();
		Date now = new Date();
		dto.setChannelId(channelId);
		dto.setUserId(userId);

		long total = channelMemberMapper.queryChannelMemberTotalCount(dto);
		dto.setDegree(degree);
		dto.setSubTime(now.getTime());
		if (0 == total) {
			channelMemberMapper.insertChannelMember(dto);
			webChannelService.updateMemberCount(channelId);
		} else {
			channelMemberMapper.updateChannelMemberDegree(dto);
		}
	}

	@Override
	public void updateChannelMemberDegree(Integer channelId, Integer userId, Integer degree) throws Exception {
		if (channelId == null || userId == null) {
			return;
		}
		OpChannelMemberDto dto = new OpChannelMemberDto();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		dto.setDegree(degree);
		channelMemberMapper.updateChannelMemberDegree(dto);

	}

	@Override
	public void queryChannelMember(Integer channelId, Integer userId, Integer userStarId, Integer notified, Integer shield, Integer maxId, int page, int rows, Map<String, Object> jsonMap) throws Exception {
		OpChannelMemberDto dto = new OpChannelMemberDto();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		dto.setStar(userStarId);
		dto.setNotified(notified);
		dto.setShield(shield);

		buildNumberDtos(dto, page, rows, jsonMap, new NumberDtoListAdapter<OpChannelMemberDto>() {
			@Override
			public long queryTotal(OpChannelMemberDto dto) {
				return channelMemberMapper.queryChannelMemberTotalCount(dto);
			}

			@Override
			public List<? extends AbstractNumberDto> queryList(OpChannelMemberDto dto) {
				return channelMemberMapper.queryChannelMember(dto);
			}
		}, new NumberDtoListMaxIdAdapter() {

			@Override
			public Serializable getMaxId(List<? extends Serializable> list) throws Exception {
				return channelMemberMapper.getChannelMemberMaxId();
			}

		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.imzhitu.admin.op.service.OpChannelMemberService#queryChannelMemberByUserName(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.util.Map)
	 */
	@Override
	public void queryChannelMemberByUserName(Integer channelId, Integer userId, Integer page, Integer rows, Map<String, Object> jsonMap) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveChannelStar(Integer channelMemberId) {
		OpChannelMemberDto memberDto = channelMemberMapper.queryChannelMemberById(channelMemberId);
		
		// 若不是红人，则插入到频道红人表中
		if ( !memberDto.isChannelStar() ) {
			// 因为频道成员与频道红人关键字段id，channel_id，user_id相同，所以直接使用dto进行插入操作
			channelMemberMapper.insertChannelStar(memberDto);
			
			// 设置channel_star为1，并刷新频道成员为红人
			memberDto.setChannelStar(1);
			channelMemberMapper.updateChannelMember(memberDto);
		}
		
	}

	@Override
	public void deleteChannelStars(Integer[] channelMemberIds) {
		channelMemberMapper.deleteChannelStarByIds(channelMemberIds);
	}

}
