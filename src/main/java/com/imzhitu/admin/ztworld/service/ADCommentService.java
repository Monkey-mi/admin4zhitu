package com.imzhitu.admin.ztworld.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;
import com.imzhitu.admin.common.pojo.ADKeywordDTO;
import com.imzhitu.admin.common.pojo.ZTWorldCommentDto;

/**
 * 广告评论操作接口
 * 
 * @author zhangbo	2015年7月14日
 *
 */
public interface ADCommentService extends BaseService {
    
    /**
     * 查询广告评论列表
     * 
     * @param dto	评论管理数据访问对象 
     * @param page	当前页数
     * @param rows	每页查询的数量
     * @param jsonMap	返回的结果集
     * @author zhangbo	2015年7月14日
     * @throws Exception 
     */
    public void queryADCommentList(ZTWorldCommentDto dto, Integer page, Integer rows, Map<String, Object> jsonMap) throws Exception;
    
    /**
     * 更新广告评论
     * 
     * @param dto
     * @throws Exception
     * @author zhangbo	2015年7月15日
     */
    public void updateADComment(ZTWorldCommentDto dto) throws Exception;

    /**
     * 批量删除广告评论
     * 
     * @param toIds	广告评论主键id集合
     * @author zhangbo	2015年7月16日
     */
    public void deleteADCommentByIds(Integer[] ids) throws Exception;

    /**
     * 查询广告关键词列表
     * 
     * @param dto	评论管理数据访问对象
     * @param page	当前页数
     * @param rows	每页查询的数量
     * @param jsonMap	返回的结果集
     * @author zhangbo	2015年7月17日
     */
    public void queryADKeywordList(ADKeywordDTO dto, Integer page, Integer rows, Map<String, Object> jsonMap) throws Exception;

    /**
     * 批量删除广告关键词
     * 
     * @param toIds
     * @author zhangbo	2015年7月17日
     */
    public void deleteADKeywords(Integer[] ids) throws Exception;

    /**
     * 添加广告关键词
     * 
     * @param keywords	添加关键词集合
     * @return msg	添加操作的返回提示
     * @author zhangbo	2015年7月17日
     */
    public void addADKeywords(String[] keywords) throws Exception;
    
}
