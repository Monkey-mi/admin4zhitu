package com.imzhitu.admin.trade.item;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.trade.item.pojo.Item;
import com.imzhitu.admin.trade.item.service.ItemService;

/**
 * 商品模块REST接口
 * 
 * @author lynch
 *
 */
public class ItemAction extends BaseCRUDAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6495522537093771785L;

	private Integer id;
	private String ids;
	private Integer itemId;
	private Integer itemSetId;
	private Item item = new Item();
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 保存商品
	 * 
	 * @return
	 */
	public String saveItem() {
		try {
			itemService.saveItem(item);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 更新商品
	 * 
	 * @return
	 */
	public String updateItem() {
		try {
			itemService.updateItem(item);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询商品列表
	 * 
	 * @return
	 */
	public String queryItemList() {
		try {
			itemService.buildItemList(item, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据id查询商品
	 * 
	 * @return
	 */
	public String queryItemById() {
		try {
			Item item = itemService.queryItemById(id);
			JSONUtil.optResult(OptResult.OPT_SUCCESS, item, OptResult.JSON_KEY_OBJ, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String batchDeleteItem() {
		try {
			itemService.batchDeleteItem(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询集合内的商品列表
	 * 
	 * @return
	 */
	public String querySetItem() {
		try {
			itemService.buildSetItem(item, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 删除集合中的商品
	 * 
	 * @return 
	 */
	public String batchDeleteSetItem(){
		try {
			itemService.batchDeleteSetItem(itemSetId, ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
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
			itemService.updateSetItemSerial(itemSetId, ids);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 添加商品到集合
	 * 
	 * @return
	 */
	public String saveSetItem() {
		try {
			itemService.saveSetItem(itemSetId, itemId);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public Integer getId() {
		return id;
	}

	public String getIds() {
		return ids;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getItemSetId() {
		return itemSetId;
	}

	public void setItemSetId(Integer itemSetId) {
		this.itemSetId = itemSetId;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	

}
