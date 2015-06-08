package com.imzhitu.admin.op.service.impl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.aliyun.service.OsChannelService;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.OpChannel;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.aliyun.service.OpenSearchService;
import com.imzhitu.admin.common.pojo.AdminAndUserRelationshipDto;
import com.imzhitu.admin.common.pojo.OpChannelV2Dto;
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.op.mapper.ChannelWorldMapper;
import com.imzhitu.admin.op.mapper.OpChannelV2Mapper;
import com.imzhitu.admin.op.service.OpChannelMemberService;
import com.imzhitu.admin.op.service.OpChannelV2Service;
import com.imzhitu.admin.userinfo.service.AdminAndUserRelationshipService;

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
	
	@Autowired
	private AdminAndUserRelationshipService adminAndUserRelationshipService;
	
	@Autowired
	private ChannelWorldMapper channelWorlMapper;
	
	@Autowired
	private com.hts.web.operations.service.ChannelService webChannelService;
	
	@Autowired
	private OpChannelMemberService channelMemberService;
	
	@Override
	public void insertOpChannel(Integer ownerId, String channelName,
			String channelTitle, String subtitle, String channelDesc,
			String channelIcon, String subIcon, Integer channelTypeId,
			String channelLabelNames, String channelLabelIds,
			Integer worldCount, Integer worldPictureCount, Integer memberCount,
			Integer superbCount, Integer childCountBase, Integer superb,
			Integer valid, Integer serial, Integer danmu, Integer moodFlag,
			Integer worldFlag,Integer themeId) throws Exception{
		// TODO Auto-generated method stub
		OpChannelV2Dto dto = new OpChannelV2Dto();
		Date now = new Date();
		Integer channelId = keyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_ID);
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
		dto.setThemeId(themeId);
		dto.setCreateTime(now.getTime());
		dto.setLastModifiedTime(now.getTime());
		
		opChannelV2Mapper.insertOpChannel(dto);
		//新增频道的同时须将该频道拥有着关注该频道
		channelMemberService.insertChannelMember(channelId, ownerId, Tag.TRUE);
		
//		osChannelService.saveChannelAtOnce(channelId, ownerId, channelName, channelTitle, subtitle, channelDesc, 
//				channelIcon, subIcon, channelTypeId, channelLabelNames,channelLabelIds ,danmu, moodFlag, worldFlag);
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
			Integer worldFlag,Integer themeId) throws Exception{
		// TODO Auto-generated method stub
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		List<OpChannelV2Dto> list = opChannelV2Mapper.queryOpChannel(dto);
		
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
		dto.setThemeId(themeId);
		
		opChannelV2Mapper.updateOpChannel(dto);
		
		//若是修改了所有者，则修妖修改原拥有着的degree，和现拥有者的degree
		if(list.size() == 1){
			if(list.get(0).getOwnerId() == ownerId){
				return;
			}
			//原拥有者degree设置为0
			channelMemberService.updateChannelMemberDegree(null, channelId, list.get(0).getOwnerId(), Tag.FALSE);
			
			//新增频道的同时须将该频道拥有着关注该频道
			channelMemberService.insertChannelMember(channelId, ownerId, Tag.TRUE);
		}
	}

	@Override
	public void queryOpChannel(Integer channelId,String channelName,Integer channelTypeId,
			Integer ownerId, Integer superb, Integer valid, Integer serial,
			Integer danmu, Integer moodFlag, Integer worldFlag,Integer themeId, int start,
			int rows, Integer maxId,Map<String, Object> jsonMap) throws Exception{
		// TODO Auto-generated method stub
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		dto.setChannelName(channelName);
		dto.setChannelTypeId(channelTypeId);
		dto.setOwnerId(ownerId);
		dto.setSuperb(superb);
		dto.setValid(valid);
		dto.setSerial(serial);
		dto.setDanmu(danmu);
		dto.setMoodFlag(moodFlag);
		dto.setWorldFlag(worldFlag);
		dto.setMaxId(maxId);
		dto.setThemeId(themeId);
		
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
	public long queryOpChannelTotalCount(Integer channelId,String channelName, Integer channelTypeId,Integer ownerId,
			Integer superb, Integer valid, Integer serial, Integer danmu,
			Integer moodFlag, Integer worldFlag,Integer maxId,Integer themeId) throws Exception{
		// TODO Auto-generated method stub
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		dto.setChannelName(channelName);
		dto.setChannelTypeId(channelTypeId);
		dto.setOwnerId(ownerId);
		dto.setSuperb(superb);
		dto.setValid(valid);
		dto.setSerial(serial);
		dto.setDanmu(danmu);
		dto.setMoodFlag(moodFlag);
		dto.setWorldFlag(worldFlag);
		dto.setMaxId(maxId);
		dto.setThemeId(themeId);
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
//					byte[] b = label.getBytes("iso-8859-1");
//					String labelUTF_8 = new String(b,"utf-8");
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
	public OpChannelV2Dto queryOpChannelByIdOrName(Integer id,String channelName)throws Exception{
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(id);
		dto.setChannelName(channelName);
		List<OpChannelV2Dto> list = opChannelV2Mapper.queryOpChannel(dto);
		if(list != null && list.size() == 1){
			OpChannelV2Dto o = list.get(0);
			long yestodayMemberIncreasement =  queryYestodayMemberIncreasement(null,null,o.getChannelId());
			long yestodayWorldIncreasement  =  queryYestodayWorldIncreasement(null, null, o.getChannelId());
			o.setYestodayMemberIncreasement(yestodayMemberIncreasement);
			o.setYestodayWorldIncreasement(yestodayWorldIncreasement);
			return o;
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
	
	/**
	 * 批量更新
	 */
	@Override
	public void batchUpdateValid(String channelIdsStr,Integer valid)throws Exception{
		if(channelIdsStr == null || "".equals(channelIdsStr.trim())){
			return ;
		}
		Integer [] channelIds = StringUtil.convertStringToIds(channelIdsStr);
		List<OpChannel> list = new ArrayList<OpChannel>(channelIds.length);
		for(int i=0; i<channelIds.length;i++){
			updateValid(channelIds[i],valid);
			OpChannelV2Dto d = queryOpChannelByIdOrName(channelIds[i],null);
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
	
	/**
	 * 根据管理员账号查询其对应的频道
	 * 
	 * @param adminUserId
	 * @param jsonMap
	 * @throws Exception
	 */
	@Override
	public void queryOpChannelByAdminUserId(Integer channelId,String channelName,Integer channelTypeId,Integer adminUserId,Map<String,Object>jsonMap)throws Exception{
		List<AdminAndUserRelationshipDto> relationshipList = adminAndUserRelationshipService.getAllAdminAndUserRelationshipByRole(adminUserId);
		Integer[] ownerIdArray = new Integer[relationshipList.size()];
		for(int i=0; i<relationshipList.size(); i++){
			ownerIdArray[i] = relationshipList.get(i).getUserId();
		}
		List<OpChannelV2Dto>channelList = opChannelV2Mapper.queryOpChannelByOwnerIds(ownerIdArray);
		List<OpChannelV2Dto>resultList = new ArrayList<OpChannelV2Dto>();
		if(channelList != null && channelList.size()>0){
			if(channelId != null ){
				for(OpChannelV2Dto dto:channelList){
					if(dto.getChannelId() == channelId){
						resultList.add(dto);
						break;
					}
				}
			}else if(channelName != null){
				for(OpChannelV2Dto dto:channelList){
					if(dto.getChannelName() == channelName){
						resultList.add(dto);
						break;
					}
				}
			}else if(channelTypeId != null){
				for(OpChannelV2Dto dto:channelList){
					if(dto.getChannelTypeId() == channelTypeId){
						resultList.add(dto);
						break;
					}
				}
			}else{
				resultList.addAll(channelList);
			}
		}
		
		
		jsonMap.put(OptResult.ROWS, resultList);
		jsonMap.put(OptResult.TOTAL, resultList.size());
	}
	
	
	/**
	 * 批量插入织图到频道
	 * @param channelId
	 * @param worldAndAuthorIds
	 * @throws Exception
	 */
	@Override
	public void batchInsertWorldToChannel(Integer channelId,String worldAndAuthorIds)throws Exception{
		if(channelId == null || worldAndAuthorIds == null){
			return;
		}
		Date now = new Date();
		String[] worldAndAuthors = worldAndAuthorIds.trim().split(",");
		for(int i=0; i<worldAndAuthors.length; i++){
			String[] worldAndAuthor = worldAndAuthors[i].split("-");
			if(worldAndAuthor.length != 2){
				continue;
			}
			Integer worldId = Integer.parseInt(worldAndAuthor[0]);
			Integer authorId = Integer.parseInt(worldAndAuthor[1]);
			
			//先查询是否重复
			OpChannelWorld channelWorld = new OpChannelWorld();
			channelWorld.setWorldId(worldId);
			
			long r = channelWorlMapper.queryChannelWorldCount(channelWorld);
			if(r != 0){
				continue;
			}
			
			Integer channelWorldId = keyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_WORLD_ID);
			
			channelWorld.setChannelId(channelId);
			channelWorld.setAuthorId(authorId);
			channelWorld.setDateAdded(now);
			channelWorld.setId(channelWorldId);
			channelWorld.setNotified(Tag.FALSE);
			channelWorld.setValid(Tag.FALSE);
			channelWorld.setSuperb(Tag.FALSE);
			channelWorld.setWeight(0);
			channelWorld.setSerial(channelWorldId);
			channelWorlMapper.save(channelWorld);
		}
		
		webChannelService.updateWorldAndChildCount(channelId);
		
	}
	
	/**
	 * 查询昨天新增成员数，不包含马甲
	 * @param yestodayTime
	 * @param todayTime
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public long queryYestodayMemberIncreasement(Long yestodayTime, Long todayTime,Integer  channelId)throws Exception{
		Date now = new Date();
		DateFormat df = new  SimpleDateFormat("yyyy-MM-dd");
		Date today = df.parse(df.format(now));
		if(yestodayTime == null ){
			yestodayTime = today.getTime() - 24*60*60*1000;
		}
		if(todayTime == null){
			todayTime = today.getTime();
		}
		return opChannelV2Mapper.queryYestodayMemberIncreasement(yestodayTime, todayTime, channelId);
	}
	
	/**
	 * 查询昨天新增织图数，不包含马甲
	 * @param yestoday
	 * @param today
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public long queryYestodayWorldIncreasement(Date yestoday, Date today,Integer  channelId)throws Exception{
		Date now = new Date();
		DateFormat df = new  SimpleDateFormat("yyyy-MM-dd");
		Date today1 = df.parse(df.format(now));
		if(yestoday == null ){
			yestoday = new Date(today1.getTime() - 24*60*60*1000);
		}
		if(today == null){
			today = today1;
		}
		return opChannelV2Mapper.queryYestodayWorldIncreasement(yestoday, today, channelId);
	}

}
