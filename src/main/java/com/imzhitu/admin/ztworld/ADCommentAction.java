package com.imzhitu.admin.ztworld;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.ADKeywordDTO;
import com.imzhitu.admin.common.pojo.ZTWorldCommentDto;
import com.imzhitu.admin.ztworld.dao.HTWorldCommentDao;
import com.imzhitu.admin.ztworld.service.ADCommentService;

/**
 * @author zhangbo 2015年7月14日
 *
 */
public class ADCommentAction extends BaseCRUDAction {

    /**
     * 序列号
     * 
     * @author zhangbo 2015年7月14日
     */
    private static final long serialVersionUID = -1772704045512006158L;

    /**
     * 织图评论ID
     * 
     * @author zhangbo 2015年7月15日
     */
    private Integer worldCommnetId;

    /**
     * 有效性标记，是否有效
     * 
     * @author zhangbo 2015年7月15日
     */
    private Integer valid;

    /**
     * 广告评论主键id集合
     * 
     * @author zhangbo 2015年7月16日
     */
    private String ids;

    /**
     * @param worldCommnetId
     *            the worldCommnetId to set
     */
    public void setWorldCommnetId(Integer worldCommnetId) {
	this.worldCommnetId = worldCommnetId;
    }

    /**
     * @param valid
     *            the valid to set
     */
    public void setValid(Integer valid) {
	this.valid = valid;
    }

    /**
     * @param ids
     *            the ids to set
     */
    public void setIds(String ids) {
	this.ids = ids;
    }

    @Autowired
    private ADCommentService adService;

    @Autowired
    private HTWorldCommentDao htWorldCommentDao;

    /**
     * 查询广告评论列表
     * 
     * @return
     * @author zhangbo 2015年7月14日
     */
    public String queryADCommentList() {
	try {
	    ZTWorldCommentDto dto = new ZTWorldCommentDto();
	    adService.queryADCommentList(dto, page, rows, jsonMap);
	    JSONUtil.optSuccess(jsonMap);
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	}
	return StrutsKey.JSON;
    }

    /**
     * 更新广告评论与织图评论（同一条评论，存储位置不同）
     * 
     * @return
     * @author zhangbo 2015年7月14日
     */
    public String updateADComment() {
	try {
	    // 更新广告评论
	    ZTWorldCommentDto dto = new ZTWorldCommentDto();
	    dto.setId(worldCommnetId);
	    dto.setValid(valid);
	    adService.updateADComment(dto);

	    // 更新织图评论
	    htWorldCommentDao.updateCommentValid(worldCommnetId, valid);
	    JSONUtil.optSuccess(jsonMap);
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	}
	return StrutsKey.JSON;
    }

    /**
     * 批量删除广告评论（在广告屏蔽表中，织图评论表中依然保留，只是有效性设置为无效）
     * 
     * @return
     * @author zhangbo 2015年7月17日
     */
    public String deleteADComments() {
	try {
	    Integer[] toIds = StringUtil.convertStringToIds(ids);
	    adService.deleteADCommentByIds(toIds);

	    JSONUtil.optSuccess(jsonMap);
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	}
	return StrutsKey.JSON;
    }

    /**
     * 查询广告关键词列表
     * 
     * @return
     * @author zhangbo 2015年7月17日
     */
    public String queryADKeywordList() {
	try {
	    ADKeywordDTO dto = new ADKeywordDTO();
	    adService.queryADKeywordList(dto, page, rows, jsonMap);

	    JSONUtil.optSuccess(jsonMap);
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	}
	return StrutsKey.JSON;
    }

    /**
     * 批量删除广告关键词
     * 
     * @return
     * @author zhangbo 2015年7月17日
     */
    public String deleteADKeywords() {
	try {
	    Integer[] toIds = StringUtil.convertStringToIds(ids);
	    adService.deleteADKeywords(toIds);

	    JSONUtil.optSuccess(jsonMap);
	} catch (Exception e) {
	    JSONUtil.optFailed(e.getMessage(), jsonMap);
	}
	return StrutsKey.JSON;
    }

    /**
     * 添加广告关键词
     * 
     * @return
     * @author zhangbo	2015年7月17日
     */
    public String addADKeywords() {
	String[] keywords = request.getParameterValues("keyword");
	try {
	    adService.addADKeywords(keywords);
	    JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
	} catch (Exception e) {
	    JSONUtil.optFailed("有重复数据》》》》" + e.getMessage(), jsonMap);
	}
	return StrutsKey.JSON;
    }

}
