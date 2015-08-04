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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.common.pojo.ZombieChildWorld;
import com.imzhitu.admin.common.pojo.ZombieWorld;
import com.imzhitu.admin.common.service.KeyGenService;
import com.imzhitu.admin.interact.mapper.InteractZombieChildWorldMapper;
import com.imzhitu.admin.interact.mapper.InteractZombieImgMapper;
import com.imzhitu.admin.interact.mapper.InteractZombieWorldMapper;
import com.imzhitu.admin.interact.service.InteractZombieService;
import com.imzhitu.admin.op.mapper.ChannelMapper;
import com.imzhitu.admin.op.mapper.ChannelWorldMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class InteractZombieServiceImpl extends BaseServiceImpl implements InteractZombieService {

    @Autowired
    private InteractZombieImgMapper zombieImgMapper;

    @Autowired
    private InteractZombieWorldMapper zombieWorldMapper;

    @Autowired
    private InteractZombieChildWorldMapper zombieChildWorldMapper;

    @Autowired
    private ChannelWorldMapper channelWorlMapper;

    @Autowired
    private ChannelMapper channelMapper;

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

    @Autowired
    private com.hts.web.operations.service.ChannelService webChannelService;

    private String baseThumbPathAixin = "http://static.imzhitu.com/world/thumbs/1403056393000.png";
    private String baseThumbPathXing = "http://static.imzhitu.com/world/thumbs/1403057093000.png";
    private String baseThumbPathHuabian = "http://static.imzhitu.com/world/thumbs/1403056953000.png";
    private String baseThumbPathHua = "http://static.imzhitu.com/world/thumbs/1403057046000.png";

    @SuppressWarnings("unchecked")
    @Override
    public Integer saveZombieWorld(String childsJSON, Integer titleId, Integer authorId, String worldName,
	    String worldDesc, String worldLabel, String labelIds, String coverPath, String titlePath,
	    String titleThumbPath, Double longitude, Double latitude, String locationAddr, Integer size,
	    Integer channelId) throws Exception {
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
	zombieWorld.setWorldDesc(worldDesc == null? null : handleWorldDesc(worldDesc));
	zombieWorld.setWorldLabel(worldLabel);
	zombieWorld.setWorldLabelIds(labelIds);
	zombieWorld.setWorldName(worldName);
	zombieWorld.setAddDate(now);
	zombieWorld.setModifyDate(now);
	if (channelId == null) {
	    zombieWorld.setChannelId(0);
	} else {
	    zombieWorld.setChannelId(channelId);
	}

	// 保存zombie world
	zombieWorldMapper.insertZombieWorld(zombieWorld);

	// 解析childworld
	Object[] obj = converChildsStringToChildWorld(childsJSON);
	Map<Integer, ZombieChildWorld> childWorldMap = (Map<Integer, ZombieChildWorld>) obj[0];
	Map<Integer, HTWorldChildWorldThumb> thumbsMap = (Map<Integer, HTWorldChildWorldThumb>) obj[1];

	setUpTitleZombieWorld(titleId, titleThumbPath, titlePath, childWorldMap, null, null); // 设置封面世界
	// 保存childworld
	saveChildWorldInfo(titleId, childWorldMap, thumbsMap, id, 0, (float) 1.0);

	return 0;
    }

    /**
     * 检查image是否被下载过，若下载过返回1，否则返回0，并且插入到数据库
     * 
     * @param imagePath
     * @return
     * @throws Exception
     */
    @Override
    public Integer beenDownload(String imagePath) throws Exception {
	long r = zombieImgMapper.queryCount(imagePath);
	if (0 == r) {
	    zombieImgMapper.insertZombieImg(imagePath);
	    return 0;
	} else {
	    return 1;
	}
    }

    /**
     * 将马甲织图正式发布到织图列表里去
     * 
     * @param zombieWorldId
     * @throws Exception
     */
    public Integer saveZombieWorldToHtWorld(Integer zombieWorldId) throws Exception {
	Date now = new Date();
	ZombieWorld dto = new ZombieWorld();
	dto.setId(zombieWorldId);
	dto.setComplete(0);
	List<ZombieWorld> list = zombieWorldMapper.queryZombieWorld(dto);
	if (list == null || list.size() != 1) {
	    return null;
	}

	ZombieWorld zw = list.get(0);

	// 查询zombie child world
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
	htworld.setLatestValid(Tag.TRUE);
	// 生成短链
	String shortLink = MD5Encrypt.shortUrl(worldId);
	htworld.setShortLink(shortLink);
	htworld.setTp(0);

	// 保存织图标签
	if (!StringUtil.checkIsNULL(zw.getWorldLabel())) {
	    String[] names = zw.getWorldLabel().split(",");
	    Set<String> nameSet = new LinkedHashSet<String>();
	    Map<String, HTWorldLabel> labelMap = worldLabelDao.queryLabelByNames(names);
	    for (String name : names) {
		name = StringUtil.filterXSS(name);
		if (StringUtil.checkIsNULL(name) || name.trim().equals("") || nameSet.contains(name)) {
		    continue;
		}
		nameSet.add(name);
		HTWorldLabel label = labelMap.get(name);
		Integer labelId = 0;
		Integer valid = Tag.TRUE;
		if (label == null) {
		    labelId = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_ID);
		    String pinyin = StringUtil.getPinYin(name);
		    label = new HTWorldLabel(labelId, name, pinyin, 0, new Date(), Tag.FALSE, Tag.TRUE, 0, 0);
		    worldLabelDao.saveLabel(label);
		} else {
		    labelId = label.getId();
		    if (label.getLabelState().equals(Tag.WORLD_LABEL_ACTIVITY)) {
			valid = Tag.FALSE;
		    }
		}
		Integer lwid = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_LABEL_WORLD_ID);
		worldLabelWorldDao.saveLabelWorld(
			new HTWorldLabelWorld(lwid, worldId, zw.getAuthorId(), labelId, valid, lwid, 0));
		int count = 0;
		if (label.getLabelState().equals(Tag.WORLD_LABEL_NORMAL)) { // 普通标签算真实总数，其他标签等审核
		    Long labelWorldCount = worldLabelWorldDao.queryWorldCountByLabelId(labelId);
		    count = labelWorldCount.intValue();
		    worldLabelDao.updateWorldCount(labelId, count);
		}

	    }
	    String[] labelArray = new String[nameSet.size()];
	    htworld.setWorldLabel(StringUtil.convertArrayToString(nameSet.toArray(labelArray)));
	}

	// 保存织图
	worldDao.saveWorld(htworld);

	// 更新织图总数
	Long count = worldDao.queryWorldCountByAuthorId(zw.getAuthorId());
	Integer childCount = worldDao.queryChildCount(zw.getAuthorId());
	userInfoDao.updateWorldAndChildCount(zw.getAuthorId(), count.intValue(), childCount);

	// 保存子世界
	Map<Integer, Integer> childWorldIdMap = new HashMap<Integer, Integer>();
	for (int i = 0; i < childworldList.size(); i++) {
	    ZombieChildWorld zcw = childworldList.get(i);
	    HTWorldChildWorld childWorld = new HTWorldChildWorld();
	    Integer childWorldId = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_CHILD_WORLD_ID);
	    childWorldIdMap.put(zcw.getId(), childWorldId);
	    if (zcw.getAtId() == 0) {
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
	}
	dto.setComplete(1);
	dto.setModifyDate(now);
	dto.setHtworldId(worldId);
	dto.setShortLink(shortLink);
	zombieWorldMapper.updateComplete(dto);
	childWorldIdMap.clear();
	return worldId;
    }

    /**
     * 查询马甲织图数据,构建数据
     * 
     * @return
     * @throws Exception
     */
    @Override
    public Integer queryZombieWorld(Map<String, Object> jsonMap, Integer limit) throws Exception {
	ZombieWorld dto = new ZombieWorld();
	dto.setComplete(0);
	dto.setLimit(limit);
	List<Integer> worldIdList = zombieWorldMapper.queryZombieWorldId(dto);
	long zombieWorldTotalCount = zombieWorldMapper.queryZombieWorldTotalCount();
	JSONArray jsArray = new JSONArray();
	for (Integer zwId : worldIdList) {
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
     * 
     * @param childs
     * @return
     * @throws Exception
     */
    @Override
    public void queryZombieWorldForTable(Integer channelId, int maxId, Date addDate, Date modifyDate, int page,
	    int rows, Integer complete, Integer schedulaFlag, Map<String, Object> jsonMap) throws Exception {
	ZombieWorld dto = new ZombieWorld();
	dto.setComplete(complete);
	dto.setMaxId(maxId);
	dto.setModifyDate(modifyDate);
	dto.setAddDate(addDate);
	dto.setChannelId(channelId);
	dto.setSchedulaFlag(schedulaFlag);
	buildNumberDtos(dto, page, rows, jsonMap, new NumberDtoListAdapter<ZombieWorld>() {
	    @Override
	    public long queryTotal(ZombieWorld dto) {
		return zombieWorldMapper.queryZombieWorldTotalCountForTable(dto);
	    }

	    @Override
	    public List<? extends AbstractNumberDto> queryList(ZombieWorld dto) {
		List<ZombieWorld> list = zombieWorldMapper.queryZombieWorldForTable(dto);
		for (ZombieWorld o : list) {
		    try {
			o.setThumbTitlePath(converLocalPathToQiNiuPath(o.getThumbTitlePath()));
		    } catch (Exception e) {

		    }

		}
		return list;
	    }
	});
    }

    @SuppressWarnings("unchecked")
    private Object[] converChildsStringToChildWorld(String childs) throws Exception {
	JSONObject childsObject = JSONObject.fromObject(childs);
	Object[] worldInfo = new Object[2];
	Map<Integer, ZombieChildWorld> childWorldMap = new HashMap<Integer, ZombieChildWorld>();
	Map<Integer, HTWorldChildWorldThumb> thumbsMap = new HashMap<Integer, HTWorldChildWorldThumb>();
	worldInfo[0] = childWorldMap;
	worldInfo[1] = thumbsMap;

	Iterator<String> it = childsObject.keys();
	while (it.hasNext()) {
	    String key = it.next();
	    Integer childId = Integer.parseInt(key);
	    JSONArray childArray = childsObject.getJSONArray(key.toString());
	    ZombieChildWorld childWorld = (ZombieChildWorld) JSONUtil.toBean(childArray.get(0), ZombieChildWorld.class);
	    childWorld.setIsTitle(0);
	    childWorldMap.put(childId, childWorld);

	    // 遍历保存子世界缩略图
	    for (int i = 1; i < childArray.size(); i++) {
		HTWorldChildWorldThumb thumb = (HTWorldChildWorldThumb) JSONUtil.toBean(childArray.get(i),
			HTWorldChildWorldThumb.class);
		childWorld.addThumb(thumb); // 向子世界中添加其缩略图信息
		thumbsMap.put(thumb.getToId(), thumb);
	    }
	}
	return worldInfo;
    }

    private void saveChildWorldInfo(Integer childId, Map<Integer, ZombieChildWorld> childWorldMap,
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
	    if (!StringUtil.checkIsNULL(typePath)) {
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
	    if (phoneCode.equals(Tag.IOS) && userVer.equals(Tag.VERSION_2_9_84)) {
		if (thumb.getThumbPath().endsWith("thumbnail")) {
		    int i = thumb.getThumbPath().lastIndexOf(".");
		    String path = thumb.getThumbPath().substring(0, i);
		    if (path != null && !path.equals(childWorld.getPath())) {
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
	    saveChildWorldInfo(childThumb.getToId(), childWorldMap, thumbMap, worldId, phoneCode, userVer);
	}
    }

    private Integer getTypeByTypePath(String typePath) {
	Integer type = Tag.BASE_THUMB_CIRCLE;
	if (!StringUtil.checkIsNULL(typePath)) {
	    if (typePath.equals(baseThumbPathAixin)) {
		type = Tag.BASE_THUMB_TYPE_AIXIN;

	    } else if (typePath.equals(baseThumbPathXing)) {
		type = Tag.BASE_THUMB_TYPE_XING;

	    } else if (typePath.equals(baseThumbPathHuabian)) {
		type = Tag.BASE_THUMB_TYPE_HUABIAN;

	    } else if (typePath.equals(baseThumbPathHua)) {
		type = Tag.BASE_THUMB_TYPE_HUA;
	    }
	}
	return type;
    }

    private String getTypePathByType(Integer type) {
	String typePath = null;
	switch (type) {
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

    private String converLocalPathToQiNiuPath(String localPath) throws Exception {
	if (localPath == null || localPath.equals(""))
	    return null;
	int nameBeginPos = localPath.lastIndexOf('\\');
	if (nameBeginPos < 0)
	    nameBeginPos = 0;
	int nameEndPos = localPath.indexOf(".jpg");
	String str = localPath.substring(nameBeginPos, nameEndPos);
	try {
	    Long time = Long.parseLong(str);
	    Date date = new Date(time * 1000);
	    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
	    String dateStr = df.format(date);
	    return "http://static.imzhitu.com/ios/image/" + dateStr + "/" + localPath.substring(nameBeginPos);
	} catch (Exception e) {
	    throw new Exception("名字出错");
	}

    }

    private void setUpTitleZombieWorld(Integer titleId, String titleThumbPath, String titlePath,
	    Map<Integer, ZombieChildWorld> childWorldMap, Integer phoneCode, Float userVer) {
	ZombieChildWorld titleChild = childWorldMap.get(titleId);
	titleChild.setThumbPath(titleThumbPath);
	titleChild.setIsTitle(1); // 设置封面标志
	titleChild.setAtId(0); // 表示其并不在任何子世界之中
	titleChild.setCoordinatex(0);
	titleChild.setCoordinatey(0);
    }

    /**
     * 批量更新标签
     * 
     * @throws Exception
     */
    @Override
    public void batchUpdateZombieWorldLabel(String zombieWorldIds, String labelString) throws Exception {
	String[] labels = labelString.trim().split(",");
	Integer[] zombieWorldIdArray = StringUtil.convertStringToIds(zombieWorldIds);
	int size = labels.length;
	List<String> list = new ArrayList<String>();
	ZombieWorld dto = new ZombieWorld();
	dto.setModifyDate(new Date());
	long l = 0;
	for (int j = 0; j < zombieWorldIdArray.length; j++) {
	    /**
	     * 算法描述： 先随机一个数。然后查看该数的字节码，决定该字节码对应的标签是否需要
	     */
	    l = Math.round(Math.random() * 1024);
	    for (int i = 0; i < size; i++) {
		if (((l >> i) & 1) == 1) {
		    list.add(labels[i]);
		}
	    }

	    if (list.size() > 0) {
		String label = list.toString();
		dto.setWorldLabel(label.substring(1, label.length() - 1));
		dto.setId(zombieWorldIdArray[j]);
		zombieWorldMapper.updateZombieWorldLabel(dto);
		list.clear();
	    }
	}

    }

    /**
     * 将马甲织图发布到织图和频道下
     * 
     * @param zombieWorldId
     * @throws Exception
     */
    @Override
    public void saveZombieWorldToChannelAndWorld(Integer zombieWorldId) throws Exception {
	ZombieWorld dto = new ZombieWorld();
	dto.setId(zombieWorldId);
	Date now = new Date();
	// 检查数据是否合法
	List<ZombieWorld> zombieWorldList = zombieWorldMapper.queryZombieWorld(dto);
	if (zombieWorldList != null && zombieWorldList.size() == 1) {
	    ZombieWorld zw = zombieWorldList.get(0);
	    // 正式发布织图
	    Integer worldId = saveZombieWorldToHtWorld(zombieWorldId);

	    // 添加到频道
	    if (zw.getChannelId() != null && zw.getChannelId() != 0) {
		if (channelMapper.queryChannelById(zw.getChannelId()) != null) {
		    Integer channelWorldId = webKeyGenService.generateId(KeyGenServiceImpl.OP_CHANNEL_WORLD_ID);
		    OpChannelWorld channelWorld = new OpChannelWorld();
		    channelWorld.setAuthorId(zw.getAuthorId());
		    channelWorld.setChannelId(zw.getChannelId());
		    channelWorld.setDateAdded(now);
		    channelWorld.setId(channelWorldId);
		    channelWorld.setNotified(Tag.TRUE);
		    channelWorld.setValid(Tag.TRUE);
		    channelWorld.setWorldId(worldId);
		    channelWorld.setSuperb(Tag.FALSE);
		    channelWorld.setWeight(0);
		    channelWorld.setSerial(channelWorldId);
		    channelWorlMapper.save(channelWorld);
		    // 更新频道图片总数
		    webChannelService.updateWorldAndChildCount(zw.getChannelId());
		}
	    }
	}
    }

    /**
     * 批量删除
     */
    @Override
    public void batchDeleteZombieWorld(String idsStr) throws Exception {
	Integer[] ids = StringUtil.convertStringToIds(idsStr);
	zombieWorldMapper.batchDeleteZombieWorld(ids);
    }

    @Override
    public void updateZombieWorld(Integer id, String worldDesc) {
	ZombieWorld dto = new ZombieWorld();
	dto.setId(id);
	dto.setWorldDesc(worldDesc);
	zombieWorldMapper.updateZombieWorld(dto);
    }

    /**
     * 处理织图描述，过滤掉不需要的字符
     * 如：html标签，与html标签中间所携带的内容，样式关键字“class=”与之后的内容
     * 原因：因为马甲织图都来源于网络，粘贴过程中，会携带网页上隐藏的内容，转化为文本后，会显示出来，故要做处理
     * 
     * @param worldDesc	织图描述
     * @return
     * @author zhangbo	2015年7月29日
     */
    private String handleWorldDesc(String worldDesc) {
	// 声明返回值
	String result = "";
	// 匹配html标签中间夹杂内容的，要进行多次过滤
	String regex1 = "<[A-Za-z]*>[A-Za-z0-9_\\u4E00-\\u9FA5\\!@#$%^&*(),.?！@#￥%……&*（），。？]*</[A-Za-z]*>";
	
	// 匹配各种单独的html起始标签，如<div>
	String regex2 = "<[A-Za-z]*>";
	
	// 匹配各种单独的html起始标签，并且有属性配置的，如<SPAN class="emoji emoji2764" title="heavy black heart">
	String regex3 = "<[A-Za-z0-9\\s\\`~!@#$%^&*()_+-=\"\']*>";
	
	// 匹配各种单独的html起始标签，如<div>
	String regex4 = "</[A-Za-z]*>";
	
	// 匹配网页粘贴过来后，剩余的样式class设置，故其中不包括中文与中文字符的过滤
	String regex5 = "class=[A-Za-z0-9\\s\\`~!@#$%^&*()_+-=]*>";
	
	// 进行连续处理
	String str1 = handleStrByRegex(regex1, worldDesc);
	String str2 = handleStrByRegex(regex2, str1);
	String str3 = handleStrByRegex(regex3, str2);
	String str4 = handleStrByRegex(regex4, str3);
	result = handleStrByRegex(regex5, str4);
	return result;
    }
    
    /**
     * 通过正则表达式去过滤
     * 
     * @param regex	正则表达式
     * @param str	被处理的字符串
     * @return		处理后的字符串
     * @author zhangbo	2015年7月29日
     */
    private String handleStrByRegex(String regex, String str) {
	Pattern pat = Pattern.compile(regex);
	Matcher matcher = pat.matcher(str);
	/*
	 * sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序 存储起来。
	 */
	StringBuffer sb = new StringBuffer();
	while (matcher.find()) {
	    // 因为要删除掉符合正则表达式的字符串，故用空字符串替换
	    matcher.appendReplacement(sb, "");
	}
	// 最后还得要把尾串接到已替换的内容后面去  
        matcher.appendTail(sb);
        return sb.toString();
    }

}
