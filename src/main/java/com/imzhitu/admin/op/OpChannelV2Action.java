package com.imzhitu.admin.op;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.OpChannelLink;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.OpChannelV2Dto;
import com.imzhitu.admin.constant.LoggerKeies;
import com.imzhitu.admin.op.service.OpChannelV2Service;
import com.imzhitu.admin.privileges.dao.RoleDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class OpChannelV2Action extends BaseCRUDAction {

	private static final long serialVersionUID = -8394229282973325347L;

	private static Logger log = Logger.getLogger(LoggerKeies.CHANNEL_V2);

	@Autowired
	private OpChannelV2Service opChannelV2Service;

	@Autowired
	private RoleDao roleDao;

	/**
	 * 插入频道
	 * 
	 * @return
	 */
	public String insertOpChannel() {
		try {

			opChannelV2Service.insertOpChannel(channelDesc, channelIcon, channelSubIcon, channelBanner, channelReview, channelName, channelTypeId, ownerId, themeId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 删除频道
	 * 
	 * @return
	 */
	public String deleteOpChannel() {
		try {
			opChannelV2Service.deleteOpChannel(channelId, ownerId);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 更新频道
	 * 
	 * @return
	 */
	public String updateOpChannel() {
		try {
			opChannelV2Service.updateOpChannel(channelId, channelDesc, channelIcon, channelSubIcon, channelBanner, channelReview, channelName, channelTypeId, ownerId, themeId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 更新频道精选
	 * 
	 * @return
	 * @author zhangbo 2015年8月19日
	 */
	public String updateOpChannelSuperb() {
		try {
			opChannelV2Service.updateOpChannelSuperb(channelId, superb);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 更新频道有效性
	 * 
	 * @return
	 * @author zhangbo 2015年8月19日
	 */
	public String updateOpChannelValid() {
		try {
			opChannelV2Service.updateOpChannelValid(channelId, valid);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 更新频道是否可以发弹幕
	 * 
	 * @return
	 * @author zhangbo 2015年8月19日
	 */
	public String updateOpChannelDanmu() {
		try {
			opChannelV2Service.updateOpChannelDanmu(channelId, danmu);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 更新频道是否可以发心情
	 * 
	 * @return
	 * @author zhangbo 2015年8月19日
	 */
	public String updateOpChannelMoodFlag() {
		try {
			opChannelV2Service.updateOpChannelMoodFlag(channelId, moodFlag);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新频道是否可以发织图
	 * 
	 * @return
	 * @author zhangbo 2015年8月19日
	 */
	public String updateOpChannelWorldFlag() {
		try {
			opChannelV2Service.updateOpChannelWorldFlag(channelId, worldFlag);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 分页查询频道
	 * 
	 * @return
	 */
	public String queryOpChannel() {
		try {
			// 判断是否置顶，置顶传递1，不置顶传递null，因为是查询，若不置顶，则设置为null，表示不查询top字段
			Integer top = topFlag ? 1 : null;
			opChannelV2Service.queryOpChannel(channelId, channelName, channelTypeId, ownerId, superb, valid, top, serial, danmu, moodFlag, worldFlag, themeId, page, rows, maxId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 查询频道标签 ，根据名称模糊查询，查询出匹配的所有标签
	 * 
	 * @return
	 */
	public String queryOpChannelLabel() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			JSONArray jsonArray = opChannelV2Service.queryOpChannelLabel(channelLabelNames);
			out.print(jsonArray.toString());
			out.flush();
			// JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(json.toString());
			out.flush();
			log.error(e.getMessage(), e);
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 根据频道查询与该频道关联的所有标签
	 * 
	 * @return
	 */
	public String queryOpChannelLabelList() {
		try {
			List<Map<String, Object>> queryOpChannelLabelList = opChannelV2Service.queryOpChannelLabelList(channelId);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, queryOpChannelLabelList, OptResult.ROWS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 根据ID查询频道
	 * 
	 * @return
	 */
	public String queryOpChannelByIdOrName() {
		try {
			OpChannelV2Dto dto = opChannelV2Service.queryOpChannelByIdOrName(channelId, channelName);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, dto, OptResult.JSON_KEY_OBJ, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	public String queryOpChannelByAdminUserId() {
		try {
			if (roleDao.isSuperOrOpAdminCurrentLogin()) {
				opChannelV2Service.queryOpChannel(channelId, channelName, channelTypeId, ownerId, superb, valid, null, serial, danmu, moodFlag, worldFlag, themeId, page, rows, maxId, jsonMap);
			} else {
				opChannelV2Service.queryOpChannelByAdminUserId(channelId, channelName, channelTypeId, getCurrentLoginUserId(), jsonMap);
			}
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	public String batchInsertWorldToChannel() {
		try {
			opChannelV2Service.batchInsertWorldToChannel(channelId, worldAndAuthorIdsStr);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 刷新频道缓存
	 *
	 * @return
	 * @author zhangbo 2015年6月10日
	 */
	public String refreshCache() {
		try {
			opChannelV2Service.updateChannelCache();
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 查询关联频道返回集合
	 *
	 * @return
	 * @author zhangbo 2015年6月10日
	 */
	public String queryRelatedChannel() {
		try {
			List<OpChannelLink> queryRelatedChannelList = opChannelV2Service.queryRelatedChannelList(channelId);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, queryRelatedChannelList, OptResult.ROWS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 添加关联频道
	 *
	 * @return
	 * @author zhangbo 2015年6月11日
	 */
	public String addRelatedChannel() {
		try {
			opChannelV2Service.addRelatedChannel(channelId, linkChannelId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 批量删除关联频道
	 *
	 * @return
	 * @author zhangbo 2015年6月11日
	 */
	public String deleteRelatedChannels() {
		try {
			Integer[] delIds = StringUtil.convertStringToIds(deleteIds);
			opChannelV2Service.deleteRelatedChannels(channelId, delIds);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 批量删除关联频道
	 *
	 * @return
	 * @author zhangbo 2015年6月11日
	 */
	public String updateRelatedChannelSerial() {
		String channelId = request.getParameter("reIndexChannelId");
		String[] linkChannelIds = request.getParameterValues("reIndexlinkId");
		try {
			opChannelV2Service.updateRelatedChannelSerial(Integer.valueOf(channelId), linkChannelIds);
			JSONUtil.optSuccess("排序成功", jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 保存频道置顶
	 *
	 * @return
	 * @author zhangbo 2015年6月12日
	 */
	public String saveChannelTop() {
		try {
			opChannelV2Service.saveChannelTop(channelId);
			JSONUtil.optSuccess("修改成功", jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 删除频道置顶
	 *
	 * @return
	 * @author zhangbo 2015年6月12日
	 */
	public String deleteChannelTop() {
		try {
			opChannelV2Service.deleteChannelTop(channelId);
			JSONUtil.optSuccess("修改成功", jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 修改频道标签
	 *
	 * @return
	 * @author zhangbo 2015年6月12日
	 */
	public String updateOpChannelLabel() {
		try {
			String labelIds = channelLabelIds.equals("") ? null : channelLabelIds;
			String labelNames = channelLabelNames.equals("") ? null : channelLabelNames;
			opChannelV2Service.updateOpChannelLabel(channelId, labelIds, labelNames);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}	

		return StrutsKey.JSON;
	}

	/**
	 * 查询频道专题
	 *
	 * @return
	 * @author mishengliang 2015-12-02
	 */
	public String queryChannelThemeList() {
		try {
			 opChannelV2Service.queryChannelThemeList(jsonMap);
			 JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 新增专属主题
	 *  
		*	2015年12月2日
		*	mishengliang
	 */
	public void insertChannelTheme(){
		try {
			opChannelV2Service.insertChannelTheme(themeName);
			 JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 更新专属主题
	 *  
		*	2015年12月2日
		*	mishengliang
	 */
	public void updateChannelTheme(){
		try {
			opChannelV2Service.updateChannelTheme(themeId,themeName);
			 JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 删除专属主题
	 *  
		*	2015年12月2日
		*	mishengliang
	 */
	public void deleteChannelTheme(){
		try {
			opChannelV2Service.deleteChannelTheme(themeId);
			 JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 添加自动通过id
	 * 
	 * @return
	 * @author lynch 2015-09-14
	 */
	public String addAutoRejectId() {
		try {
			opChannelV2Service.addAutoRejectId(channelId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除自动通过id
	 * 
	 * @return
	 * @author lynch 2015-09-14
	 */
	public String deleteAutoRejectId() {
		try {
			opChannelV2Service.deleteAutoRejectId(channelId);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	private Integer channelId; // 频道id
	private Integer ownerId; // 拥有者ID
	private String channelName; // 频道名称
	private String channelDesc; // 频道描述
	private String channelIcon; // 频道icon
	private String channelSubIcon; // 频道sub_icon
	private String channelBanner; // 频道banner
	private String channelReview; // 频道review，用于点击banner时，跳转到h5页面的链接
	private Integer channelTypeId; // 频道类型ID
	private String channelLabelNames; // 频道标签名称，eg。label_A,label_B,...
	private String channelLabelIds; // 频道标签ids，eg: label_A_id,label_B_id....
	private Integer superb; // 精选标记。0非精选。1精选
	private Integer valid; // 有效性 0无效。1有效
	private Integer serial; // 序号
	private Integer danmu; // 弹幕标记。0非弹幕，1弹幕
	private Integer moodFlag; // 发心情标记
	private Integer worldFlag; // 织图标记
	private String worldAndAuthorIdsStr;// worldId and authorId，eg：123-114,124-114
	private Integer themeId; // 主题ID
	private String themeName;//主题名
	private Integer linkChannelId; // 关联频道id
	private String deleteIds; // 执行删除操作的id集合
	private boolean topFlag; // 是否置顶 true置顶，false不置顶

	public void setOpChannelV2Service(OpChannelV2Service opChannelV2Service) {
		this.opChannelV2Service = opChannelV2Service;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}

	public void setChannelIcon(String channelIcon) {
		this.channelIcon = channelIcon;
	}

	public void setChannelSubIcon(String channelSubIcon) {
		this.channelSubIcon = channelSubIcon;
	}

	public void setChannelBanner(String channelBanner) {
		this.channelBanner = channelBanner;
	}

	public void setChannelReview(String channelReview) {
		this.channelReview = channelReview;
	}

	public void setChannelTypeId(Integer channelTypeId) {
		this.channelTypeId = channelTypeId;
	}

	public void setChannelLabelNames(String channelLabelNames) {
		this.channelLabelNames = channelLabelNames;
	}

	public void setChannelLabelIds(String channelLabelIds) {
		this.channelLabelIds = channelLabelIds;
	}

	public void setSuperb(Integer superb) {
		this.superb = superb;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public void setDanmu(Integer danmu) {
		this.danmu = danmu;
	}

	public void setMoodFlag(Integer moodFlag) {
		this.moodFlag = moodFlag;
	}

	public void setWorldFlag(Integer worldFlag) {
		this.worldFlag = worldFlag;
	}

	public void setWorldAndAuthorIdsStr(String worldAndAuthorIdsStr) {
		this.worldAndAuthorIdsStr = worldAndAuthorIdsStr;
	}

	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}

	public void setLinkChannelId(Integer linkChannelId) {
		this.linkChannelId = linkChannelId;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public void setDeleteIds(String deleteIds) {
		this.deleteIds = deleteIds;
	}

	public void setTopFlag(boolean topFlag) {
		this.topFlag = topFlag;
	}

}
