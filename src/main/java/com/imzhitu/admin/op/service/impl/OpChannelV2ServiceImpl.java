package com.imzhitu.admin.op.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.aliyun.service.OsChannelService;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.aliyun.service.OpenSearchService;
import com.imzhitu.admin.common.pojo.OpChannelV2Dto;
import com.imzhitu.admin.op.mapper.OpChannelV2Mapper;
import com.imzhitu.admin.op.service.OpChannelV2Service;

@Service
public class OpChannelV2ServiceImpl extends BaseServiceImpl implements OpChannelV2Service{

	@Autowired
	private OpChannelV2Mapper opChannelV2Mapper;
	
	@Autowired
	private OpenSearchService openSearchService;
	
	@Autowired
	private com.hts.web.common.service.KeyGenService keyGenService;
	
	@Autowired
	private OsChannelService osChannelService;
	
	@Override
	public void insertOpChannel(Integer ownerId, String channelName,
			String channelTitle, String subtitle, String channelDesc,
			String channelIcon, String subIcon, Integer channelTypeId,
			String channelLabelNames, String channelLabelIds,
			Integer worldCount, Integer worldPictureCount, Integer memberCount,
			Integer superbCount, Integer childCountBase, Integer superb,
			Integer valid, Integer serial, Integer danmu, Integer moodFlag,
			Integer worldFlag) throws Exception{
		// TODO Auto-generated method stub
//		OpChannelV2Dto dto = new OpChannelV2Dto();
		Integer channelId = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_ID);
//		dto.setChannelId(channelId);
//		dto.setOwnerId(ownerId);
//		dto.setChannelName(channelName);
//		dto.setChannelTitle(channelTitle);
//		dto.setSubtitle(subtitle);
//		dto.setChannelDesc(channelDesc);
//		dto.setChannelIcon(channelIcon);
//		dto.setSubIcon(subIcon);
//		dto.setChannelTypeId(channelTypeId);
//		dto.setChannelLabelNames(channelLabelNames);
//		dto.setChannelLabelIds(channelLabelIds);
//		dto.setWorldCount(worldCount);
//		dto.setWorldPictureCount(worldPictureCount);
//		dto.setMemberCount(memberCount);
//		dto.setSuperbCount(superbCount);
//		dto.setChildCountBase(childCountBase);
//		dto.setSuperb(superb);
//		dto.setValid(valid);
//		dto.setSerial(serial);
//		dto.setDanmu(danmu);
//		dto.setMoodFlag(moodFlag);
//		dto.setWorldFlag(worldFlag);
//		
//		opChannelV2Mapper.insertOpChannel(dto);
		
		osChannelService.saveChannelAtOnce(channelId, ownerId, channelName, channelTitle, subtitle, channelDesc, 
				channelIcon, subIcon, channelTypeId, channelLabelNames,channelLabelIds ,danmu, moodFlag, worldFlag);
	}

	@Override
	public void deleteOpChannel(Integer channelId, Integer ownerId) throws Exception{
		// TODO Auto-generated method stub
		if(null == channelId && null == ownerId)
			return;
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setOwnerId(ownerId);
		dto.setChannelId(channelId);
		opChannelV2Mapper.deleteOpChannel(dto);
	}

	@Override
	public void updateOpChannel(Integer channelId, Integer ownerId,
			String channelName, String channelTitle, String subtitle,
			String channelDesc, String channelIcon, String subIcon,
			Integer channelTypeId, String channelLabelNames,
			String channelLabelIds, Integer worldCount,
			Integer worldPictureCount, Integer memberCount,
			Integer superbCount, Integer childCountBase, Integer superb,
			Integer valid, Integer serial, Integer danmu, Integer moodFlag,
			Integer worldFlag) throws Exception{
		// TODO Auto-generated method stub
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		dto.setOwnerId(ownerId);
		dto.setChannelName(channelName);
		dto.setChannelTitle(channelTitle);
		dto.setSubtitle(subtitle);
		dto.setChannelDesc(channelDesc);
		dto.setChannelIcon(channelIcon);
		dto.setSubIcon(subIcon);
		dto.setChannelTypeId(channelTypeId);
		dto.setChannelLabelNames(channelLabelNames);
		dto.setChannelLabelIds(channelLabelIds);
		dto.setWorldCount(worldCount);
		dto.setWorldPictureCount(worldPictureCount);
		dto.setMemberCount(memberCount);
		dto.setSuperbCount(superbCount);
		dto.setChildCountBase(childCountBase);
		dto.setSuperb(superb);
		dto.setValid(valid);
		dto.setSerial(serial);
		dto.setDanmu(danmu);
		dto.setMoodFlag(moodFlag);
		dto.setWorldFlag(worldFlag);
		
		opChannelV2Mapper.updateOpChannel(dto);
	}

	@Override
	public void queryOpChannel(Integer channelId,
			Integer ownerId, Integer superb, Integer valid, Integer serial,
			Integer danmu, Integer moodFlag, Integer worldFlag, int start,
			int rows, Integer maxId,Map<String, Object> jsonMap) throws Exception{
		// TODO Auto-generated method stub
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		dto.setOwnerId(ownerId);
		dto.setSuperb(superb);
		dto.setValid(valid);
		dto.setSerial(serial);
		dto.setDanmu(danmu);
		dto.setMoodFlag(moodFlag);
		dto.setWorldFlag(worldFlag);
		dto.setMaxId(maxId);
		
		buildNumberDtos("getChannelId",dto,start,rows,jsonMap,new NumberDtoListAdapter<OpChannelV2Dto>(){

			@Override
			public List<? extends Serializable> queryList(OpChannelV2Dto dto) {
				// TODO Auto-generated method stub
				return opChannelV2Mapper.queryOpChannel(dto);
			}

			@Override
			public long queryTotal(OpChannelV2Dto dto) {
				// TODO Auto-generated method stub
				return opChannelV2Mapper.queryOpChannelTotalCount(dto);
			}
			
		});
	}

	@Override
	public long queryOpChannelTotalCount(Integer channelId, Integer ownerId,
			Integer superb, Integer valid, Integer serial, Integer danmu,
			Integer moodFlag, Integer worldFlag,Integer maxId) throws Exception{
		// TODO Auto-generated method stub
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		dto.setOwnerId(ownerId);
		dto.setSuperb(superb);
		dto.setValid(valid);
		dto.setSerial(serial);
		dto.setDanmu(danmu);
		dto.setMoodFlag(moodFlag);
		dto.setWorldFlag(worldFlag);
		dto.setMaxId(maxId);
		return opChannelV2Mapper.queryOpChannelTotalCount(dto);
	}
	
	@Override
	public JSONArray queryOpChannelLabel(String label)throws Exception{
		if(label == null || "".equals(label.trim()))
			return null;
		JSONObject jsonObj = openSearchService.queryChannelLabel(label);
		boolean flag = false;
		//若存在标签
		if(jsonObj != null && jsonObj.isNullObject() == false){
			JSONArray jsonArray = jsonObj.getJSONArray("items");
			if(jsonArray != null && jsonArray.isEmpty() == false){
				//若不存在完全匹配
				for(int i=0; i< jsonArray.size();i++){
					JSONObject jo = jsonArray.getJSONObject(i);
					if(label.equals(jo.opt("label_name"))){
						flag = true;
						break;
					}
				}
				
				if(flag == false){
					JSONObject tmpJO = new JSONObject();
					tmpJO.put("id", -1);
					tmpJO.put("label_name", "创建此标签:"+label);
					jsonArray.add(0, tmpJO);
				}
				return jsonArray;
			}
		}
		
		//没有标签，构建一个创建标签的提示
		jsonObj = new JSONObject();
		jsonObj.put("id", -1);
		jsonObj.put("label_name", "创建此标签:"+label);
		JSONArray jsArray = new JSONArray();
		jsArray.add(jsonObj);
		return jsArray;
	}
	
	
	/**
	 * 根据id查询频道
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public OpChannelV2Dto queryOpChannelById(Integer id)throws Exception{
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(id);
		List<OpChannelV2Dto> list = opChannelV2Mapper.queryOpChannel(dto);
		if(list != null && list.size() == 1){
			return list.get(0);
		}else {
			return null;
		}
		
	}
	
	
	public void updateValid(Integer  channelId,Integer valid)throws Exception{
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		dto.setValid(valid);
		opChannelV2Mapper.updateOpChannel(dto);
	}
	
	public void batchUpdateValid(String channelIdsStr,Integer valid)throws Exception{
		if(channelIdsStr == null || "".equals(channelIdsStr.trim())){
			return ;
		}
		Integer [] channelIds = StringUtil.convertStringToIds(channelIdsStr);
		List<OpChannel> list = new ArrayList<OpChannel>(channelIds.length);
		for(int i=0; i<channelIds.length;i++){
			updateValid(channelIds[i],valid);
			OpChannelV2Dto d = queryOpChannelById(channelIds[i]);
			if(null == d){
				continue;
			}
			OpChannel opChannel = new OpChannel();
			opChannel.setChannelIcon(d.getChannelIcon());
			opChannel.setChannelName(d.getChannelName());
			opChannel.setChannelTitle(d.getChannelTitle());
			opChannel.setChildCount(d.getWorldPictureCount());
			opChannel.setId(d.getChannelId());
			opChannel.setSerial(d.getSerial());
			opChannel.setSubIcon(d.getSubIcon());
			list.add(opChannel);
		}
		
		osChannelService.updateChannelAtOnce(list);
	}

}
