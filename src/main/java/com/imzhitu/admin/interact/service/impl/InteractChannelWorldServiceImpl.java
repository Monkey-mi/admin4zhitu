package com.imzhitu.admin.interact.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imzhitu.admin.channel.mapper.ChannelWorldInteractCommentMapper;
import com.imzhitu.admin.channel.service.ChannelWorldInteractSchedulerService;
import com.imzhitu.admin.common.pojo.ChannelWorldInteractComment;
import com.imzhitu.admin.common.pojo.ChannelWorldInteractScheduler;
import com.imzhitu.admin.common.pojo.InteractChannelLevel;
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.common.pojo.ZTWorldDto;
import com.imzhitu.admin.common.util.AdminUtil;
import com.imzhitu.admin.interact.service.InteractChannelLevelService;
import com.imzhitu.admin.interact.service.InteractChannelWorldService;
import com.imzhitu.admin.interact.service.InteractWorldService;
import com.imzhitu.admin.op.mapper.ChannelWorldMapper;
import com.imzhitu.admin.ztworld.service.ZTWorldService;

/**
 * @author zhangbo	2015年10月30日
 *
 */
@Service
public class InteractChannelWorldServiceImpl implements InteractChannelWorldService {
	
	@Autowired
	private InteractChannelLevelService channelLevelService;
	
	@Autowired
	private ChannelWorldMapper channelWolrdMapper;
	
	@Autowired
	private InteractWorldService interactWorldService;
	
	@Autowired
	private ChannelWorldInteractCommentMapper channelWorldInteractCommentMapper;
	
	@Autowired
	private ZTWorldService worldService;
	
	@Autowired
	private ChannelWorldInteractSchedulerService channelWorldInteractSchedulerService;

	@Override
	public void saveChannelWorldInteractComment(Integer channelId, Integer worldId, Integer[] commentIds) throws Exception {
		for (Integer commentId : commentIds) {
			if ( commentId != null ) {
				channelWorldInteractCommentMapper.insert(channelId, worldId, commentId);
			}
		}
	}
	
	@Override
	public void saveChannelWorldInteract(Integer channelId, Integer worldId) throws Exception {
		// 定义频道等级对象
		InteractChannelLevel interactChannelLevel = new InteractChannelLevel();
		// 根据频道id，获取频道等级
		List<InteractChannelLevel> channelLevelList = channelLevelService.queryChannelLevel(channelId, null);
		
		// 因为频道id是唯一的，所以查询得出的结论应该若不为空，则直接去第一个元素
		if ( channelLevelList != null && channelLevelList.size() !=0 ) {
			interactChannelLevel = channelLevelList.get(0);
		}
		
		// 定义添加的点赞数与播放数
		Integer likeCount = 0;
		Integer playCount = 0;
		Integer minFansCount = 0;
		Integer maxFansCount = 0;
		
		// 获取频道织图对象
		OpChannelWorld channelWorld = channelWolrdMapper.queryChannelWorldByWorldId(worldId, channelId);
		
		// 频道织图精选与否，决定了加赞，加点播，加评论的数量
		if ( channelWorld.getSuperb() == 1 ) {
			likeCount = interactChannelLevel.getSuperMaxLikeCount() + interactChannelLevel.getSuperMinLikeCount()/2;
			playCount = interactChannelLevel.getSuperMaxClickCount() + interactChannelLevel.getSuperMinClickCount()/2;
			minFansCount = interactChannelLevel.getSuperMinFollowCount();
			maxFansCount = interactChannelLevel.getSuperMaxFollowCount();
		} else {
			likeCount = interactChannelLevel.getUnSuperMaxLikeCount() + interactChannelLevel.getUnSuperMinLikeCount()/2;
			playCount = interactChannelLevel.getUnSuperMaxClickCount() + interactChannelLevel.getUnSuperMinClickCount()/2;
			minFansCount = interactChannelLevel.getUnSuperMinFollowCount();
			maxFansCount = interactChannelLevel.getUnSuperMaxFollowCount();
		}
		
		// 定义频道织图互动的评论关系中，评论id的集合
		List<Integer> commnetIdList = new ArrayList<Integer>();
		List<ChannelWorldInteractComment> channelWorldCommentList = channelWorldInteractCommentMapper.queryNotCompletedCommentByChannelIdAndWorldId(channelId, worldId);
		for (ChannelWorldInteractComment interactComment : channelWorldCommentList) {
			commnetIdList.add(interactComment.getCommentId());
			
			// 并且将频道织图互动的评论关系，设置为已完成
			channelWorldInteractCommentMapper.complete(interactComment.getChannelId(), interactComment.getWorldId(), interactComment.getCommentId());
		}
		
		// 定义评论id字符串数组
		String[] commentIds = new String[commnetIdList.size()];
		
		for (int i = 0; i < commnetIdList.size(); i++) {
			commentIds[i] = String.valueOf(commnetIdList.get(i));
		}
		
		interactWorldService.saveInteractV3(worldId, playCount, likeCount, commentIds, interactChannelLevel.getMinuteTime());
		
		// 获取织图信息
		ZTWorldDto world = worldService.getZTWorldByWorldId(worldId);
		// 为织图作者添加粉丝
		interactWorldService.saveUserInteract(world.getAuthorId(), AdminUtil.GetRandamNum(minFansCount, maxFansCount), interactChannelLevel.getMinuteTime());
	}
	
	@Override
	public Map<String, Integer> queryWorldUNInteractCount(Integer channelId, Integer worldId) throws Exception{
		// 定义频道织图规划互动添加的点赞数，播放数，评论数
		int clickCount = 0;
		int likedCount = 0;
		int commentCount = 0;
		
		// 先查询频道织图是否已经做了规划互动，若有，则进行点赞数、播放数、评论数的展示，若没有直接展示默认值0
		List<ChannelWorldInteractScheduler> schedulerList = channelWorldInteractSchedulerService.queryChannelWorldInteractSchedulerNotCompleteList(channelId, worldId);
		if ( schedulerList != null && schedulerList.size() != 0 ) {
			// 评论数量应与已经规划好的，但是未执行的互动评论数量一致
			List<ChannelWorldInteractComment> channelWorldCommentList = channelWorldInteractCommentMapper.queryNotCompletedCommentByChannelIdAndWorldId(channelId, worldId);
			if ( channelWorldCommentList != null ) {
				commentCount = channelWorldCommentList.size();
			}
			
			// 定义频道等级对象
			InteractChannelLevel interactChannelLevel = new InteractChannelLevel();
			// 根据频道id，获取频道等级
			List<InteractChannelLevel> channelLevelList = channelLevelService.queryChannelLevel(channelId, null);
			
			// 因为频道id是唯一的，所以查询得出的结论应该若不为空，则直接去第一个元素
			if ( channelLevelList != null && channelLevelList.size() !=0 ) {
				interactChannelLevel = channelLevelList.get(0);
			}
			
			// 若频道存在等级，则获取相应的数据
			if(interactChannelLevel != null){
				// 获取频道织图对象
				OpChannelWorld channelWorld = channelWolrdMapper.queryChannelWorldByWorldId(worldId, channelId);
				
				// 频道织图精选与否，决定了点赞数，点播量，评论数不同的获取方法，但是目前都取频道等级中，最少量返回前台，主要是给操作者一个数量印象
				if ( channelWorld.getSuperb() == 1 ) {
					clickCount = interactChannelLevel.getSuperMinClickCount();
					likedCount = interactChannelLevel.getSuperMinLikeCount();
				} else {
					clickCount = interactChannelLevel.getUnSuperMinClickCount();
					likedCount = interactChannelLevel.getUnSuperMinLikeCount();
				}
			}
		}
		
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		resultMap.put("clickCount", clickCount);
		resultMap.put("commentCount", commentCount);
		resultMap.put("likedCount", likedCount);
		
		return resultMap;
	}
	
}
