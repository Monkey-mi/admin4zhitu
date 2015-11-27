package com.imzhitu.admin.ztworld.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.Tag;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.ADKeywordDTO;
import com.imzhitu.admin.common.pojo.ZTWorldCommentDto;
import com.imzhitu.admin.ztworld.dao.HTWorldCommentDao;
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
	
	@Autowired
    private HTWorldCommentDao htWorldCommentDao;

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
	public void updateADComment(Integer adCommentId, Integer valid) throws Exception {
		
		// 根据广告评论主键id，查询此条广告评论
		ZTWorldCommentDto adComment = mapper.queryADCommentById(adCommentId);

		// 根据评论内容，织图id，作者id，查询此条评论
		Map<String, Object> attrMap = new HashMap<String, Object>();
		attrMap.put("content", adComment.getContent());
		attrMap.put("world_id", adComment.getWorldId());
		attrMap.put("author_id", adComment.getAuthorId());
		
		Map<String, Object> userAttrMap = new HashMap<String, Object>();
		userAttrMap.put("id", adComment.getAuthorId());
		
		List<ZTWorldCommentDto> worldCommentList = htWorldCommentDao.queryComment(attrMap , userAttrMap, new RowSelection(1, 1));
		
		//TODO
		/**
		 * @modify zxx 2015年11月13日 18:21:21
		 * 现在htworld_comment这张表没有valid和shield字段，
		 * 以前valid=0或shield=1的评论一律从htworld_comment中删除、并插入htworld_comment_delete对应的接口htWorldCommentDao。deleteCommentById
		 * 
		 * 若是以前valid=1或shield=0的操作是：从htworld_comment_delete表中删除，并重新插入到htworld_comment表中，对应的接口htWorldCommentDao。recoveryCommentById
		 */
		if ( worldCommentList != null && worldCommentList.size() != 0) {
			// 更新织图评论的有效性，理论上只能获取出唯一条评论，故取第一个
//			htWorldCommentDao.updateCommentValid(worldCommentList.get(0).getId(), valid);
			
			// 若为生效，则屏蔽状态设置为0，若为失效，屏蔽状态设置为1
			if (valid == Tag.TRUE) {
//				htWorldCommentDao.updateCommentShield(worldCommentList.get(0).getId(), Tag.FALSE);
			} else {
//				htWorldCommentDao.updateCommentShield(worldCommentList.get(0).getId(), Tag.TRUE);
			}
			
			// 更新广告评论表屏蔽状态
			mapper.updateADComment(adCommentId, valid);
		} else {
			throw new Exception("这条评论已经被删除，可不必操作，请知。");
		}
		
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
		cacheService.setADKeywordCacheToDB();

		mapper.deleteADKeywordByIds(ids);

		cacheService.refreshADKeywordCacheToRedis();

		// 刷新之后发布更新消息，通知其他redis-cli
		cacheService.publisher(publishMessage);
	}

	@Override
	public void addADKeywords(String[] keywords) throws Exception {

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

		cacheService.setADKeywordCacheToDB();
		cacheService.refreshADKeywordCacheToRedis();

		// 刷新之后发布更新消息，通知其他redis-cli
		cacheService.publisher(publishMessage);
	}

}
