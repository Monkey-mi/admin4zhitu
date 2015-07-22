package com.imzhitu.admin.interact.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.InteractChannelLevel;
import com.imzhitu.admin.common.pojo.InteractChannelWorldLabel;
import com.imzhitu.admin.common.pojo.InteractCommentLabelChannel;
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.interact.mapper.InteractChannelWorldLabelMapper;
import com.imzhitu.admin.interact.service.InteractChannelLevelService;
import com.imzhitu.admin.interact.service.InteractChannelWorldLabelService;
import com.imzhitu.admin.interact.service.InteractCommentLabelChannelService;
import com.imzhitu.admin.interact.service.InteractCommentService;
import com.imzhitu.admin.interact.service.InteractWorldService;
import com.imzhitu.admin.op.mapper.ChannelWorldMapper;

@Service
public class InteractChannelWorldLabelServiceImpl extends BaseServiceImpl implements InteractChannelWorldLabelService{

	@Autowired
	private InteractChannelWorldLabelMapper channelWorldLabelMapper;
	
	@Autowired
	private ChannelWorldMapper channelWorldMapper;
	
	@Autowired
	private InteractWorldService worldService;
	
	@Autowired
	private InteractCommentService commentService;
	
	@Autowired
	private InteractChannelLevelService channelLevelService;
	
	@Autowired
	private InteractCommentLabelChannelService commentLabelChannelService;
	
	@Override
	public void insertChannelWorldLabel(Integer channelId, Integer worldId,
			String label_ids, Integer operator) throws Exception {
		// TODO Auto-generated method stub
		if(null == label_ids || "".equals(label_ids.trim())){
			throw new Exception("标签不能为空");
		}
		
		InteractChannelWorldLabel dto = new InteractChannelWorldLabel();
		dto.setChannelId(channelId);
		dto.setWorldId(worldId);
		dto.setLabel_ids(label_ids);
		dto.setAddDate(new Date());
		dto.setOperator(operator);
		
		List<InteractChannelLevel> channelLevelList = null;
		long total = channelWorldLabelMapper.queryChannelWorldLabelTotalCount(dto);
		
		if( total > 0){
			throw new Exception("已经添加过了");
		}else{
			//查询 频道等级
			channelLevelList = channelLevelService.queryChannelLevel(channelId, null);
			if(null == channelLevelList || channelLevelList.size() != 1){
				throw new Exception("channelId="+channelId+".worldId="+worldId+".channellevel is null or size != 1 which except 1.");
			}
			InteractChannelLevel channelLevel = channelLevelList.get(0);
			
			//查询是否为精选
			OpChannelWorld channelWorld = new OpChannelWorld();
			channelWorld.setChannelId(channelId);
			channelWorld.setWorldId(worldId);
			channelWorld.setSuperb(Tag.TRUE);
			long isSuperFlag = channelWorldMapper.queryChannelWorldCount(channelWorld);
			
			//查询 评论ids //点赞和粉丝数
			StringBuilder sb = new StringBuilder();
			int totalCommentCount = 0;
			int totalLikeCount = 0;
			int totalFollowCount = 0;
			int totalClickCount = 0;
			Integer [] labelIds = StringUtil.convertStringToIds(label_ids);
			long rNum = Math.round(Math.random()*1234);
			if(isSuperFlag == 0){
				totalCommentCount = (int)(channelLevel.getUnSuperMinCommentCount() + rNum % (channelLevel.getUnSuperMaxCommentCount() - channelLevel.getUnSuperMinCommentCount()));
				totalClickCount = (int)(channelLevel.getUnSuperMinClickCount() + rNum % (channelLevel.getUnSuperMaxClickCount() - channelLevel.getUnSuperMinClickCount()));
				totalFollowCount = (int)(channelLevel.getUnSuperMinFollowCount() + rNum % (channelLevel.getUnSuperMaxFollowCount() - channelLevel.getUnSuperMinFollowCount()));
				totalLikeCount = (int)(channelLevel.getUnSuperMinLikeCount() + rNum % (channelLevel.getUnSuperMaxLikeCount() - channelLevel.getUnSuperMinLikeCount()));
			}else{
				totalCommentCount = (int)(channelLevel.getSuperMinCommentCount() + rNum % ( channelLevel.getSuperMaxCommentCount() - channelLevel.getSuperMinCommentCount()));
				totalClickCount = (int)(channelLevel.getSuperMinClickCount() + rNum % ( channelLevel.getSuperMaxClickCount() - channelLevel.getSuperMinClickCount()));
				totalFollowCount = (int)(channelLevel.getSuperMinFollowCount() + rNum % ( channelLevel.getSuperMaxFollowCount() - channelLevel.getSuperMinFollowCount()));
				totalLikeCount = (int)(channelLevel.getSuperMinLikeCount() + rNum % ( channelLevel.getSuperMaxLikeCount() - channelLevel.getSuperMinLikeCount()));
			}
			Integer avetege = (int)(totalCommentCount / labelIds.length);
			avetege = avetege > 0 ? avetege : 1;
			
			for( int i=0; i<labelIds.length; i++){
				if(totalCommentCount >= avetege){
					totalCommentCount -= avetege;
					List<Integer> commentList = commentService.getRandomCommentIds(labelIds[i], avetege);
					if(sb.length() > 0){
						sb.append(',');
					}
					sb.append(commentList.toString());
				}else if( totalCommentCount > 0){
					List<Integer> commentList = commentService.getRandomCommentIds(labelIds[i], (int)totalCommentCount);
					if(sb.length() > 0){
						sb.append(',');
					}
					sb.append(commentList.toString());
					break;
				}else{
					break;
				}
			}
			
			
			
			//评论
			try{
				String commentStr = sb.toString();
				String[] commentIds = commentStr.substring(1, commentStr.length()-1).split(",");
				worldService.saveUserInteractByWorldId(worldId, totalFollowCount, 400);
				worldService.saveChannelInteractV3(channelId, worldId, totalClickCount, totalLikeCount, commentIds, channelLevel.getMinuteTime());
				channelWorldLabelMapper.insertChannelWorldLabel(dto);
			}catch(Exception e){
				throw new Exception("commentIds:"+sb.toString()+"\n"+e.getMessage());
			}
		}
	}

	@Override
	public List<InteractChannelWorldLabel> queryChannelWorldLabel(Integer id,
			Integer worldId, Integer channelId) throws Exception {
		// TODO Auto-generated method stub
		InteractChannelWorldLabel dto = new InteractChannelWorldLabel();
		dto.setId(id);
		dto.setChannelId(channelId);
		dto.setWorldId(worldId);
		return channelWorldLabelMapper.queryChannelWorldLabel(dto);
	}

	@Override
	public long queryChannelWorldLabelTotalCount(Integer id, Integer worldId,
			Integer channelId) throws Exception {
		// TODO Auto-generated method stub
		InteractChannelWorldLabel dto = new InteractChannelWorldLabel();
		dto.setId(id);
		dto.setChannelId(channelId);
		dto.setWorldId(worldId);
		return channelWorldLabelMapper.queryChannelWorldLabelTotalCount(dto);
	}

	@Override
	public void queryChannelWorldLabel(Integer worldId, Integer channelId,
			Map<String, Object> jsonMap) throws Exception {
		// TODO Auto-generated method stub
		if(null == worldId || channelId == null){
			throw new Exception("worldId and channelId cannot be null");
		}
		
		List<InteractChannelWorldLabel> list = null;
		try{
			list = queryChannelWorldLabel(null,worldId,channelId);
			if(list != null && list.size()== 1){
				List<InteractCommentLabelChannel>channelLabelList =  commentLabelChannelService.queryCommentLabelNameByLabelIds(list.get(0).getLabel_ids());
				jsonMap.put(OptResult.JSON_KEY_LABEL_INFO, channelLabelList);
				jsonMap.put(OptResult.JSON_KEY_INTERACT, Tag.TRUE);
			}
		}catch(Exception e){
			list = null;
		}
		
		if(list == null || list.size() == 0){
			List<InteractCommentLabelChannel>channelLabelList =  commentLabelChannelService.queryCommentLabelByChannelId(channelId);
			jsonMap.put(OptResult.JSON_KEY_LABEL_INFO, channelLabelList);
			jsonMap.put(OptResult.JSON_KEY_INTERACT, Tag.FALSE);
		}
	}

}
