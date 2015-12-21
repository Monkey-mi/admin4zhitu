package com.imzhitu.admin.trade.item;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.trade.item.service.ItemShowService;

public class ItemShowAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2585579360473717411L;

	/**
	 * 商品集合ID
	 */
	private Integer itemSetId;
	
	/**
	 * 织图ID
	 */
	private Integer worldId;

	/**
	 * 排序序号
	 */
	private Integer serial;
	
	/**
	 * 批量删除ids
	 */
	private String ids;
	
	@Autowired
	private ItemShowService itemShowService;
	
	/**
	 * 查询买家秀
	 * @return 
		*	2015年12月19日
		*	mishengliang
	 */
	public String bulidItemShowList(){
		try {
			itemShowService.bulidItemShowList(itemSetId,worldId,jsonMap);
			JSONUtil.optSuccess("查询成功", jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 添加买家秀记录
	 * @return 
		*	2015年12月19日
		*	mishengliang
	 */
	public String addItemShowList(){
		try {
			itemShowService.addItemShowList(itemSetId,worldId,jsonMap);
			JSONUtil.optSuccess("添加成功", jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	public  String batchDelete(){
		try {
			itemShowService.batchDelete(ids);
			JSONUtil.optSuccess("删除成功", jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新集合内商品的排序
	 * 
	 * @return
	 */
	public String updateSetItemSerial(){
		try {
			String[]  ids = request.getParameterValues("reIndexId");
			String  itemSetIdString = request.getParameter("itemSetId");
			itemSetId = Integer.parseInt(itemSetIdString);
			itemShowService.updateSetItemSerial(itemSetId, ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public Integer getItemSetId() {
		return itemSetId;
	}

	public void setItemSetId(Integer itemSetId) {
		this.itemSetId = itemSetId;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public void setWorldId(Integer worldId) {
		this.worldId = worldId;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
