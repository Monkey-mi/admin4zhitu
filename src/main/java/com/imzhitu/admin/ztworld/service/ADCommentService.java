package com.imzhitu.admin.ztworld.service;

import java.util.Map;

import com.hts.web.common.service.BaseService;
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
    
}
