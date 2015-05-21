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
     * 织图在频道中是否置顶
     */
    private boolean isTop;

    /**
     * 织图在频道中是否加精
     */
    private boolean isSuperb;

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

    private static final String CANCEL = "取消";
    private static final String TOP_SUCCESS = "置顶成功";
    private static final String TOP_FAILED = "置顶失败";
    private static final String SUPERB_SUCCESS = "加精成功";
    private static final String SUPERB_FAILED = "加精失败";

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
	    opWorldOfCannelService.setTopById(getId(), isTop());
	    System.out.println("isTop>>>>>>>>>>>>>>>>>>>>.." + isTop());
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
	    opWorldOfCannelService.setSuperbById(getId(), isSuperb());
	    setSuperbResult(true);
	} catch (Exception e) {
	    e.printStackTrace();
	    setSuperbResult(false);
	}
	return StrutsKey.JSON;
    }

    /**
     * 删除织图与频道关联关系
     *
     * @return
     * @author zhangbo 2015年5月19日
     */
    public String deleteWorldFromCannel() {
	try {
	    opWorldOfCannelService.deleteById(getId());
	    JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
	} catch (Exception e) {
	    e.printStackTrace();
	    JSONUtil.optFailed(OptResult.DELETE_FAILED, jsonMap);
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
