package com.imzhitu.admin.qiniu;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseAction;
import com.imzhitu.admin.qiniu.service.QiniuService;

/**
 * <p>
 * 七牛控制器
 * </p>
 * 
 * 创建时间：2014-1-14
 * 
 * @author ztj
 * 
 */
public class QiniuAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8609088920467411299L;
	
	@Autowired
	private QiniuService qiniuService;

	public QiniuService getQiniuService() {
		return qiniuService;
	}

	public void setQiniuService(QiniuService qiniuService) {
		this.qiniuService = qiniuService;
	}

	/**
	 * 生成uptoken
	 * 
	 * @return
	 */
	public String uptoken() {
		try {
			String uptoken = qiniuService.getToken();
			jsonMap.put("uptoken", uptoken);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

}
