package com.imzhitu.admin.ztworld.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.ADKeywordDTO;
import com.imzhitu.admin.common.pojo.ZTWorldCommentDto;
import com.imzhitu.admin.ztworld.mapper.ADCommentMapper;
import com.imzhitu.admin.ztworld.service.ADCommentService;
import com.imzhitu.admin.ztworld.service.ADKeywordCacheService;

/**
 * @author zhangbo 2015年7月14日
 *
 */
@Service
public class ADCommentServiceImpl extends BaseServiceImpl implements ADCommentService {

    private static final String publishMessage = "admin change the ADKeywordCache";

    @Autowired
    private ADCommentMapper mapper;

    @Autowired
    private ADKeywordCacheService cacheService;

    @Override
    public void queryADCommentList(ZTWorldCommentDto dto, Integer page, Integer rows, Map<String, Object> jsonMap)
	    throws Exception {
	buildNumberDtos(dto, page, rows, jsonMap, new NumberDtoListAdapter<ZTWorldCommentDto>() {

	    @Override
	    public long queryTotal(ZTWorldCommentDto dto) {
		return mapper.queryADCommentTotalCount(dto);
	    }

	    @Override
	    public List<? extends AbstractNumberDto> queryList(ZTWorldCommentDto dto) {
		return mapper.queryADComment(dto);
	    }

	});
    }

    @Override
    public void updateADComment(ZTWorldCommentDto dto) throws Exception {
	mapper.updateADComment(dto);
    }

    @Override
    public void deleteADCommentByIds(Integer[] ids) throws Exception {
	mapper.deleteADCommentByIds(ids);
    }

    @Override
    public void queryADKeywordList(ADKeywordDTO dto, Integer page, Integer rows, Map<String, Object> jsonMap)
	    throws Exception {
	buildNumberDtos(dto, page, rows, jsonMap, new NumberDtoListAdapter<ADKeywordDTO>() {

	    @Override
	    public long queryTotal(ADKeywordDTO dto) {
		return mapper.queryADKeywordTotalCount(dto);
	    }

	    @Override
	    public List<? extends AbstractNumberDto> queryList(ADKeywordDTO dto) {
		return mapper.queryADKeywordList(dto);
	    }

	});
    }

    @Override
    public void deleteADKeywords(Integer[] ids) throws Exception {

	// 先执行刷新操作，把缓存中数据持久化后，再执行删除动作
	cacheService.refreshADKeywordCache();
	mapper.deleteADKeywordByIds(ids);

	// 刷新之后发布更新消息，通知其他redis-cli
	cacheService.publisher(publishMessage);
    }

    @Override
    public void addADKeywords(String[] keywords) {

	// 从缓存中获取广告关键字集合，缓存中的关键字与数据库中存储的数据一致
	Map<String, Integer> cacheMap = cacheService.getADKeywordCacheFromRedis();

	for (String key : keywords) {
	    // 缓存中不存在的关键字，且不为空，则保存到数据库中
	    if (!cacheMap.keySet().contains(key.trim()) && !"".equals(key)) {
		ADKeywordDTO dto = new ADKeywordDTO();
		dto.setADKeyword(key.trim());
		mapper.insertADKeyword(dto);
	    }
	}

	cacheService.refreshADKeywordCache();

	// 刷新之后发布更新消息，通知其他redis-cli
	cacheService.publisher(publishMessage);
    }

}
