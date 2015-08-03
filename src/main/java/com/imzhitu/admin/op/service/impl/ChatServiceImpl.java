package com.imzhitu.admin.op.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.ChatDTO;
import com.imzhitu.admin.op.mapper.ChatMapper;
import com.imzhitu.admin.op.service.ChatService;

/**
 * @author zhangbo 2015年8月3日
 *
 */
@Service
public class ChatServiceImpl extends BaseServiceImpl implements ChatService {

	@Autowired
	private ChatMapper mapper;

	@Override
	public void buildChatList(Integer page, Integer rows, Map<String, Object> jsonMap) throws Exception {
		ChatDTO dto = new ChatDTO();
		dto.setFirstRow(page);
		dto.setLimit(rows);

		buildNumberDtos(dto, page, rows, jsonMap, new NumberDtoListAdapter<ChatDTO>() {
			@Override
			public long queryTotal(ChatDTO dto) {
				return mapper.queryChatCount(dto);
			}

			@Override
			public List<? extends AbstractNumberDto> queryList(ChatDTO dto) {
				return mapper.queryChatList(dto);
			}

		});
	}

	@Override
	public void deleteChats(Integer[] ids) throws Exception {
		mapper.deleteChats(ids);
	}

}
