package com.imzhitu.admin.interact.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.pojo.HTWorld;
import com.hts.web.common.pojo.HTWorldChildWorld;
import com.hts.web.common.pojo.HTWorldChildWorldThumb;
import com.hts.web.common.pojo.HTWorldLabel;
import com.hts.web.common.pojo.HTWorldLabelWorld;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.MD5Encrypt;
import com.hts.web.common.util.StringUtil;
import com.hts.web.userinfo.dao.UserInfoDao;
import com.hts.web.ztworld.dao.HTWorldChildWorldDao;
import com.hts.web.ztworld.dao.HTWorldDao;
import com.hts.web.ztworld.dao.HTWorldLabelDao;
import com.hts.web.ztworld.dao.HTWorldLabelWorldDao;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.ZombieChildWorld;
import com.imzhitu.admin.common.pojo.ZombieWorld;
import com.imzhitu.admin.common.service.KeyGenService;
import com.imzhitu.admin.interact.mapper.InteractZombieChildWorldMapper;
import com.imzhitu.admin.interact.mapper.InteractZombieImgMapper;
import com.imzhitu.admin.interact.mapper.InteractZombieWorldMapper;
import com.imzhitu.admin.interact.service.InteractZombieService;

@Service
public class InteractZombieServiceImpl extends BaseServiceImpl implements InteractZombieService{
	
	@Autowired
	private InteractZombieImgMapper zombieImgMapper;
	
	@Autowired
	private InteractZombieWorldMapper zombieWorldMapper;
	
	@Autowired
	private InteractZombieChildWorldMapper zombieChildWorldMapper;
	
	@Autowired
	private KeyGenService keyGenService;
	
	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private HTWorldChildWorldDao worldChildWorldDao;
	@Autowired
	private HTWorldDao worldDao;
	@Autowired
	private HTWorldLabelDao worldLabelDao;
	@Autowired
	private HTWorldLabelWorldDao worldLabelWorldDao;
	@Autowired
	private UserInfoDao userInfoDao;
	
	private String baseThumbPathAixin = "http://imzhitu.qiniudn.com/world/thumbs/1403056393000.png";
	private String baseThumbPathXing = "http://imzhitu.qiniudn.com/world/thumbs/1403057093000.png";
	private String baseThumbPathHuabian = "http://imzhitu.qiniudn.com/world/thumbs/1403056953000.png";
	private String baseThumbPathHua = "http://imzhitu.qiniudn.com/world/thumbs/1403057046000.png";
	
	@Override
	public Integer saveZombieWorld(String childsJSON, Integer titleId,
			Integer authorId, String worldName,
			String worldDesc, String worldLabel, String labelIds,  
			String coverPath, String titlePath, String titleThumbPath, 
			Double longitude, Double latitude,
			String locationAddr, Integer size)throws Exception{
		Date now = new Date();
		ZombieWorld zombieWorld = new ZombieWorld();
		Integer id = keyGenService.generateId(Admin.KEYGEN_ZOMBIE_WORLD);
		zombieWorld.setId(id);
		zombieWorld.setAuthorId(authorId);
		zombieWorld.setComplete(0);
		zombieWorld.setCoverPath(coverPath);
		zombieWorld.setLatitude(latitude);
		zombieWorld.setLocationAddr(locationAddr);
		zombieWorld.setLongitude(longitude);
		zombieWorld.setSize(size);
		zombieWorld.setThumbTitlePath(titleThumbPath);
		zombieWorld.setTitlePath(titlePath);
		zombieWorld.setWorldDesc(worldDesc);
		zombieWorld.setWorldLabel(worldLabel);
		zombieWorld.setWorldLabelIds(labelIds);
		zombieWorld.setWorldName(worldName);
		zombieWorld.setAddDate(now);
		zombieWorld.setModifyDate(now);
		//保存zombie world
		zombieWorldMapper.insertZombieWorld(zombieWorld);
		
		//解析childworld
		Object[] obj = converChildsStringToChildWorld(childsJSON);
		Map<Integer,ZombieChildWorld> childWorldMap = (Map<Integer, ZombieChildWorld>) obj[0];
		Map<Integer, HTWorldChildWorldThumb> thumbsMap = (Map<Integer, HTWorldChildWorldThumb>) obj[1];
		
		setUpTitleZombieWorld(titleId, titleThumbPath, titlePath, childWorldMap, null, null); // 设置封面世界
		//保存childworld
		saveChildWorldInfo(titleId,childWorldMap,thumbsMap,id,0,(float)1.0);
		
		
		return 0;
	}
	
	/**
	 * 检查image是否被下载过，若下载过返回1，否则返回0，并且插入到数据库
	 * @param imagePath
	 * @return
	 * @throws Exception
	 */
	@Override
	public Integer beenDownload(String imagePath)throws Exception{
		long r = zombieImgMapper.queryCount(imagePath);
		if(0 == r){
			zombieImgMapper.insertZombieImg(imagePath);
			return 0;
		}else{
			return 1;
		}
	}
	
	
	/**
	 * 将马甲织图正式发布到织图列表里去
	 * @param zombieWorldId
	 * @throws Exception
	 */
	public void saveZombieWorldToHtWorld(Integer zombieWorldId)throws Exception{
		Date now = new Date();
		ZombieWorld dto = new ZombieWorld();
		dto.setId(zombieWorldId);
		dto.setComplete(0);
		List<ZombieWorld> list = zombieWorldMapper.queryZombieWorld(dto);
		for(ZombieWorld zw:list){
			//查询zombie child world
			List<ZombieChildWorld> childworldList = zombieChildWorldMapper.queryZombieChildWorld(zw.getId());
			
			HTWorld htworld = new HTWorld();
			Integer worldId = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_HTWORLD_ID);
			htworld.setId(worldId);
			htworld.setAuthorId(zw.getAuthorId());
			htworld.setWorldName(zw.getWorldName());
			htworld.setChildCount(childworldList.size());
			htworld.setWorldDesc(zw.getWorldDesc());
			htworld.setWorldLabel(zw.getWorldLabel());
			htworld.setDateAdded(now);
			htworld.setDateModified(now);
			htworld.setLatitude(zw.getLatitude());
			htworld.setLongitude(zw.getLongitude());
			htworld.setShield(0);
			htworld.setVer(1);
			htworld.setClickCount(0);
			htworld.setCommentCount(0);
			htworld.setLikeCount(0);
			htworld.setValid(Tag.TRUE);
			htworld.setSize(zw.getSize());
			String titlePath = converLocalPathToQiNiuPath(zw.getTitlePath());
			htworld.setTitlePath(titlePath);
			String coverPath = converLocalPathToQiNiuPath(zw.getCoverPath());
			htworld.setCoverPath(coverPath);
			String titleThumbPath = converLocalPathToQiNiuPath(zw.getThumbTitlePath());
			htworld.setTitleThumbPath(titleThumbPath);
			htworld.setLatestValid(Tag.FALSE);
			// 生成短链
			String shortLink = MD5Encrypt.shortUrl(worldId);
			htworld.setShortLink(shortLink);
			
			// 保存织图标签
			if(!StringUtil.checkIsNULL(zw.getWorldLabel())) {
				String[] names = zw.getWorldLabel().split(",");
				Set<String> nameSet = new LinkedHashSet<String>();
				Map<String, HTWorldLabel> labelMap = worldLabelDao.queryLabelByNames(names);
				for(String name : names) {
					name = StringUtil.filterXSS(name);
					if(StringUtil.checkIsNULL(name) || name.trim().equals("") || nameSet.contains(name)) {
						continue;
					}
					nameSet.add(name);
					HTWorldLabel label = labelMap.get(name);
					Integer labelId = 0;
					Integer valid = Tag.TRUE;
					if(label == null) {
						labelId = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_ID);
						String pinyin = StringUtil.getPinYin(name);
						label = new HTWorldLabel(labelId, name, pinyin, 0, new Date(), Tag.FALSE, Tag.TRUE, 0, 0);
						worldLabelDao.saveLabel(label);
					} else {
						labelId = label.getId();
						if(label.getLabelState().equals(Tag.WORLD_LABEL_ACTIVITY)) {
							valid = Tag.FALSE;
						}
					}
					Integer lwid = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_WORLD_ID);
					worldLabelWorldDao.saveLabelWorld(new HTWorldLabelWorld(lwid, worldId, zw.getAuthorId(), 
							labelId, valid, lwid, 0));
					int count = 0;
					if(label.getLabelState().equals(Tag.WORLD_LABEL_NORMAL)) { // 普通标签算真实总数，其他标签等审核
						Long labelWorldCount = worldLabelWorldDao.queryWorldCountByLabelId(labelId);
						count = labelWorldCount.intValue();
						worldLabelDao.updateWorldCount(labelId, count);
					}
					
				}
				String[] labelArray = new String[nameSet.size()];
				htworld.setWorldLabel(StringUtil.convertArrayToString(nameSet.toArray(labelArray)));
			}
			
			//保存织图
			worldDao.saveWorld(htworld);
			// 更新织图总数
			Long count = worldDao.queryWorldCountByAuthorId(zw.getAuthorId());
			userInfoDao.updateWorldCount(zw.getAuthorId(), count.intValue());
			
			//保存子世界
			Map<Integer,Integer> childWorldIdMap = new HashMap<Integer,Integer>();
			for(int i=0; i < childworldList.size(); i++){
				ZombieChildWorld zcw = childworldList.get(i);
				HTWorldChildWorld childWorld = new HTWorldChildWorld();
				Integer childWorldId = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_CHILD_WORLD_ID);
				childWorldIdMap.put(zcw.getId(), childWorldId);
				if(zcw.getAtId() == 0){
					childWorld.setAtId(0);
				} else {
					childWorld.setAtId(childWorldIdMap.get(zcw.getAtId()));
				}
				
				childWorld.setId(childWorldId);
				childWorld.setWorldId(worldId);
				childWorld.setAngle(zcw.getAngle());
				childWorld.setChildWorldDesc(zcw.getChildWorldDesc());
				childWorld.setCoordinatex(zcw.getCoordinatex());
				childWorld.setCoordinatey(zcw.getCoordinatey());
				childWorld.setIsTitle(zcw.getIsTitle());
				
				String path = converLocalPathToQiNiuPath(zcw.getPath());
				childWorld.setPath(path);
				
				String thumbPath = converLocalPathToQiNiuPath(zcw.getThumbPath());
				childWorld.setThumbPath(thumbPath);
				
				childWorld.setType(zcw.getType());
				childWorld.setTypePath(zcw.getTypePath());
				childWorld.setHeight(zcw.getHeight());
				childWorld.setWidth(zcw.getHeight());
				
				worldChildWorldDao.saveChildWorld(childWorld);
				dto.setComplete(1);
				dto.setModifyDate(now);
				zombieWorldMapper.updateComplete(dto);
			}
			childWorldIdMap.clear();
			
		}
	}
	
	/**
	 * 查询马甲织图数据,构建数据
	 * @return
	 * @throws Exception
	 */
	@Override
	public Integer queryZombieWorld(Map<String,Object>jsonMap,Integer limit)throws Exception{
		ZombieWorld dto = new ZombieWorld();
		dto.setComplete(0);
		dto.setLimit(limit);
		List<Integer> worldIdList = zombieWorldMapper.queryZombieWorldId(dto);
		long zombieWorldTotalCount = zombieWorldMapper.queryZombieWorldTotalCount();	
		JSONArray jsArray = new JSONArray();
		for(Integer zwId:worldIdList){
			JSONObject jsObj = new JSONObject();
			jsObj.put("id", zwId);
			List<String> imagePathList = zombieChildWorldMapper.queryZombieChildWorldPath(zwId);
			jsObj.put("imagesCount", imagePathList.size());
			jsObj.put("images", imagePathList);
			jsArray.add(jsObj);
		}
		jsonMap.put("data", jsArray);
		jsonMap.put("total", zombieWorldTotalCount);
		jsonMap.put("currentCount", worldIdList.size());
		return 0;
	}
	
	
	/**
	 * 分页查询
	 * @param childs
	 * @return
	 * @throws Exception
	 */
	@Override
	public void queryZombieWorldForTable(int maxId,int page,int rows,Integer complete,Map<String,Object>jsonMap)throws Exception{
		ZombieWorld dto = new ZombieWorld();
		dto.setComplete(complete);
		dto.setMaxId(maxId);
		buildNumberDtos(dto,page,rows,jsonMap,new NumberDtoListAdapter<ZombieWorld>(){
			@Override
			public long queryTotal(ZombieWorld dto){
				return zombieWorldMapper.queryZombieWorldTotalCountForTable(dto);
			}
			
			@Override
			public List< ? extends AbstractNumberDto> queryList(ZombieWorld dto){
				List<ZombieWorld> list = zombieWorldMapper.queryZombieWorldForTable(dto);
				for(ZombieWorld o:list){
					try{
						o.setThumbTitlePath(converLocalPathToQiNiuPath(o.getThumbTitlePath()));
					} catch (Exception e){
						
					}
					
				}
				return list;
			}
		});
	}
	
	private Object[] converChildsStringToChildWorld(String childs)throws Exception{
		JSONObject childsObject = JSONObject.fromObject(childs);
		Object[] worldInfo = new Object[2];
		Map<Integer,ZombieChildWorld> childWorldMap = new HashMap<Integer,ZombieChildWorld>();
		Map<Integer,HTWorldChildWorldThumb> thumbsMap = new HashMap<Integer, HTWorldChildWorldThumb>();
		worldInfo[0] = childWorldMap;
		worldInfo[1] = thumbsMap;
		
		Iterator<String> it = childsObject.keys();
		while(it.hasNext()) {
			String key = it.next();
			Integer childId = Integer.parseInt(key);
			JSONArray childArray = childsObject.getJSONArray(key.toString());
			ZombieChildWorld childWorld = (ZombieChildWorld) JSONUtil.toBean(childArray.get(0), ZombieChildWorld.class);
			childWorld.setIsTitle(0);
			childWorldMap.put(childId, childWorld);
			
			//遍历保存子世界缩略图
			for(int i = 1; i < childArray.size(); i++) {
				HTWorldChildWorldThumb thumb = (HTWorldChildWorldThumb) JSONUtil.toBean(childArray.get(i), HTWorldChildWorldThumb.class);
				childWorld.addThumb(thumb); //向子世界中添加其缩略图信息
				thumbsMap.put(thumb.getToId(), thumb);
			}
		}
		return worldInfo;
	}
	
	private void saveChildWorldInfo(Integer childId,
			Map<Integer, ZombieChildWorld> childWorldMap,
			Map<Integer, HTWorldChildWorldThumb> thumbMap, Integer worldId, Integer phoneCode, Float userVer)
			throws Exception {
		ZombieChildWorld childWorld = childWorldMap.get(childId);
		childWorld.setZombieWorldId(worldId);
		String childworldDesc = StringUtil.filterXSS(childWorld.getChildWorldDesc());
		childWorld.setChildWorldDesc(childworldDesc);

		HTWorldChildWorldThumb thumb = thumbMap.get(childId);
		if (thumb != null) {
			String typePath = thumb.getTypePath();
			Integer type = thumb.getType();
			if(!StringUtil.checkIsNULL(typePath)) {
				type = getTypeByTypePath(typePath);
			} else {
				typePath = getTypePathByType(type);
			}
			
			childWorld.setAtId(thumb.getAtId());
			childWorld.setCoordinatex(thumb.getCoordinatex());
			childWorld.setCoordinatey(thumb.getCoordinatey());
			childWorld.setThumbPath(thumb.getThumbPath());
			childWorld.setAngle(thumb.getAngle());
			childWorld.setType(type);
			childWorld.setTypePath(typePath);
			
			// TODO 过几个版本要删除掉
			// 修复IOS2.9.84发布图片出现窜位的bug，强制试用缩略图中的.thumbnail之前路径替代大图路径
			if(phoneCode.equals(Tag.IOS) && userVer.equals(Tag.VERSION_2_9_84)) {
				if(thumb.getThumbPath().endsWith("thumbnail")) {
					int i = thumb.getThumbPath().lastIndexOf(".");
					String path = thumb.getThumbPath().substring(0, i);
					if(path != null && !path.equals(childWorld.getPath())) {
						childWorld.setPath(path);
					}
				}
			}
		} else {
			childWorld.setAtId(0);
			childWorld.setCoordinatex(0);
			childWorld.setCoordinatey(0);
			childWorld.setAngle(-1);
			childWorld.setType(1);
			childWorld.setTypePath(null);
		}

		Integer atId = keyGenService.generateId(Admin.KEYGEN_ZOMBIE_CHILD_WORLD);
		childWorld.setId(atId);
		zombieChildWorldMapper.insertZombieChildWorld(childWorld);

		Map<Integer, HTWorldChildWorldThumb> thumbs = childWorld.getThumbs();
		Set<Integer> keies = thumbs.keySet();
		Iterator<Integer> it = keies.iterator();
		while (it.hasNext()) {
			Integer key = it.next();
			HTWorldChildWorldThumb childThumb = thumbs.get(key);
			childThumb.setAtId(atId);
			/*
			 * 递归保存包含缩略图对应的子世界
			 */
			saveChildWorldInfo(childThumb.getToId(), childWorldMap, thumbMap,
					worldId, phoneCode, userVer);
		}
	}
	
	/**
	 * 批量定时发布马甲织图
	 * @param zombieWorldIds
	 * @param begin
	 * @param timeSpan
	 */
	@Override
	public void batchSaveZombieWorldToHTWorld(final String zombieWorldIds,final Date begin, final Integer timeSpan){
		if(null == zombieWorldIds || "".equals(zombieWorldIds.trim()))
			return;
		
		Runnable rn = new Runnable(){
			public void run(){
				Integer [] zombieWorldIdArray = StringUtil.convertStringToIds(zombieWorldIds);
				long timeMsSpan = timeSpan*60L*1000;
				
				//从啥时候开始发图
				Date now = new Date();
				long beginMsSpan = begin.getTime() - now.getTime();
				if(beginMsSpan > 0 && beginMsSpan < 24L*60*60*1000 && timeMsSpan >0 && timeMsSpan < 3600L*1000){//目标发图时间必须在一天之内。因为防止误操作导致建立一条很长时间睡眠的线程。
					try{
						Thread.sleep(beginMsSpan);
					}catch(Exception e){
						return;
					}
				}else{//不符合规则的时间间隔将不处理
					return;
				}
				for(Integer id: zombieWorldIdArray){
					try{
						ZombieWorld dto = new ZombieWorld();
						dto.setId(id);
						//检查数据是否合法
						List<ZombieWorld> zombieWorldList = zombieWorldMapper.queryZombieWorld(dto);
						if(zombieWorldList != null && zombieWorldList.size() == 1){
							if(zombieWorldList.get(0).getComplete() == Tag.FALSE){
								//正式发布
								saveZombieWorldToHtWorld(id);
								//睡眠多少毫秒
								Thread.sleep(timeMsSpan);
							}
						}
					}catch(Exception e){
						//不处理异常
					}
				}
			}
		};
		Thread t = new Thread(rn);
		t.start();
	}
	
	private Integer getTypeByTypePath(String typePath) {
		Integer type = Tag.BASE_THUMB_CIRCLE;
		if(!StringUtil.checkIsNULL(typePath)) {
			if(typePath.equals(baseThumbPathAixin)) {
				type = Tag.BASE_THUMB_TYPE_AIXIN;
				
			} else if(typePath.equals(baseThumbPathXing)) {
				type = Tag.BASE_THUMB_TYPE_XING;
				
			} else if(typePath.equals(baseThumbPathHuabian)) {
				type = Tag.BASE_THUMB_TYPE_HUABIAN;
				
			} else if(typePath.equals(baseThumbPathHua)) {
				type = Tag.BASE_THUMB_TYPE_HUA;
			}
		}
		return type;
	}
	
	private String getTypePathByType(Integer type) {
		String typePath = null;
		switch(type) {
		case Tag.BASE_THUMB_TYPE_AIXIN:
			typePath = baseThumbPathAixin;
			break;
		case Tag.BASE_THUMB_TYPE_XING:
			typePath = baseThumbPathXing;
			break;
		case Tag.BASE_THUMB_TYPE_HUABIAN:
			typePath = baseThumbPathHuabian;
			break;
		case Tag.BASE_THUMB_TYPE_HUA:
			typePath = baseThumbPathHua;
			break;
		default:
			break;
		}
		return typePath;
	}
	
	
	private String converLocalPathToQiNiuPath(String localPath)throws Exception{
		if(localPath == null || localPath.equals(""))return null;
		int nameBeginPos = localPath.lastIndexOf('\\');
		if (nameBeginPos < 0)
			nameBeginPos = 0;
		int nameEndPos = localPath.indexOf(".jpg");
		String str = localPath.substring(nameBeginPos, nameEndPos);
		try{
			Long time = Long.parseLong(str);
			Date date = new Date(time*1000);
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
			String dateStr = df.format(date);
			return "http://imzhitu.qiniudn.com/ios/image/" + dateStr + "/"+localPath.substring(nameBeginPos);
		}catch(Exception e){
			throw new Exception("名字出错");
		}
		
	}
	
	
	private void setUpTitleZombieWorld(Integer titleId, String titleThumbPath, String titlePath,
			Map<Integer,ZombieChildWorld> childWorldMap, Integer phoneCode, Float userVer){
		ZombieChildWorld titleChild = childWorldMap.get(titleId);
		titleChild.setThumbPath(titleThumbPath);
		titleChild.setIsTitle(1); // 设置封面标志
		titleChild.setAtId(0); // 表示其并不在任何子世界之中
		titleChild.setCoordinatex(0);
		titleChild.setCoordinatey(0);
	}
}
