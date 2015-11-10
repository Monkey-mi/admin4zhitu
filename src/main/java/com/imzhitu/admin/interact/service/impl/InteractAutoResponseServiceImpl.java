package com.imzhitu.admin.interact.service.impl;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.pojo.HTWorldComment;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.service.impl.KeyGenServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.InteractAutoResponseDto;
import com.imzhitu.admin.constant.Emoji;
import com.imzhitu.admin.interact.mapper.InteractAutoResponseMapper;
import com.imzhitu.admin.interact.service.InteractAutoResponseSchedulaService;
import com.imzhitu.admin.interact.service.InteractAutoResponseService;
import com.imzhitu.admin.interact.service.InteractParseResponseService;
import com.imzhitu.admin.interact.service.InteractRobotService;

//@Service
public class InteractAutoResponseServiceImpl extends BaseServiceImpl implements InteractAutoResponseService{
	
	private Logger logger = Logger.getLogger(InteractAutoResponseServiceImpl.class);
	
	private static final Integer SCAN_SPAN_MS = 90*60*1000;//90分钟 
	
	@Autowired
//	private InteractAutoResponseDao interactAutoResponseDao;
	private InteractAutoResponseMapper autoResponseMapper;
	
	@Autowired
	private InteractParseResponseService interactParseResponseService;
	
	@Autowired
	private InteractRobotService interactRobotService;
	
	@Autowired
	private com.hts.web.common.service.KeyGenService webKeyGenService;
	
	@Autowired
	private com.hts.web.ztworld.dao. HTWorldCommentDao webWorldCommentDao;
	
	@Autowired
	private com.hts.web.ztworld.service.ZTWorldInteractService ztWorldInteractService;
	
	
	
	@Autowired
	private InteractAutoResponseSchedulaService interactAutoResponseSchedulaService;
	
	@Override
	public void queryUncompleteResponse(Integer maxId, int start, int limit, Integer userLevelId, Integer channelId,
			 Map<String, Object> jsonMap) throws Exception{
		InteractAutoResponseDto dto = new InteractAutoResponseDto();
		dto.setMaxId(maxId);
		dto.setUserLevelId(userLevelId);
		dto.setChannelId(channelId);
		buildNumberDtos(dto,start,limit,jsonMap,new NumberDtoListAdapter<InteractAutoResponseDto>(){
			@Override
			public List< ? extends AbstractNumberDto> queryList(InteractAutoResponseDto dto){
				List<InteractAutoResponseDto> list = autoResponseMapper.queryUncompleteResponse(dto);
				//上上次评论
				for(InteractAutoResponseDto o:list){
					if(o.getPreReId() != 0){
						String preComment = autoResponseMapper.queryPreComment(o.getPreReId());
						o.setPreComment(preComment);
					}
				}
				
				return list;
			}
			
			@Override 
			public long queryTotal(InteractAutoResponseDto dto){
				return autoResponseMapper.getUnCompleteResponseCountByMaxId(dto);
			}
		});
	}
	

	/**
	 * 页面上更新自动回复的完成状态，其实是将自动回复转成计划，按计划实施
	 */
	@Override
	public void updateResponseCompleteByIds(String idsStr,Integer operatorId)throws Exception{
		if(idsStr == null || idsStr.equals(""))
			return;
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		if(ids.length <= 0)
			return;

		for(int i=0; i<ids.length; i++){
			interactAutoResponseSchedulaService.addAutoResponseSchedula(ids[i], Tag.TRUE, Tag.FALSE, null, operatorId);
		}
		autoResponseMapper.updateResponseCompleteByIds(ids);
	}
	
	/**
	 * 计划完成后，新插入一条数据
	 * 
	 */
	@Override 
	public void updateResponseCompleteById(Integer id){
		InteractAutoResponseDto dto = autoResponseMapper.queryRespnse(id);
		if(dto != null){
			/*
			HTWorldComment comment = new HTWorldComment();
				
			Integer commentId = webKeyGenService.generateId(KeyGenServiceImpl.HTWORLD_COMMENT_ID);
			comment.setId(commentId);
			comment.setCk(Tag.FALSE);
			comment.setValid(Tag.TRUE);
			comment.setShield(Tag.FALSE);
			comment.setContent(dto.getContext());
			comment.setCommentDate(new Date());
		    comment.setAuthorId(dto.getAuthor());
		    comment.setReAuthorId(dto.getReAuthor());
		    comment.setReId(dto.getCommentId());
		    comment.setWorldId(dto.getWorldId());
		    comment.setWorldAuthorId(dto.getWorldAuthorId());
		    webWorldCommentDao.saveWorldComment(comment);
		    */
		    try{
		    	ztWorldInteractService.saveReply4Admin(dto.getAuthor(), dto.getContext(), dto.getWorldId(), dto.getCommentId());
		    }catch(Exception e){
		    	logger.warn(e.getMessage());
		    }

		}
		
	}
	
	public void scanResponseAndGetAnswer(){
		//查找马甲对应的被回复
		String answerStr;
		Date now = new Date();
		logger.info("InteractAutoResponseServiceImpl scanResponseAndGetAnswer begin============================>>>>>"+now);
		Date beginDate = new Date(now.getTime() - SCAN_SPAN_MS);
		InteractAutoResponseDto dto1 = new InteractAutoResponseDto();
		dto1.setCommentDate(beginDate);
		List<InteractAutoResponseDto> listDto = autoResponseMapper.queryUnCkResponse(dto1);
		if(listDto == null)return ;
		for(InteractAutoResponseDto dto:listDto){
			try{
				//分析被回复内容，过滤表情
				String qStr = interactParseResponseService.parserQString(dto.getContext());
				if(qStr == null)continue;
				//若是表情
				if(qStr.equals(Emoji.emojiTag)){
					answerStr = dto.getContext().replaceAll("\n", "").replaceAll(" *@.*:", "").trim();
					if(answerStr == null || answerStr.trim().equals(""))continue;
				}else{//若非表情
					//向机器人发送请求，获取回复
					answerStr = interactRobotService.getAnswer(qStr);
				}
				if(null == answerStr)answerStr = "哦";//若从机器人那里得到空值，则复制为哦
				String  asStr= "回复@" + dto.getAuthorName() + ": " + answerStr;//加上名字
//				//更新htworld_comment 中的ck位
//				webWorldCommentDao.updateCkById(Tag.TRUE, dto.getCommentId());;D
				//保存自动回复记录到interact_auto_response
				Integer reAuthorId = dto.getAuthor();
				dto.setAuthor(dto.getReAuthor());
				dto.setReAuthor(reAuthorId);
				dto.setComplete(Tag.FALSE);
				dto.setCommentId(dto.getCommentId());
				dto.setContext(asStr);
				autoResponseMapper.addResponse(dto);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		Date end = new Date();
		logger.info("InteractAutoResponseServiceImpl scanResponseAndGetAnswer end============================>>>>>"+end);
	}
	
	@Override
	public void queryResponseGroupById(Integer id,Map<String,Object>jsonMap)throws Exception{
		try{
			List<InteractAutoResponseDto> list = autoResponseMapper.queryResponseById(id);
			if(list != null && list.size() > 0){
				List<InteractAutoResponseDto> tmpList = autoResponseMapper.queryResponseGroupById(list.get(0));
				list.addAll(tmpList);
			}
			if(list == null)throw new Exception("数据为空");
			jsonMap.put(OptResult.TOTAL, list.size());
			jsonMap.put(OptResult.ROWS, list);
		}catch(Exception e){
			throw e;
		}
		
	}
	
	@Override
	public void delAutoResponseByIds(String idsStr)throws Exception{
		if(idsStr==null || idsStr.equals(""))return;
		try{
			Integer[] ids = StringUtil.convertStringToIds(idsStr);
			autoResponseMapper.delAutoResponseByIds(ids);
		}catch(Exception e){
			throw e;
		}
	}
	
	@Override
	public void updateCommentContentByRowJson(String rowJson)throws Exception{
		JSONArray jsArray = JSONArray.fromObject(rowJson);
		for(int i=0;i<jsArray.size();i++){
			JSONObject jsObj = jsArray.getJSONObject(i);
			String context = jsObj.optString("context");
			Integer id = jsObj.optInt("id");
			InteractAutoResponseDto dto = new InteractAutoResponseDto();
			dto.setContext(context);
			dto.setId(id);
			autoResponseMapper.updateResponseContext(dto);
		}
	}
	
	@Override
	public void teachTuLingRobot(String question,String answer,Map<String,Object>jsonMap)throws Exception{
		if(question == null || question.trim().equals("") || answer == null || answer.trim().equals(""))return ;
		String context = "问："+question+"答："+answer;
		String result = interactRobotService.getAnswerFromTuLing(URLEncoder.encode(context,"UTF-8"));
		if( result == null ){
			throw new Exception("教机器人失败");
		}
		jsonMap.clear();
		jsonMap.put("content", result);
	}
}
