package com.imzhitu.admin.op;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.AdminUserDetails;
import com.imzhitu.admin.common.pojo.OpChannel;
import com.imzhitu.admin.common.pojo.OpChannelCover;
import com.imzhitu.admin.common.pojo.OpChannelTopOne;
import com.imzhitu.admin.common.pojo.OpChannelTopOnePeriod;
import com.imzhitu.admin.common.pojo.OpChannelTopType;
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.op.service.ChannelService;
import com.imzhitu.admin.op.service.OpChannelUserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ChannelAction extends BaseCRUDAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9184021956909143186L;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private OpChannelUserService channelUserService;
	
	private OpChannel channel = new OpChannel();
	private OpChannelWorld world = new OpChannelWorld();
	private OpChannelTopOne topOne = new OpChannelTopOne();
	private OpChannelTopOnePeriod period = new OpChannelTopOnePeriod();
	private OpChannelCover cover = new OpChannelCover();
	
	private String title;
	private String ids;
	private Integer id;
	private Integer channelId;
	private Integer valid;
	private Date beginDate;
	private Date endDate;
	private Boolean addAllTag;
	private Integer childCountBase;
	private Boolean isAdd = true;
	private Integer worldId;
	private String query;
	private Integer superb;
	
	/**
	 * 频道织图查询类型标记位
	 * 全部查询：空
	 * 频道织图生效并过滤织图被用户删除：1
	 * 频道织图未生效并过滤织图被用户删除：2
	 * 频道织图被小编删除：3
	 * 织图被用户删除：4
	 * @author zhangbo	2015年11月2日
	 */
	private Integer flag;
	
	/**
	 * 更新频道top one title缓存
	 * 
	 * @return
	 */
	public String updateTopOneTitleCache() {
		try {
			channelService.updateTopOneTitleCache(beginDate, endDate);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新频道top one缓存
	 * 
	 * @return
	 */
	public String updateTopOneCache() {
		try {
			channelService.updateTopOneCache();
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询频道列表
	 * 
	 * @return
	 */
	public String queryChannel() {
		try {
			channelService.buildChannel(channel, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 搜索频道
	 * 
	 * @return
	 */
	public String searchChannel() {
		try {
			channelService.searchChannel(query, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String queryAllChannel(){
		PrintWriter out = null;
		try{
			out = response.getWriter();
			List<OpChannel> list = channelService.queryAllChannel();
			JSONArray jsArray = JSONArray.fromObject(list);
			out.print(jsArray.toString());
			out.flush();
		}catch(Exception e){
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(json.toString());
			out.flush();
		}finally{
			out.close();
		}
		return null;
	}
	
	/**
	 * 根据id查询频道
	 * 
	 * @return
	 */
	public String queryChannelById() {
		try {
			OpChannel channel = channelService.queryChannelById(id);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, channel, OptResult.JSON_KEY_OBJ, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存频道
	 * 
	 * @return
	 */
	public String saveChannel() {
		try {
			channelService.saveChannel(channel);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新频道
	 * 
	 * @return
	 */
	public String updateChannel() {
		try {
			channelService.updateChannel(channel);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除频道
	 * 
	 * @return
	 */
	public String deleteChannels() {
		try {
			channelService.deleteChannel(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新频道排序
	 * 
	 * @return
	 */
	public String updateChannelSerial() {
		String[] ids = request.getParameterValues("reIndexId");
		try {
			channelService.addChannelSerial(ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新有效性
	 * 
	 * @return
	 */
	public String updateChannelValid() {
		try {
			channelService.updateChannelValid(ids, valid);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询频道织图列表
	 * 
	 * @return
	 */
	public String queryChannelWorld() {
		try {
			channelService.buildChannelWorld(world, flag, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新频道织图
	 * 
	 * @return
	 */
	public String updateChannelWorld() {
		try {
			channelService.updateChannelWorld(world);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存频道织图
	 * 
	 * @return
	 */
	public String saveChannelWorld() {
		try {
			channelService.saveChannelWorld(world);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除频道织图
	 * 
	 * @return
	 */
	public String deleteChannelWorld() {
		try {
			channelService.deleteChannelWorlds(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据频道id与织图id删除频道织图的关联关系
	 * 
	 * @return
	 * @author zhangbo	2015年10月14日
	 */
	public String deleteChannelWorldByChannelIdAndWorldId() {
		try {
			channelService.deleteChannelWorldByChannelIdAndWorldId(channelId, worldId);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新频道织图有效性,同时做发通知操作
	 * 
	 * @return
	 */
	public String updateChannelWorldValid() {
		try {
			channelService.updateChannelWorldValid(ids, valid);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据织图id更新有效性
	 * 
	 * @return
	 */
	public String updateWorldValidByCIDAndWID() {
		try {
			channelService.updateChannelWorldValid(channelId, worldId, valid);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 
	 * 更新精选标记和通知用户入选精选
	 * @author mishengliang
	 * @return
	 */
	public String updateWorldSuperbByWID() {
		try {
			channelService.updateChannelWorldSuperb(channelId, worldId, superb);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新频道织图序号
	 * 
	 * @return
	 */
	public String updateChannelWorldSerial() {
		String[] ids = request.getParameterValues("reIndexId");
		try {
			channelService.addChannelWorldId(channelId, ids);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 添加频道织图推荐消息
	 * 
	 * @return
	 */
	public String addChannelWorldRecommendMsgs() {
		try {
			channelService.addChannelWorldNoticeMsgs(ids);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询top one列表
	 * 
	 * @return
	 */
	public String queryTopOne() {
		try {
			channelService.buildTopOneDto(topOne, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存top one
	 * 
	 * @return
	 */
	public String saveTopOne() {
		try {
			channelService.saveTopOne(topOne);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存top one
	 * 
	 * @return
	 */
	public String updateTopOne() {
		try {
			channelService.updateTopOne(topOne);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新top one排序
	 * 
	 * @return
	 */
	public String updateTopOneSerial() {
		String[] ids = request.getParameterValues("reIndexId");
		try {
			channelService.addTopOneId(ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新top one有效性
	 * 
	 * @return
	 */
	public String updateTopOneValid() {
		try {
			channelService.updateTopOneValid(ids, valid);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除top one
	 * @return
	 */
	public String deleteTopOne() {
		try {
			channelService.deleteTopOne(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询TonOne期数
	 * @return
	 */
	public String queryTopOnePeriod() {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			if(start == 0) 
				period.setFirstRow(0);
			List<OpChannelTopOnePeriod> list = channelService.queryTopOnePeriodList(period, page, rows, addAllTag);
			JSONArray jsArray = JSONArray.fromObject(list);
			writer.println(jsArray.toString());
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject jsObj = JSONObject.fromObject(jsonMap);
			writer.println(jsObj.toString());
			e.printStackTrace();
		} finally {
			writer.close();
		}
		return null;
	}
	
	/**
	 * 添加top one推荐消息
	 * 
	 * @return
	 */
	public String addTopOneRecommendMsgs() {
		try {
			channelService.addTopOneRecommendMsgs(ids);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id查询Top One
	 * @return
	 */
	public String queryTopOneById() {
		try {
			OpChannelTopOne topOne = channelService.queryTopOneById(id);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, topOne, OptResult.JSON_KEY_OBJ, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询所有Top One类型
	 * 
	 * @return
	 */
	public String queryTopType() {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			List<OpChannelTopType> list = channelService.queryTopType(addAllTag);
			JSONArray jsArray = JSONArray.fromObject(list);
			writer.println(jsArray.toString());
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject jsObj = JSONObject.fromObject(jsonMap);
			writer.println(jsObj.toString());
		} finally {
			writer.close();
		}
		return null;
	}
	
	/**
	 * 仅使用一次，自动更新top one
	 * @return
	 * 
	 */
	public String updateTopOneByAdmin(){
		try{
			AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(user.getId() != 59) return null;
			channelService.updateTopOne();
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return null;
	}
	
	/**
	 * 保存织图到多个频道
	 * 
	 * @return
	 * @author zhangbo	2015年10月14日
	 */
	public String saveWorldIntoChannels() {
		try {
			Integer[] channelIds = StringUtil.convertStringToIds(ids);
			for (Integer channelId : channelIds) {
				
				// 先将织图作者加入频道的订阅人中
				AdminUserDetails user = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				channelUserService.addChannelUserByWorldId(worldId, channelId, Tag.TRUE, user.getId());
				
				// 再将织图加入频道中，但不生效，由小编审核后生效
				OpChannelWorld channelWorld = new OpChannelWorld();
				channelWorld.setChannelId(channelId);
				channelWorld.setWorldId(worldId);
				channelWorld.setValid(Tag.FALSE);
				channelWorld.setNotified(Tag.FALSE);
				
				channelService.saveChannelWorld(channelWorld);
			}
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public ChannelService getChannelService() {
		return channelService;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	public OpChannel getChannel() {
		return channel;
	}

	public void setChannel(OpChannel channel) {
		this.channel = channel;
	}

	public OpChannelWorld getWorld() {
		return world;
	}

	public void setWorld(OpChannelWorld world) {
		this.world = world;
	}

	public OpChannelTopOne getTopOne() {
		return topOne;
	}

	public void setTopOne(OpChannelTopOne topOne) {
		this.topOne = topOne;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getAddAllTag() {
		return addAllTag;
	}

	public void setAddAllTag(Boolean addAllTag) {
		this.addAllTag = addAllTag;
	}

	public Integer getChildCountBase() {
		return childCountBase;
	}

	public void setChildCountBase(Integer childCountBase) {
		this.childCountBase = childCountBase;
	}

	public Boolean getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(Boolean isAdd) {
		this.isAdd = isAdd;
	}

	public OpChannelTopOnePeriod getPeriod() {
		return period;
	}

	public void setPeriod(OpChannelTopOnePeriod period) {
		this.period = period;
	}

	public OpChannelCover getCover() {
		return cover;
	}

	public void setCover(OpChannelCover cover) {
		this.cover = cover;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Integer getSuperb() {
		return superb;
	}

	public void setSuperb(Integer superb) {
		this.superb = superb;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

}
