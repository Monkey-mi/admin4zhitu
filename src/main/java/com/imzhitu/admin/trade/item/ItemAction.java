package com.imzhitu.admin.trade.item;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.trade.item.service.ItemService;

/**
 * 商品控制类
 * 
 * @author zhangbo	2015年12月8日
 *
 */
@SuppressWarnings("serial")
public class ItemAction extends BaseCRUDAction {
	
	/**
	 * 商品主键id
	 * @author zhangbo	2015年12月9日
	 */
	private Integer id;
	
	/**
	 * 商品图片路径
	 * @author zhangbo	2015年12月9日
	 */
	private String imgPath;
	
	/**
	 * 商品缩略图路径
	 * @author zhangbo	2015年12月9日
	 */
	private String imgThumb;
	
	/**
	 * 商品名称
	 * @author zhangbo	2015年12月9日
	 */
	private String name;
	
	/**
	 * 商品简介
	 * @author zhangbo	2015年12月9日
	 */
	private String summary;
	
	/**
	 * 商品详情描述
	 * @author zhangbo	2015年12月9日
	 */
	private String description;
	
	/**
	 * 商品关联织图id
	 * @author zhangbo	2015年12月9日
	 */
	private Integer worldId;
	
	/**
	 * 商品价格
	 * @author zhangbo	2015年12月9日
	 */
	private BigDecimal price;
	
	/**
	 * 商品促销价
	 * @author zhangbo	2015年12月9日
	 */
	private BigDecimal sale;
	
	/**
	 * 商品销售量
	 * @author zhangbo	2015年12月9日
	 */
	private Integer sales;
	
	/**
	 * 商品库存量
	 * @author zhangbo	2015年12月9日
	 */
	private Integer stock;
	
	/**
	 * 淘宝商品真实id
	 * @author zhangbo	2015年12月9日
	 */
	private Integer trueItemId;
	
	/**
	 * 淘宝商品类型（1：淘宝，2：天猫）
	 * @author zhangbo	2015年12月9日
	 */
	private Integer trueItemType;
	
	/**
	 * 商品类别
	 * @author zhangbo	2015年12月9日
	 */
	private Integer categoryId;
	
	/**
	 * 商品品牌
	 * @author zhangbo	2015年12月9日
	 */
	private Integer brandId;
	
	/**
	 * 点赞数量
	 * @author zhangbo	2015年12月10日
	 */
	private Integer like;
	
	/**
	 * 商品id的集合，以逗号分隔
	 * @author zhangbo	2015年12月9日
	 */
	private String ids;
	
	/**
	 * 商品集合id
	 * @author mishengliang
	 */
	private Integer itemSetId;
	
	/**
	 * 商品链接
	 */
	private String link;
	
	/**
	 * 是否查询集合下商品的Flag
	 * @author mishengliang
	 */
	private Integer isForItemSet;
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 保存商品
	 * 
	 * @return
	 * @author zhangbo	2015年12月9日
	 */
	public String saveitem() {
		try {
			if ( id == null ) {
				itemService.insertItem(name,imgPath,imgThumb,summary,description,worldId,price,sale,sales,stock,trueItemId,trueItemType,categoryId,brandId,like,link);
			} else {
				itemService.updateItem(id,name,imgPath,imgThumb,summary,description,worldId,price,sale,sales,stock,trueItemId,trueItemType,categoryId,brandId,like,link);
			}
			
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 构造商品列表
	 * 现在只有通过商品名搜索，后续按需求增加字段搜索
	 * @return
	 * @author zhangbo	2015年12月9日
	 */
	public String buildItemList() {
		try {
			if(isForItemSet == 1){//是否为查询set下的商品
				itemService.buildItemListForSetItem(itemSetId,page, rows, jsonMap);
			}else{
				itemService.buildItemList(name,itemSetId,page, rows, jsonMap);
			}
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	/**
	 * 构造商品列表通过商品集合ID
	 * 
	 * @return
	 * @author zhangbo	2015年12月9日
	 */
	public String buildItemListBySetId() {
		try {
			itemService.buildItemListBySetId(itemSetId,page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchDelete() {
		try {
			Integer[] idArray = StringUtil.convertStringToIds(ids);
			itemService.batchDelete(idArray);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 删除集合中的商品
	 * @return 
		*	2015年12月12日
		*	mishengliang
	 */
	public String batchDeleteItemFromSet(){
		try {
			Integer[] idArray = StringUtil.convertStringToIds(ids);
			itemService.batchDeleteItemFromSet(itemSetId,idArray);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String reOrderIndexforItem(){
		try {
			String[]  ids = request.getParameterValues("reIndexId");
			itemService.reOrderIndexforItem(ids);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public Integer getId() {
		return id;
	}

	public String getImgPath() {
		return imgPath;
	}

	public String getImgThumb() {
		return imgThumb;
	}

	public String getName() {
		return name;
	}

	public String getSummary() {
		return summary;
	}

	public String getDescription() {
		return description;
	}

	public Integer getWorldId() {
		return worldId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getSale() {
		return sale;
	}

	public Integer getSales() {
		return sales;
	}

	public Integer getStock() {
		return stock;
	}

	public Integer getTrueItemId() {
		return trueItemId;
	}

	public Integer getTrueItemType() {
		return trueItemType;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public Integer getLike() {
		return like;
	}

	public String getIds() {
		return ids;
	}

public void setId(Integer id) {
		this.id = id;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public void setImgThumb(String imgThumb) {
		this.imgThumb = imgThumb;
	}

	public void setName(String name) {
		this.name = name;
	}

public void setSummary(String summary) {
		this.summary = summary;
	}

public void setDescription(String description) {
		this.description = description;
	}

public void setWorldId(Integer worldId) {
	this.worldId = worldId;
}

public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setSale(BigDecimal sale) {
		this.sale = sale;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

public void setStock(Integer stock) {
		this.stock = stock;
	}

	public void setTrueItemId(Integer trueItemId) {
		this.trueItemId = trueItemId;
	}

public void setTrueItemType(Integer trueItemType) {
		this.trueItemType = trueItemType;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public void setLike(Integer like) {
		this.like = like;
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

	public Integer getIsForItemSet() {
		return isForItemSet;
	}

	public void setIsForItemSet(Integer isForItemSet) {
		this.isForItemSet = isForItemSet;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
}
