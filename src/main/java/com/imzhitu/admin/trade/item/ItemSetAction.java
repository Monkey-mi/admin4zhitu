package com.imzhitu.admin.trade.item;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.trade.item.service.ItemSetService;

/**
 * 商品集合控制类，用于控制商品集合banner请求
 * 
 * @author zhangbo	2015年12月8日
 *
 */
@SuppressWarnings("serial")
public class ItemSetAction extends BaseCRUDAction {
	
	/**
	 * 商品集合主键id
	 * @author zhangbo	2015年12月9日
	 */
	private Integer id;
	
	/**
	 * 商品集合标题
	 * @author zhangbo	2015年12月14日
	 */
	private String title;
	
	/**
	 * 商品集合描述
	 * @author zhangbo	2015年12月9日
	 */
	private String description;
	
	/**
	 * 商品集合图片路径
	 * @author zhangbo	2015年12月9日
	 */
	private String path;
	
	/**
	 * 商品集合缩略图路径
	 * @author zhangbo	2015年12月9日
	 */
	private String thumb;
	
	/**
	 * 商品集合点击链接地址
	 * @author zhangbo	2015年12月24日
	 */
	private String link;
	
	/**
	 * 商品集合id集合，以逗号分隔
	 * @author zhangbo	2015年12月8日
	 */
	private String ids;
	
	/**
	 * 公告id集合，以逗号分隔
	 * @author zhangbo	2015年12月8日
	 */
	private String bullentinIds;
	
	/**
	 * 是否正在展示， 1为正在展示的限时秒杀，2为正在展示的推荐商品，null为查询全部数据
	 * @author zhangbo	2015年12月9日
	 */
	private Integer cacheFlag;
	
	/**
	 * 限时秒杀截止日期
	 * @author zhangbo	2015年12月11日
	 */
	private String deadline;
	
	/**
	 * 模糊查询时使用的接收参数，有可能为根据id查询，有可能根据名称
	 * @author zhangbo	2015年12月21日
	 */
	private String idOrName;
	
	@Autowired
	private ItemSetService itemSetService;
	
	/**
	 * 选择公告添加到有奖活动中
	 * 
	 * @return
	 * @author zhangbo	2015年12月8日
	 */
	public String addAwardActivityByBullentin() {
		try {
			itemSetService.addAwardActivityByBullentin(bullentinIds);
			JSONUtil.optSuccess("添加成功", jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 构造商品集合展示列表
	 * 
	 * @return
	 * @author zhangbo	2015年12月9日
	 */
	public String buildItemSetList() {
		try {
			itemSetService.buildItemSetList(page, rows, cacheFlag, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存商品集合
	 * 
	 * @return
	 * @author zhangbo	2015年12月9日
	 */
	public String saveItemSet() {
		try {
			// 若id不存在，则为新增，否则为更新
			if ( id == null ) {
				itemSetService.addItemSet(title, description, path, thumb, link);
			} else {
				itemSetService.updateItemSet(id, title, description, path, thumb, link);
			}
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量删除商品集合banner
	 * 
	 * @return
	 * @author zhangbo	2015年12月8日
	 */
	public String batchDelete() {
		try {
			Integer[] idArray = StringUtil.convertStringToIds(ids);
			itemSetService.batchDelete(idArray);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 刷新限时秒杀redis缓存
	 * 
	 * @return
	 * @author zhangbo	2015年12月10日
	 */
	public String addSeckill() {
		try {
			// 定义并转化限时秒杀截止日期
			Date deadlineDate = null;
			if ( deadline != null && deadline != "") {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				deadlineDate = format.parse(deadline);
			}
			itemSetService.addSeckillToTemp(id, deadlineDate);
			JSONUtil.optSuccess("刷新成功", jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 从秒杀商品集合中去除传递过来的商品集合id
	 * @return
	 * @author zhangbo	2015年12月12日
	 */
	public String cancelSeckill() {
		try {
			itemSetService.cancelSeckillFromTemp(id);
			JSONUtil.optSuccess("取消成功", jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
		
	}
	
	/**
	 * 刷新商品集合redis缓存
	 * 
	 * @return
	 * @author zhangbo	2015年12月10日
	 */
	public String refreshItemSetCache() {
		try {
			itemSetService.refreshItemSetCache();
			JSONUtil.optSuccess("刷新成功", jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 重新排序
	 * 
	 * @return
	 * @author zhangbo	2015年12月12日
	 */
	public String reorder() {
		try {
			itemSetService.reorder(ids);
			JSONUtil.optSuccess("排序成功", jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 根据商品集合id或商品集合标题查询商品集合展示列表
	 * 
	 * @return
	 * @author zhangbo	2015年12月21日
	 */
	public String buildItemSetListByIdOrName() {
		try {
			itemSetService.buildItemSetListByIdOrName(idOrName, page, rows, jsonMap);
			JSONUtil.optSuccess(OptResult.QUERY_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setBullentinIds(String bullentinIds) {
		this.bullentinIds = bullentinIds;
	}

	public void setCacheFlag(Integer cacheFlag) {
		this.cacheFlag = cacheFlag;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public void setIdOrName(String idOrName) {
		this.idOrName = idOrName;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
}
