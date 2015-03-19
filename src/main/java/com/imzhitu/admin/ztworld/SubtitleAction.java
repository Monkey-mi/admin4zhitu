package com.imzhitu.admin.ztworld;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.ZTWorldSubtitle;
import com.imzhitu.admin.ztworld.service.SubtitleService;

public class SubtitleAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7961847824215226856L;
	
	@Autowired
	private SubtitleService subtitleService;
	
	private ZTWorldSubtitle title = new ZTWorldSubtitle();
	
	private Integer id;
	private String ids;
	
	public ZTWorldSubtitle getTitle() {
		return title;
	}

	public void setTitle(ZTWorldSubtitle title) {
		this.title = title;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 更新字幕缓存
	 * 
	 * @return
	 */
	public String updateTitleCache() {
		try {
			subtitleService.updateSubtitleCache();
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询副标题
	 * 
	 * @return
	 */
	public String queryTitle() {
		try {
			subtitleService.buildTitles(title, start, limit, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 保存字幕
	 *
	 * @return
	 */
	public String saveTitle() {
		try {
			subtitleService.saveTitle(title);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 删除字幕
	 * @return
	 */
	public String deleteByIds() {
		try {
			subtitleService.deleteTitleByIds(ids);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 更新字幕
	 * @return
	 */
	public String updateTitle()	{
		try {
			subtitleService.updateTitle(title);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	/**
	 * 根据id查询字幕
	 * 
	 * @return
	 */
	public String queryTitleById() {
		try {
			subtitleService.queryTitleById(id);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
}
