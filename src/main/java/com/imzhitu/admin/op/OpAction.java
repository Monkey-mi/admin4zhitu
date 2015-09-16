package com.imzhitu.admin.op;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.HTSException;
import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.OpActivity;
import com.hts.web.common.pojo.OpActivityAward;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.op.service.OpService;
import com.imzhitu.admin.ztworld.service.ZTWorldService;

/**
 * <p>
 * 织图运营维护控制器
 * </p>
 * 
 * 创建时间：2013-8-9
 * @author ztj
 *
 */
public class OpAction extends BaseCRUDAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4933111322421784204L;
	private Integer id;
	private String ids;
	private Integer worldId;
	private String worldIds;
	private Integer valid;
	
	private String topic;
	private String topicPath;
	private String topicPathHd;

	private Integer userId;
	private String userName; //用户名
	
	private Integer squareLabel; // 广场推送标签, 默认为旅行
	private Integer superb; // 精品标记
	private Integer squareValid;
	private String worldJSON;
	
	private Integer squareType; // 广场类型
	private String notifyTip;
	
	private Integer labelId; // 标签id
	private String titlePath;
	private String titleThumbPath;
	private String channelPath;
	private String activityName;
	private String activityTitle;
	private String activityDesc;
	private String activityLink;
	private String activityLogo;
	private Date deadline;
	private Date activityDate;
	private Integer commercial = Tag.FALSE;
	private String shareTitle;
	private String shareDesc;
	private String sponsorIds;
	private String json;
	private Integer weight;
	private Integer isWinner;
	
	private String logoPath;
	
	private String iconThumbPath;
	private String iconPath;
	private String awardName;
	private String awardDesc;
	private Double price;
	private String awardLink;
	private Integer total;
	private Integer remain;
	
	private Integer activityWorldId;
	private Integer activityId;
	private String checked;
	private Boolean pass;
	private String q;
	private Integer serial;
	private Integer awardId;
	private String userIdOrUserName;
	
	@Autowired
	private OpService opService;
	
	@Autowired
	private ZTWorldService worldService;
	
	/**
	 * 查询活动
	 * 
	 * @return
	 */
	public String querySquarePushActivity() {
		try {
			opService.buildActivity(maxSerial, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询普通有效活动
	 * 
	 * @return
	 */
	public String queryNormalValidSquarePushActivity() {
		try {
			opService.buildCacheActivity(jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 添加活动
	 * 
	 * @return
	 */
	public String saveSquarePushActivity() {
		try {
			if(worldId == null && StringUtil.checkIsNULL(titlePath)) {
				throw new HTSException("封面链接和织图ID不能同时为空");
			}
			opService.saveActivity(labelId, titlePath, titleThumbPath, channelPath, activityName, 
					activityTitle, activityDesc, activityLink, activityLogo, activityDate, deadline,
					commercial, shareTitle, shareDesc, sponsorIds, valid);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新活动
	 * 
	 * @return
	 */
	public String updateActivity() {
		try {
			opService.updateActivity(labelId, titlePath, titleThumbPath, channelPath, activityName,
					activityTitle, activityDesc, activityLink, activityLogo, activityDate,
					deadline, commercial, shareTitle, shareDesc, sponsorIds, valid);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id查询活动
	 * 
	 * @return
	 */
	public String queryActivityById() {
		try {
			OpActivity activity = opService.queryActivityById(id);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, activity, OptResult.JSON_KEY_ACTIVITY, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id删除活动
	 * 
	 * @return
	 */
	public String deleteActivityById() {
		try {
			opService.deleteActivityById(id);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存活动织图
	 * 
	 * @return
	 */
	public String saveSquarePushActivityWorld() {
		try {
			opService.addActivityWorld(worldId, ids);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询广场活动
	 * 
	 * @return
	 */
	public String querySquarePushActivityWorld() {
		if(userIdOrUserName != null && !userIdOrUserName.trim().equals("")){
			try{
				setUserId(Integer.valueOf(userIdOrUserName));
			}catch(NumberFormatException e){
				setUserName(userIdOrUserName);
			}catch(Exception e) {
				JSONUtil.optFailed(e.getMessage(), jsonMap);
			}
		}
		try {
			opService.buildActivityWorld(maxId, activityId, valid, weight, superb, worldId,userId,userName,
					isWinner, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 审核广场织图
	 * 
	 * @return
	 */
	public String checkSquarePushActivityWorld() {
		try {
			opService.updateActivityWorldValid(valid, activityWorldId, activityId, worldId, userId, userName, notifyTip);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, valid, OptResult.JSON_KEY_VALID, jsonMap);
		}catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量审核活动织图
	 * 
	 * @return
	 */
	public String checkActivityWorlds() {
		try {
			opService.updateActivityWorldValids(ids, valid);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 获取活动Logo
	 * 
	 * @return
	 */
	public String queryActivityLogo() {
		try {
			opService.buildActivityLogo(maxSerial, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存活动LOGO
	 * 
	 * @return
	 */
	public String saveActivityLogo() {
		try {
			opService.saveActivityLogo(activityId, logoPath);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除活动logo
	 * 
	 * @return
	 */
	public String deleteActivityLogo() {
		try {
			opService.deleteActivityLogo(ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新logo有效性
	 * 
	 * @return
	 */
	public String updateActivityLogoValid() {
		try {
			opService.updateActivityLogoValid(ids, valid);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON; 
	}
	
	/**
	 * 更新Logo排序
	 * 
	 * @return
	 */
	public String updateActivityLogoSerial() {
		String[] ids = request.getParameterValues("reIndexId");
		try {
			opService.updateActivityLogoSerial(ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON; 
	}
	
	/**
	 * 查询活动奖品
	 * 
	 * @return
	 */
	public String queryActivityAward() {
		try {
			opService.buildActivityAward(maxSerial, start, limit, activityId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询活动所有奖品
	 * 
	 * @return
	 */
	public String queryActivityAllAward() {
		try {
			opService.buildActivityAward(activityId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 保存活动奖品
	 * 
	 * @return
	 */
	public String saveActivityAward() {
		try {
			opService.saveActivityAward(activityId, iconThumbPath, iconPath, 
					awardName, awardDesc, price, awardLink, total, remain);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 更新活动奖品
	 * 
	 * @return
	 */
	public String updateActivityAward() {
		try {
			opService.updateActivityAward(id, activityId, iconThumbPath, iconPath, 
					awardName, awardDesc, price, awardLink, total, remain, serial);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除奖品
	 * 
	 * @return
	 */
	public String deleteActivityAward() {
		try {
			opService.deleteActivityAward(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新活动奖品排序
	 * 
	 * @return
	 */
	public String updateActivityAwardSerial() {
		String[] ids = request.getParameterValues("reIndexId");
		try {
			opService.updateActivityAwardSerial(ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id获取奖品
	 * 
	 * @return
	 */
	public String queryActivityAwardById() {
		try {
			OpActivityAward award = opService.getActivityAwardById(id);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, award, OptResult.JSON_KEY_AWARD, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	
	/**
	 * 更新广场分类
	 * 
	 * @return
	 */
	public String updateOpWorldType() {
		try {
			opService.updateOpWorldType();
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON; 
	}
	
	/**
	 * 保存活动获胜者
	 * 
	 * @return
	 */
	public String saveActivityWinner() {
		try {
			opService.saveActivityWinner(activityId, worldId, userId, awardId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除活动获胜者
	 * 
	 * @return
	 */
	public String deleteActivityWinner() {
		try {
			opService.deleteActivityWinner(activityId, worldId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询活动获胜者奖品
	 * 
	 * @return
	 */
	public String queryActivityWinnerAward() {
		try {
			Integer id = opService.queryActivityWinnerAwardId(activityId, worldId);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, id, OptResult.JSON_KEY_AWARD, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新活动奖品
	 * 
	 * @return
	 */
	public String updateActivityWinnerAward() {
		try {
			opService.updateActivityWinnerAward(activityId, worldId, awardId);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.optSuccess(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新活动织图加精状态
	 * @return
	 * @author zhangbo	2015年9月16日
	 */
	public String updateActivityWorldSuperb() {
		try {
			opService.updateActivityWorldSuperb(id, superb);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optSuccess(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public String getWorldIds() {
		return worldIds;
	}

	public void setWorldIds(String worldIds) {
		this.worldIds = worldIds;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTopicPath() {
		return topicPath;
	}

	public void setTopicPath(String topicPath) {
		this.topicPath = topicPath;
	}

	public String getTopicPathHd() {
		return topicPathHd;
	}

	public void setTopicPathHd(String topicPathHd) {
		this.topicPathHd = topicPathHd;
	}

	public OpService getOpService() {
		return opService;
	}

	public void setOpService(OpService opService) {
		this.opService = opService;
	}

	public ZTWorldService getWorldService() {
		return worldService;
	}

	public void setWorldService(ZTWorldService worldService) {
		this.worldService = worldService;
	}

	public String getWorldJSON() {
		return worldJSON;
	}

	public void setWorldJSON(String worldJSON) {
		this.worldJSON = worldJSON;
	}

	public Integer getSquareLabel() {
		return squareLabel;
	}

	public void setSquareLabel(Integer squareLabel) {
		this.squareLabel = squareLabel;
	}

	public Integer getSuperb() {
		return superb;
	}

	public void setSuperb(Integer superb) {
		this.superb = superb;
	}

	public Integer getSquareType() {
		return squareType;
	}

	public void setSquareType(Integer squareType) {
		this.squareType = squareType;
	}

	public Integer getSquareValid() {
		return squareValid;
	}

	public void setSquareValid(Integer squareValid) {
		this.squareValid = squareValid;
	}

	public String getNotifyTip() {
		return notifyTip;
	}

	public void setNotifyTip(String notifyTip) {
		this.notifyTip = notifyTip;
	}

	public String getTitlePath() {
		return titlePath;
	}

	public void setTitlePath(String titlePath) {
		this.titlePath = titlePath;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public Boolean getPass() {
		return pass;
	}

	public void setPass(Boolean pass) {
		this.pass = pass;
	}

	public Integer getActivityWorldId() {
		return activityWorldId;
	}

	public void setActivityWorldId(Integer activityWorldId) {
		this.activityWorldId = activityWorldId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getActivityTitle() {
		return activityTitle;
	}

	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}

	public Integer getCommercial() {
		return commercial;
	}

	public void setCommercial(Integer commercial) {
		this.commercial = commercial;
	}

	public String getSponsorIds() {
		return sponsorIds;
	}

	public void setSponsorIds(String sponsorIds) {
		this.sponsorIds = sponsorIds;
	}

	public String getTitleThumbPath() {
		return titleThumbPath;
	}

	public void setTitleThumbPath(String titleThumbPath) {
		this.titleThumbPath = titleThumbPath;
	}

	public String getActivityLink() {
		return activityLink;
	}

	public void setActivityLink(String activityLink) {
		this.activityLink = activityLink;
	}

	public String getActivityLogo() {
		return activityLogo;
	}

	public void setActivityLogo(String activityLogo) {
		this.activityLogo = activityLogo;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	
	public String getIconThumbPath() {
		return iconThumbPath;
	}

	public void setIconThumbPath(String iconThumbPath) {
		this.iconThumbPath = iconThumbPath;
	}

	public String getAwardDesc() {
		return awardDesc;
	}

	public void setAwardDesc(String awardDesc) {
		this.awardDesc = awardDesc;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getShareTitle() {
		return shareTitle;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public String getAwardLink() {
		return awardLink;
	}

	public void setAwardLink(String awardLink) {
		this.awardLink = awardLink;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRemain() {
		return remain;
	}

	public void setRemain(Integer remain) {
		this.remain = remain;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getIsWinner() {
		return isWinner;
	}

	public void setIsWinner(Integer isWinner) {
		this.isWinner = isWinner;
	}

	public String getShareDesc() {
		return shareDesc;
	}

	public void setShareDesc(String shareDesc) {
		this.shareDesc = shareDesc;
	}

	public Integer getAwardId() {
		return awardId;
	}

	public void setAwardId(Integer awardId) {
		this.awardId = awardId;
	}
	public void setUserIdOrUserName(String userIdOrUserName){
		this.userIdOrUserName = userIdOrUserName;
	}
	public String getUserIdOrUserName(){
		return this.userIdOrUserName;
	}

	public String getChannelPath() {
		return channelPath;
	}

	public void setChannelPath(String channelPath) {
		this.channelPath = channelPath;
	}

}
