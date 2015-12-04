package com.imzhitu.admin.op.service.impl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.OpChannelLink;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.hts.web.common.util.TimeUtil;
import com.imzhitu.admin.aliyun.service.OpenSearchService;
import com.imzhitu.admin.channel.dao.ChannelCache;
import com.imzhitu.admin.channel.dao.ChannelThemeCache;
import com.imzhitu.admin.common.pojo.AdminAndUserRelationshipDto;
import com.imzhitu.admin.common.pojo.ChannelTheme;
import com.imzhitu.admin.common.pojo.OpChannelV2Dto;
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.common.pojo.OpDataStatisticsDto;
import com.imzhitu.admin.common.pojo.UserInfo;
import com.imzhitu.admin.op.dao.ChannelAutoRejectIdCacheDao;
import com.imzhitu.admin.op.mapper.ChannelWorldMapper;
import com.imzhitu.admin.op.mapper.OpChannelV2Mapper;
import com.imzhitu.admin.op.service.OpChannelMemberService;
import com.imzhitu.admin.op.service.OpChannelV2Service;
import com.imzhitu.admin.userinfo.mapper.UserInfoMapper;
import com.imzhitu.admin.userinfo.service.AdminAndUserRelationshipService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class OpChannelV2ServiceImpl extends BaseServiceImpl implements OpChannelV2Service {
	private static final Logger log = Logger.getLogger(OpChannelV2ServiceImpl.class);

	@Autowired
	private OpChannelV2Mapper opChannelV2Mapper;

	@Autowired
	private OpenSearchService openSearchService;

	@Autowired
	private com.hts.web.common.service.KeyGenService keyGenService;

	@Autowired
	private AdminAndUserRelationshipService adminAndUserRelationshipService;

	@Autowired
	private ChannelWorldMapper channelWorlMapper;

	@Autowired
	private com.hts.web.operations.service.ChannelService webChannelService;

	@Autowired
	private OpChannelMemberService channelMemberService;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private ChannelCache channelCache;

	@Autowired
	private com.hts.web.operations.dao.ChannelDao channelDao;

	@Autowired
	private com.hts.web.operations.dao.ChannelLinkDao channelLinkDao;

	@Autowired
	private ChannelAutoRejectIdCacheDao channelAutoPassIdCacheDao;
	
	@Autowired
	private com.imzhitu.admin.op.mapper.ChannelThemeMapper channelThemeMapper;
	
	@Autowired
	private ChannelThemeCache channelThemeCache;

	@Override
	public void insertOpChannel(String channelDesc, String channelIcon, String channelSubIcon, String channelBanner, String channelReview, String channelName, Integer channelTypeId, Integer ownerId, Integer themeId) throws Exception {
		OpChannelV2Dto dto = new OpChannelV2Dto();
		Date now = new Date();
		Integer channelId = keyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_ID);
		dto.setChannelId(channelId);
		dto.setOwnerId(ownerId);
		dto.setChannelName(channelName);
		dto.setChannelDesc(channelDesc);
		dto.setChannelIcon(channelIcon);
		dto.setChannelSubIcon(channelSubIcon);
		dto.setChannelBanner(channelBanner);
		dto.setChannelReview(channelReview);
		dto.setChannelTypeId(channelTypeId);
		dto.setThemeId(themeId);
		dto.setCreateTime(now.getTime());
		dto.setLastModifiedTime(now.getTime());
		// 由于客户端计算的需要，默认的serial为0会出现查询的问题，所以在创建时赋个初始值10
		dto.setSerial(10);

		opChannelV2Mapper.insertOpChannel(dto);
		
		// 新增频道的同时须将该频道拥有者关注该频道
		webChannelService.saveMember(channelId, ownerId);
		
		// 设置拥有者频道等级为owner，即1
		channelMemberService.updateChannelMemberDegree(channelId, ownerId, Tag.CHANNEL_MEMBER_ROLE_OWNER);
	}

	@Override
	public void deleteOpChannel(Integer channelId, Integer ownerId) throws Exception {
		if (null == channelId && null == ownerId)
			return;
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setOwnerId(ownerId);
		dto.setChannelId(channelId);
		opChannelV2Mapper.deleteOpChannel(dto);

	}

	@Override
	public void updateOpChannel(Integer channelId, String channelDesc, String channelIcon, String channelSubIcon, String channelBanner, String channelReview, String channelName, Integer channelTypeId, Integer ownerId, Integer themeId)
			throws Exception {
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		List<OpChannelV2Dto> list = opChannelV2Mapper.queryOpChannel(dto);

		dto.setOwnerId(ownerId);
		dto.setChannelName(channelName);
		dto.setChannelDesc(channelDesc);
		dto.setChannelIcon(channelIcon);
		dto.setChannelSubIcon(channelSubIcon);
		dto.setChannelBanner(channelBanner);
		dto.setChannelReview(channelReview);
		dto.setChannelTypeId(channelTypeId);
		dto.setThemeId(themeId);
		dto.setLastModifiedTime(new Date().getTime());

		opChannelV2Mapper.updateOpChannel(dto);

		// 若是修改了所有者，则修妖修改原拥有着的degree，和现拥有者的degree
		if (list.size() == 1 && ownerId != null) {
			if (list.get(0).getOwnerId() == ownerId) {
				return;
			}
			// 原拥有者degree设置为0
			channelMemberService.updateChannelMemberDegree(channelId, list.get(0).getOwnerId(), Tag.CHANNEL_MEMBER_ROLE_NORMAL);
			
			// 现拥有者degree设置为1
			channelMemberService.updateChannelMemberDegree(channelId, ownerId, Tag.CHANNEL_MEMBER_ROLE_OWNER);
		}
	}
	
	@Override
	public void updateOpChannelSuperb(Integer channelId, Integer superb) throws Exception {
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		dto.setSuperb(superb);
		opChannelV2Mapper.updateOpChannel(dto);
		
	}
	
	@Override
	public void updateOpChannelValid(Integer channelId, Integer valid) throws Exception {
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		dto.setValid(valid);
		opChannelV2Mapper.updateOpChannel(dto);
	}
	
	@Override
	public void updateOpChannelDanmu(Integer channelId, Integer danmu) throws Exception {
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		dto.setDanmu(danmu);
		opChannelV2Mapper.updateOpChannel(dto);
	}
	
	@Override
	public void updateOpChannelMoodFlag(Integer channelId, Integer moodFlag) throws Exception {
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		dto.setMoodFlag(moodFlag);
		opChannelV2Mapper.updateOpChannel(dto);
	}
	
	@Override
	public void updateOpChannelWorldFlag(Integer channelId, Integer worldFlag) throws Exception {
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		dto.setWorldFlag(worldFlag);
		opChannelV2Mapper.updateOpChannel(dto);
	}

	@Override
	public void queryOpChannel(Integer channelId, String channelName, Integer channelTypeId, Integer ownerId, Integer superb, Integer valid, Integer top, Integer serial, Integer danmu, Integer moodFlag, Integer worldFlag, Integer themeId, int start,
			int rows, Integer maxId, Map<String, Object> jsonMap) throws Exception {
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		dto.setChannelName(channelName);
		dto.setChannelTypeId(channelTypeId);
		dto.setOwnerId(ownerId);
		dto.setSuperb(superb);
		dto.setValid(valid);
		dto.setDanmu(danmu);
		dto.setMoodFlag(moodFlag);
		dto.setWorldFlag(worldFlag);
		dto.setMaxId(maxId);
		dto.setThemeId(themeId);
		dto.setTop(top);

		buildNumberDtos("getSerial", dto, start, rows, jsonMap, new NumberDtoListAdapter<OpChannelV2Dto>() {

			@Override
			public List<? extends Serializable> queryList(OpChannelV2Dto dto) {
				List<OpChannelV2Dto> opChannelList = opChannelV2Mapper.queryOpChannel(dto);
				try {
					// 为关联频道赋值
					for (OpChannelV2Dto opChannelV2Dto : opChannelList) {
						StringBuffer relatedChannel = new StringBuffer();
						List<OpChannelLink> queryRelatedChannelList = queryRelatedChannelList(opChannelV2Dto.getChannelId());
						for (int i = 0; i < queryRelatedChannelList.size(); i++) {
							if (i == 0) {
								relatedChannel.append(queryRelatedChannelList.get(i).getChannelName());
							} else {
								relatedChannel.append(",");
								relatedChannel.append(queryRelatedChannelList.get(i).getChannelName());
							}
						}
						opChannelV2Dto.setRelatedChannel(relatedChannel.toString());
					}
				} catch (Exception e) {
					log.error(e);
				}
				return opChannelList;
			}

			@Override
			public long queryTotal(OpChannelV2Dto dto) {
				return opChannelV2Mapper.queryOpChannelTotalCount(dto);
			}

		});
	}

	@Override
	public long queryOpChannelTotalCount(Integer channelId, String channelName, Integer channelTypeId, Integer ownerId, Integer superb, Integer valid, Integer serial, Integer danmu, Integer moodFlag, Integer worldFlag, Integer maxId, Integer themeId)
			throws Exception {
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
	public JSONArray queryOpChannelLabel(String label) throws Exception {
		if (label == null || "".equals(label.trim()))
			return null;
		JSONObject jsonObj = openSearchService.queryChannelLabel(label);
		boolean flag = false;
		// 若存在标签
		if (jsonObj != null && jsonObj.isNullObject() == false) {
			JSONArray jsonArray = jsonObj.getJSONArray("items");
			if (jsonArray != null && jsonArray.isEmpty() == false) {
				// 若不存在完全匹配
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jo = jsonArray.getJSONObject(i);
					if (label.equals(jo.opt("label_name"))) {
						flag = true;
						break;
					}
				}

				if (flag == false) {
					JSONObject tmpJO = new JSONObject();
					tmpJO.put("id", -1);
					// byte[] b = label.getBytes("iso-8859-1");
					// String labelUTF_8 = new String(b,"utf-8");
					tmpJO.put("label_name", "创建此标签:" + label);
					jsonArray.add(0, tmpJO);
				}
				return jsonArray;
			}
		}

		// 没有标签，构建一个创建标签的提示
		jsonObj = new JSONObject();
		jsonObj.put("id", -1);
		jsonObj.put("label_name", "创建此标签:" + label);
		JSONArray jsArray = new JSONArray();
		jsArray.add(jsonObj);
		return jsArray;
	}

	/**
	 * 根据id查询频道
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public OpChannelV2Dto queryOpChannelByIdOrName(Integer id, String channelName) throws Exception {
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(id);
		dto.setChannelName(channelName);
		List<OpChannelV2Dto> list = opChannelV2Mapper.queryOpChannel(dto);
		if (list != null && list.size() == 1) {
			OpChannelV2Dto o = list.get(0);
			// long yestodayMemberIncreasement = queryYestodayMemberIncreasement(null,null,o.getChannelId());
			OpDataStatisticsDto YestodayWorldAndMemberIncreasement = opChannelV2Mapper.queryYestodayWorldAndMemberIncreasement(o.getChannelId());
			
			// TODO 这块逻辑要重新梳理，这个是获取queryOpChannelByIdOrName是获取全量的信息， 要斟酌使用，要提供一个channel查询的基础类，然后根据不同业务需要再添加枝叶
			if (YestodayWorldAndMemberIncreasement != null) {
				long yestodayMemberIncreasement = YestodayWorldAndMemberIncreasement.getMemberAddCount();
				long yestodayWorldIncreasement = YestodayWorldAndMemberIncreasement.getWorldAddCount();
				o.setYestodayMemberIncreasement(yestodayMemberIncreasement);
				o.setYestodayWorldIncreasement(yestodayWorldIncreasement);
			}
			

			// 获取频道主信息
			if (o.getOwnerId() != null) {
				UserInfo u = userInfoMapper.selectById(o.getOwnerId());
				o.setUserName(u.getUserName());
				o.setUserAvatar(u.getUserAvatar());
				o.setUserAvatarL(u.getUserAvatarL());
			}
			return o;
		} else {
			return null;
		}

	}

	public void updateValid(Integer channelId, Integer valid) throws Exception {
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		dto.setValid(valid);
		opChannelV2Mapper.updateOpChannel(dto);
	}

	/**
	 * 根据管理员账号查询其对应的频道
	 * 
	 * @param adminUserId
	 * @param jsonMap
	 * @throws Exception
	 */
	@Override
	public void queryOpChannelByAdminUserId(Integer channelId, String channelName, Integer channelTypeId, Integer adminUserId, Map<String, Object> jsonMap) throws Exception {
		List<AdminAndUserRelationshipDto> relationshipList = adminAndUserRelationshipService.getAllAdminAndUserRelationshipByRole(adminUserId);
		Integer[] ownerIdArray = new Integer[relationshipList.size()];
		for (int i = 0; i < relationshipList.size(); i++) {
			ownerIdArray[i] = relationshipList.get(i).getUserId();
		}
		List<OpChannelV2Dto> channelList = opChannelV2Mapper.queryOpChannelByOwnerIds(ownerIdArray);
		List<OpChannelV2Dto> resultList = new ArrayList<OpChannelV2Dto>();
		if (channelList != null && channelList.size() > 0) {
			if (channelId != null) {
				for (OpChannelV2Dto dto : channelList) {
					if (dto.getChannelId() == channelId) {
						resultList.add(dto);
						break;
					}
				}
			} else if (channelName != null) {
				for (OpChannelV2Dto dto : channelList) {
					if (dto.getChannelName() == channelName) {
						resultList.add(dto);
						break;
					}
				}
			} else if (channelTypeId != null) {
				for (OpChannelV2Dto dto : channelList) {
					if (dto.getChannelTypeId() == channelTypeId) {
						resultList.add(dto);
						break;
					}
				}
			} else {
				resultList.addAll(channelList);
			}
		}

		jsonMap.put(OptResult.ROWS, resultList);
		jsonMap.put(OptResult.TOTAL, resultList.size());
	}

	/**
	 * 批量插入织图到频道
	 * 
	 * @param channelId
	 * @param worldAndAuthorIds
	 * @throws Exception
	 */
	@Override
	public void batchInsertWorldToChannel(Integer channelId, String worldAndAuthorIds) throws Exception {
		if (channelId == null || worldAndAuthorIds == null) {
			return;
		}
		Date now = new Date();
		String[] worldAndAuthors = worldAndAuthorIds.trim().split(",");
		for (int i = 0; i < worldAndAuthors.length; i++) {
			String[] worldAndAuthor = worldAndAuthors[i].split("-");
			if (worldAndAuthor.length != 2) {
				continue;
			}
			Integer worldId = Integer.parseInt(worldAndAuthor[0]);
			Integer authorId = Integer.parseInt(worldAndAuthor[1]);

			// 先查询是否重复
			OpChannelWorld channelWorld = new OpChannelWorld();
			channelWorld.setWorldId(worldId);

			long r = channelWorlMapper.queryChannelWorldCount(channelWorld);
			if (r != 0) {
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
	 * 
	 * @param yestodayTime
	 * @param todayTime
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public long queryYestodayMemberIncreasement(Long yestodayTime, Long todayTime, Integer channelId) throws Exception {
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date today = df.parse(df.format(now));
		if (yestodayTime == null) {
			yestodayTime = today.getTime() - 24 * 60 * 60 * 1000;
		}
		if (todayTime == null) {
			todayTime = today.getTime();
		}
		return opChannelV2Mapper.queryYestodayMemberIncreasement(yestodayTime, todayTime, channelId);
	}

	@Override
	public void updateChannelCache() throws Exception {
		
		// 查询置顶频道，并且转换为客户端需要反序列化的com.hts.web.common.pojo.OpChannel对象
	    List<OpChannelV2Dto> topList = opChannelV2Mapper.queryChannelTop();
	    
	    List<com.hts.web.common.pojo.OpChannel> topTempList = new ArrayList<com.hts.web.common.pojo.OpChannel>();
	    for (OpChannelV2Dto opChannelV2Dto : topList) {
			topTempList.add(new com.hts.web.common.pojo.OpChannel(
					opChannelV2Dto.getChannelId(),
					opChannelV2Dto.getOwnerId(),
					opChannelV2Dto.getChannelName(),
					opChannelV2Dto.getChannelTitle(),
					opChannelV2Dto.getSubtitle(),
					opChannelV2Dto.getChannelDesc(),
					opChannelV2Dto.getChannelIcon(),
					opChannelV2Dto.getChannelSubIcon(),
					opChannelV2Dto.getChannelBanner(),
					opChannelV2Dto.getChannelTypeId(),
					opChannelV2Dto.getChannelLabelNames(),
					opChannelV2Dto.getChannelLabelIds(),
					opChannelV2Dto.getWorldCount(),
					opChannelV2Dto.getWorldPictureCount(),
					opChannelV2Dto.getMemberCount(),
					opChannelV2Dto.getSuperbCount(),
					TimeUtil.getDate(opChannelV2Dto.getCreateTime().longValue()),
					TimeUtil.getDate(opChannelV2Dto.getLastModifiedTime().longValue()),
					opChannelV2Dto.getSuperb(),
					opChannelV2Dto.getThemeId(),
					opChannelV2Dto.getSerial(),
					opChannelV2Dto.getDanmu(),
					opChannelV2Dto.getMoodFlag(),
					opChannelV2Dto.getWorldFlag(),
					opChannelV2Dto.getChannelReview()));
	    }
	    
	    // 只查询1000条数据
	    List<com.hts.web.common.pojo.OpChannel> superbList = channelDao.querySuperbChannel(1000);
	    
	    // 刷新频道（频道缓存中存储的为精选频道）与推荐频道（推荐频道中存储的为top频道）redis缓存
	    channelCache.updateChannel(superbList);
	    channelCache.updateRecommendChannel(topTempList);
	}

	/**
	 * @author zhangbo 2015年6月17日
	 */
	@Override
	public List<OpChannelLink> queryRelatedChannelList(Integer channelId) throws Exception {
		return channelLinkDao.queryLink(channelId);
	}

	/**
	 * @author zhangbo 2015年6月17日
	 */
	@Override
	public void addRelatedChannel(Integer channelId, Integer linkChannelId) throws Exception {
		Integer serial = keyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_LINK_SERIAL);
		;
		opChannelV2Mapper.addRelatedChannel(channelId, linkChannelId, serial);
	}

	/**
	 * @author zhangbo 2015年6月17日
	 */
	@Override
	public void deleteRelatedChannels(Integer channelId, Integer[] deleteIds) throws Exception {
		for (Integer linkId : deleteIds) {
			opChannelV2Mapper.deleteRelatedChannel(channelId, linkId);
		}
	}

	/**
	 * @author zhangbo 2015年6月17日
	 */
	@Override
	public void updateRelatedChannelSerial(Integer channelId, String[] linkChannelIds) throws Exception {
		// 反向排序，传递过来的集合，第一个是前台想排在前面的，而serial越大排序越靠前，所以要倒序对linkChannelIds进行操作
		for (int i = linkChannelIds.length - 1; i >= 0; i--) {
			if (linkChannelIds[i].isEmpty()) {
				continue;
			} else {
				Integer linkCid = Integer.parseInt(linkChannelIds[i]);
				Integer serial = keyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_LINK_SERIAL);
				opChannelV2Mapper.updateRelatedChannelSerial(channelId, linkCid, serial);
			}
		}
	}

	/**
	 * @author zhangbo 2015年6月17日
	 */
	@Override
	public void saveChannelTop(Integer channelId) throws Exception {
		opChannelV2Mapper.insertChannelTop(channelId);
	}

	/**
	 * @author zhangbo 2015年6月17日
	 */
	@Override
	public void deleteChannelTop(Integer channelId) throws Exception {
		opChannelV2Mapper.deleteChannelTop(channelId);
	}

	/**
	 * @author zhangbo 2015年6月17日
	 */
	@Override
	public List<Map<String, Object>> queryOpChannelLabelList(Integer channelId) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 根据频道ID查询结果集
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		List<OpChannelV2Dto> queryOpChannel = opChannelV2Mapper.queryOpChannel(dto);
		OpChannelV2Dto resultDto = queryOpChannel.get(0);

		// 获取结果集中标签id与标签名称，都为成对出现，且一一对应，标签id与标签名称都由“,”分隔
		String channelLabelIds = resultDto.getChannelLabelIds();
		Integer[] labelIds = StringUtil.convertStringToIds(channelLabelIds);
		String channelLabelNames = resultDto.getChannelLabelNames();
		String[] labelNames = StringUtil.convertStringToStrs(channelLabelNames);

		for (int i = 0; i < labelNames.length; i++) {
			// 定义存储标签id与标签名称的键值对Map
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("id", labelIds[i]);
			retMap.put("label_name", labelNames[i]);
			list.add(retMap);
		}
		return list;
	}

	/**
	 * @author zhangbo 2015年6月17日
	 */
	@Override
	public void updateOpChannelLabel(Integer channelId, String channelLabelIds, String channelLabelNames) throws Exception {
		OpChannelV2Dto dto = new OpChannelV2Dto();
		dto.setChannelId(channelId);
		dto.setChannelLabelIds(channelLabelIds);
		dto.setChannelLabelNames(channelLabelNames);
		opChannelV2Mapper.updateOpChannelLabel(dto);
	}

	/**
	 * mishengliang 2015-12-02
	 * 将方法从web工程中抽到admin，新建mapper层
	 */
	@Override
	public void queryChannelThemeList(Map<String,Object> jsonMap) {
		List<ChannelTheme> list= channelThemeMapper.queryAllTheme();
		Integer total = channelThemeMapper.getTotal();
		jsonMap.put(OptResult.ROWS, list);
		jsonMap.put(OptResult.TOTAL, total);
	}
	
	/**
	 * 插入新的专属主题
	 * @param themeName 　主题名
		*	2015年12月2日
		*	mishengliang
	 */
	@Override
	public void insertChannelTheme(String themeName){
		channelThemeMapper.insertChannelTheme(themeName);
	}

	/**
	 * 修改专属主题
	 */
	@Override
	public void updateChannelTheme(Integer themeId,String themeName){
		channelThemeMapper.updateChannelTheme(themeId,themeName);
	}
	
	/**
	 * 删除专属主题
	 */
	@Override
	public void deleteChannelTheme(Integer themeId){
		channelThemeMapper.deleteChannelTheme(themeId);
	}
	
	/**
	 * @author lynch 2015-09-14
	 */
	@Override
	public void addAutoRejectId(Integer channelId) {
		channelAutoPassIdCacheDao.addId(channelId);
	}
	
	/**
	 * @author lynch 2015-09-14
	 */
	@Override
	public void deleteAutoRejectId(Integer channelId) {
		channelAutoPassIdCacheDao.deleteId(channelId);
	}

	/**
	 * 刷新频道主题缓存
	 * 
	 * @throws Exception 
		*	2015年12月3日
		*	mishengliang
	 */
	@Override
	public void channelThemeRefreshCache(){
		// 定义频道主题添加到缓存的集合
		List<com.hts.web.common.pojo.OpChannelTheme> ctlist = new ArrayList<com.hts.web.common.pojo.OpChannelTheme>();
		
		// 获取所有频道主题，并转换成缓存需要的集合
		List<ChannelTheme> allChannelTheme = channelThemeMapper.queryAllTheme();
		for (ChannelTheme channelTheme : allChannelTheme) {
			com.hts.web.common.pojo.OpChannelTheme ct = new com.hts.web.common.pojo.OpChannelTheme();
			ct.setId(channelTheme.getId());
			ct.setThemeName(channelTheme.getThemeName());
			ctlist.add(ct);
		}
		
		// 更新频道主题缓存
		channelThemeCache.updateChannelTheme(ctlist);
	}
	
}
