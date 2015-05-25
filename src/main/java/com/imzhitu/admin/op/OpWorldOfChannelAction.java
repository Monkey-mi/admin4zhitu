/**
 * 
 */
package com.imzhitu.admin.op;

import org.aspectj.internal.lang.reflect.StringToType;
import org.springframework.beans.factory.annotation.Autowired;

import ch.qos.logback.core.joran.util.StringToObjectConverter;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
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
    
    /**
     * 频道与织图关联关系表的id（频道织图的id）
     */
    private Integer id;
    
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

    private static final String CANCEL = "取消";
    private static final String TOP_SUCCESS = "置顶成功";
    private static final String TOP_FAILED = "置顶失败";
    private static final String SUPERB_SUCCESS = "加精成功";
    private static final String SUPERB_FAILED = "加精失败";
    private static final String EMPTY_SUCCESS = "加精成功";
    private static final String EMPTY_FAILED = "加精失败";
    private static final String SYNC_SUCCESS = "同步成功";
    private static final String SYNC_FAILED = "同步失败";

    @Autowired
    private OpWorldOfCannelService opWorldOfCannelService;

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
	    e.printStackTrace();
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
	    e.printStackTrace();
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
	    opWorldOfCannelService.setValidById(getId(), isValid());
	    JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
	} catch (Exception e) {
	    e.printStackTrace();
	    JSONUtil.optFailed(OptResult.DELETE_FAILED, jsonMap);
	}
	return StrutsKey.JSON;
    }

    /**
     * 删除织图与频道关联关系（硬删除），从回收站中清空
     *
     * @return
     * @author zhangbo 2015年5月19日
     */
    public String setEmpty() {
	try {
	    opWorldOfCannelService.deleteById(getId());
	    JSONUtil.optSuccess(EMPTY_SUCCESS, jsonMap);
	} catch (Exception e) {
	    e.printStackTrace();
	    JSONUtil.optFailed(EMPTY_FAILED, jsonMap);
	}
	return StrutsKey.JSON;
    }
    
    /**
     * 同步织图与频道关联关系中间表数据到主表（包括置顶、加精、删除信息）
     *
     * @return
     * @author zhangbo 2015年5月22日
     */
    public String syncOperation() {
	try {
	    opWorldOfCannelService.syncDataFromCache(getChannelId());
	    JSONUtil.optSuccess(SYNC_SUCCESS, jsonMap);
	} catch (Exception e) {
	    e.printStackTrace();
	    JSONUtil.optFailed(SYNC_FAILED, jsonMap);
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
