package com.imzhitu.admin.op.service.impl;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.imzhitu.admin.common.pojo.OpChannelLink;
import com.imzhitu.admin.op.mapper.OpChannelLinkMapper;
import com.imzhitu.admin.op.service.OpChannelLinkService;

@Service
public class OpChannelLinkServiceImpl extends BaseServiceImpl implements OpChannelLinkService{

	@Autowired
	private OpChannelLinkMapper opChannelLinkMapper;
	
	@Autowired
	private com.hts.web.common.service.KeyGenService keyGenService;
	
	@Override
	public void insertOpChannelLink(Integer channelId, Integer linkId)
			throws Exception {
		// TODO Auto-generated method stub
		if( null == channelId || null == linkId){
			throw new Exception("channelId or linkId cannot be null");
		}
		OpChannelLink dto = new OpChannelLink();
		Integer serial = keyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_LINK_SERIAL);
		dto.setSerial(serial);
		dto.setChannelId(channelId);
		dto.setLinkChannelId(linkId);
		long total = opChannelLinkMapper.queryOpChannelLinkTotalCount(dto);
		if(0 == total){
			opChannelLinkMapper.insertOpChannelLink(dto);
		}else{
			throw new Exception("已经存在相对应的频道关联关系");
		}
	}

	@Override
	public void deleteOpChannelLink(Integer channelId, Integer linkId)
			throws Exception {
		// TODO Auto-generated method stub
		OpChannelLink dto = new OpChannelLink();
		dto.setChannelId(channelId);
		dto.setLinkChannelId(linkId);
		opChannelLinkMapper.deleteOpChannelLink(dto);
	}

	@Override
	public long queryOpChannelLinkTotalCount(Integer maxId, Integer channelId,
			Integer linkId) throws Exception {
		// TODO Auto-generated method stub
		OpChannelLink dto = new OpChannelLink();
		dto.setChannelId(channelId);
		dto.setLinkChannelId(linkId);
		dto.setMaxId(maxId);
		return opChannelLinkMapper.queryOpChannelLinkTotalCount(dto);
	}

	@Override
	public void queryOpChannelLink(Integer maxId, int page, int rows,
			Integer channelId, Integer linkId, Map<String, Object> jsonMap)
			throws Exception {
		// TODO Auto-generated method stub
		OpChannelLink dto = new OpChannelLink();
		dto.setChannelId(channelId);
		dto.setLinkChannelId(linkId);
		dto.setMaxId(maxId);
		dto.setFirstRow((page - 1) * rows);
		dto.setLimit(rows);
		
		long total = opChannelLinkMapper.queryOpChannelLinkTotalCount(dto);
		List<OpChannelLink> list = opChannelLinkMapper.queryOpChannelLink(dto);
		Integer reMaxId = 0;
		if(list != null && list.size() > 0){
			reMaxId = list.get(0).getSerial();
		}
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
	}
	
	
	/**
	 * 批量删除
	 * @param rowJson
	 * @throws Exception
	 */
	@Override
	public void batchDeleteOpChannelLink(String rowJson)throws Exception{
		JSONArray jsArray = JSONArray.fromObject(rowJson);
		OpChannelLink dto = new OpChannelLink();
		for(int i=0; i<jsArray.size(); i++){
			JSONObject jsObj = jsArray.getJSONObject(i);
			Integer channelId = jsObj.optInt("channelId");
			Integer linkId = jsObj.optInt("linkChannelId");
			dto.setChannelId(channelId);
			dto.setLinkChannelId(linkId);
			opChannelLinkMapper.deleteOpChannelLink(dto);
		}
	}

}
