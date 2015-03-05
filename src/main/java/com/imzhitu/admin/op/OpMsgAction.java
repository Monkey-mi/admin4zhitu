package com.imzhitu.admin.op;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.HTSException;
import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.pojo.OpNotice;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.OpSysMsg;
import com.imzhitu.admin.op.service.OpMsgService;

public class OpMsgAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9002272213062904010L;
	
	@Autowired
	private OpMsgService opMsgService;
	
	private String ids;
	private Integer id;
	private Integer phoneCode;
	private String link;
	private String path;
	
	private OpSysMsg sysMsg = new OpSysMsg();
	
	

	/**
	 * 查询公告列表
	 * 
	 * @return
	 */
	public String queryNotice() {
		try {
			opMsgService.buildNotice(jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存公告
	 * 
	 * @return
	 */
	public String saveNotice() {
		try {
			opMsgService.saveNotice(path, link, phoneCode);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新公告
	 * 
	 * @return
	 */
	public String updateNotice() {
		try {
			opMsgService.updateNotice(id, path, link, phoneCode);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据phoneCode查询公告
	 * 
	 * @return
	 */
	public String queryNoticeByPhoneCode() {
		try {
			OpNotice notice = opMsgService.getNotice(phoneCode);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, notice, OptResult.JSON_KEY_MSG, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 删除公告
	 * 
	 * @return
	 */
	public String deleteNotice() {
		try {
			opMsgService.deleteNotice(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String saveAppMsg() {
		Boolean inApp = false;
		Boolean notify = false;
		try {
			String[] notice = request.getParameterValues("notice");
			if(notice == null) {
				throw new HTSException("必须选择通知位置");
			}
			for(String n : notice) {
				if(n.equals("inApp")) {
					inApp = true;
				} else if(n.equals("notify")) {
					notify = true;
				}
			}
			opMsgService.pushAppMsg(sysMsg, inApp, notify);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
		
	}

	public OpMsgService getOpMsgService() {
		return opMsgService;
	}

	public void setOpMsgService(OpMsgService opMsgService) {
		this.opMsgService = opMsgService;
	}

	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public OpSysMsg getSysMsg() {
		return sysMsg;
	}

	public void setSysMsg(OpSysMsg sysMsg) {
		this.sysMsg = sysMsg;
	}
	
}
