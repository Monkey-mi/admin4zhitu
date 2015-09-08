/**
 * 
 */
package com.imzhitu.admin.op;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.ChannelWorldRecyclerDto;
import com.imzhitu.admin.common.pojo.OpWorldOfCannelDto;
import com.imzhitu.admin.op.service.ChannelService;
import com.imzhitu.admin.op.service.OpWorldOfCannelService;

/**
 *
 * @author zhangbo 2015年5月19日
 */
public class OpWorldOfChannelAction extends BaseCRUDAction {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1266080732618521674L;
    
    private Logger log = Logger.getLogger(OpWorldOfChannelAction.class);
    
    /**
     * 频道与织图关联关系表的id（频道织图的id）
     */
    private Integer id;
    
    /**
     * 频道与织图关联关系表的id集合
     */
    private String ids;
    
    /**
     * 频道id
     */
    private Integer channelId;
    
    /**
     * 织图id
     */
    private Integer worldId;

    /**
     * 织图在频道中是否置顶
     */
    private boolean isTop;

    /**
     * 织图在频道中是否加精
     */
    private boolean isSuperb;
    
    /**
     * 织图在频道中是否生效
     */
    private boolean isValid;
    
    /**
     * 织图在频道中删除的理由
     */
    private String deleteReason;
    
    // 如下是单个频道中回收站功能接收的参数，织图id使用worldId
    /**
     * 用户ID
     */
    private Integer authorId;
    
    /**
     * 关键字，用于根据织图描述的查询
     */
    private String keyword;

    /**
     * @return the id
     */
    public Integer getId() {
	return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
	this.id = id;
    }

    /**
     * @return the ids
     */
    public String getIds() {
        return ids;
    }

    /**
     * @param ids the ids to set
     */
    public void setIds(String ids) {
        this.ids = ids;
    }

    /**
     * @return the channelId
     */
    public Integer getChannelId() {
        return channelId;
    }

    /**
     * @param channelId the channelId to set
     */
    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    /**
     * @return the worldId
     */
    public Integer getWorldId() {
        return worldId;
    }

    /**
     * @param worldId the worldId to set
     */
    public void setWorldId(Integer worldId) {
        this.worldId = worldId;
    }

    /**
     * @return the isTop
     */
    public boolean isTop() {
        return isTop;
    }

    /**
     * @param isTop the isTop to set
     */
    public void setIsTop(boolean isTop) {
        this.isTop = isTop;
    }

    /**
     * @return the isSuperb
     */
    public boolean isSuperb() {
        return isSuperb;
    }

    /**
     * @param isSuperb the isSuperb to set
     */
    public void setIsSuperb(boolean isSuperb) {
        this.isSuperb = isSuperb;
    }

    /**
     * @return the isValid
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * @param isValid the isValid to set
     */
    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    /**
     * @return the deleteReason
     */
    public String getDeleteReason() {
        return deleteReason;
    }

    /**
     * @param deleteReason the deleteReason to set
     */
    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
    }

    /**
     * @return the authorId
     */
    public Integer getAuthorId() {
        return authorId;
    }

    /**
     * @param authorId the authorId to set
     */
    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    private static final String CANCEL = "取消";
    private static final String TOP_SUCCESS = "置顶成功";
    private static final String TOP_FAILED = "置顶失败";
    private static final String SUPERB_SUCCESS = "加精成功";
    private static final String SUPERB_FAILED = "加精失败";
    private static final String EMPTY_SUCCESS = "永久删除成功";
    private static final String EMPTY_FAILED = "永久删除失败";
    private static final String SYNC_SUCCESS = "同步成功";
    private static final String RECOVER_SUCCESS = "恢复成功";
    
    @Autowired
    private OpWorldOfCannelService opWorldOfCannelService;
    
    @Autowired
    private ChannelService cannelService;
    
    @Autowired
    private com.hts.web.operations.service.ChannelService webCannelService;

    /**
     * 频道织图中置顶操作
     *
     * @return
     * @author zhangbo 2015年5月19日
     */
    public String setTopOperation() {
	try {
	    opWorldOfCannelService.setTopToCache(getId(), getChannelId(), getWorldId(), isTop());
	    setTopResult(true);
	} catch (Exception e) {
	    log.info(e.getStackTrace().toString());
	    setTopResult(false);
	}
	return StrutsKey.JSON;
    }

    /**
     * 频道织图中加精操作
     *
     * @return
     * @author zhangbo 2015年5月19日
     */
    public String setSuperbOperation() {
	try {
	    opWorldOfCannelService.setSuperbById(getId(), getChannelId(), getWorldId(), isSuperb());
	    setSuperbResult(true);
	} catch (Exception e) {
	    log.info(e.getStackTrace().toString());
	    setSuperbResult(false);
	}
	return StrutsKey.JSON;
    }
    
    /**
     * 设置是否失效
     * 1、在页面点击删除，设置valid为0
     * 2、在回收站中恢复，设置valid为1
     *
     * @return
     * @author zhangbo 2015年5月19日
     */
    public String setValidOperation() {
	try {
	    // 若为有效，即在回收站中恢复
	    if( isValid() ) {
		// 在回收站中恢复，为批量，且valid设置为1
		cannelService.updateChannelWorldValid(getIds(),1);
		
		// 由于是恢复，那么要把中间表中对应数据删除掉
		Integer[] ids = StringUtil.convertStringToIds(getIds());
		opWorldOfCannelService.deleteByIdsFromCache(ids);
		
		JSONUtil.optSuccess(RECOVER_SUCCESS, jsonMap);
	    } 
	    // 若为无效，即在页面点击删除
	    else {
		// 在主表中更新被删除的valid字段为0
		cannelService.updateChannelWorldValid(getId().toString(),0);
		
		OpWorldOfCannelDto dto = new OpWorldOfCannelDto();
		dto.setId(getId());
		dto.setChannelId(getChannelId());
		dto.setWorldId(getWorldId());
		dto.setValid(0);
		dto.setDeleteReason(getDeleteReason());
		// 当前被删除的信息记录到中间表
		opWorldOfCannelService.setDataToCache(dto);
		
		JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
	    }
	    // 更新织图和图片总数,删除时候调用
	    webCannelService.updateWorldAndChildCount(getChannelId());
	} catch (Exception e) {
	    log.info(e.getStackTrace().toString());
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	}
	return StrutsKey.JSON;
    }

    /**
     * 删除织图与频道关联关系（硬删除），从回收站中清空
     *
     * @return
     * @author zhangbo 2015年5月19日
     */
    public String setEmptyByIds() {
	try {
	    opWorldOfCannelService.deleteByIdsFromCache(StringUtil.convertStringToIds(getIds()));
	    cannelService.deleteChannelWorlds(getIds());
	    // 更新织图和图片总数,删除时候调用
	    webCannelService.updateWorldAndChildCount(getChannelId());
	    JSONUtil.optSuccess(EMPTY_SUCCESS, jsonMap);
	} catch (Exception e) {
	    log.info(e.getStackTrace().toString());
	    JSONUtil.optFailed(EMPTY_FAILED, jsonMap);
	}
	return StrutsKey.JSON;
    }
    
    /**
     * 同步织图与频道关联关系中间表数据到主表（包括置顶、加精信息）
     *
     * @return
     * @author zhangbo 2015年5月22日
     */
    public String syncOperation() {
	try {
	    opWorldOfCannelService.syncDataFromCache(getChannelId());
	    // 更新精选总数, 加精和取消加精调用
	    webCannelService.updateSuperbCount(getChannelId());
	    JSONUtil.optSuccess(SYNC_SUCCESS, jsonMap);
	} catch (Exception e) {
	    log.info(e.getStackTrace().toString());
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	}
	return StrutsKey.JSON;
    }
    
    /**
     * 回收站列表查询
     *
     * @return
     * @author zhangbo 2015年5月27日
     */
    public String queryRecyclerdData(){
	try {
	    ChannelWorldRecyclerDto dto = new ChannelWorldRecyclerDto();
	    dto.setChannelId(getChannelId());
	    if ( getWorldId() != null ) {
		dto.setWorldId(getWorldId());
	    } else if ( getAuthorId() != null ) {
		dto.setAuthorId(getAuthorId());
	    } else if ( getKeyword() != null && !getKeyword().isEmpty() ) {
		dto.setWorldDesc(getKeyword());
	    }
	    opWorldOfCannelService.buildRecyclerList(dto, page, rows, jsonMap);
	    JSONUtil.optSuccess(jsonMap);
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	}
	return StrutsKey.JSON;
    }
    /**
     * 设置置顶操作的返回值
     * 
     * @param isSuccess
     *            是否操作成功，true：成功，false：失败
     *
     * @author zhangbo 2015年5月20日
     */
    private void setTopResult(boolean isSuccess) {

	// 定义返回前台的提示
	String msg = new String();

	// 若不置顶，则添加前缀“取消”
	if (!isTop()) {
	    msg = msg + CANCEL;
	}
	if (isSuccess) {
	    msg = msg + TOP_SUCCESS;
	    JSONUtil.optSuccess(msg, jsonMap);
	} else {
	    msg = msg + TOP_FAILED;
	    JSONUtil.optFailed(msg, jsonMap);
	}
    }

    /**
     * 设置加精操作的返回值
     *
     * @author zhangbo 2015年5月20日
     */
    private void setSuperbResult(boolean isSuccess) {

	// 定义返回前台的提示
	String msg = new String();

	// 若不加精，则添加前缀“取消”
	if (!isSuperb()) {
	    msg = msg + CANCEL;
	}
	if (isSuccess) {
	    msg = msg + SUPERB_SUCCESS;
	    JSONUtil.optSuccess(msg, jsonMap);
	} else {
	    msg = msg + SUPERB_FAILED;
	    JSONUtil.optFailed(msg, jsonMap);
	}
    }
    
}
