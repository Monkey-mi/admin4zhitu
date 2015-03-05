package com.imzhitu.admin.interact.service.impl;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.KeyGenService;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.Log;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractComment;
import com.imzhitu.admin.common.pojo.InteractPlanComment;
import com.imzhitu.admin.interact.dao.InteractCommentDao;
import com.imzhitu.admin.interact.dao.InteractPlanCommentDao;
import com.imzhitu.admin.interact.mapper.InteractPlanCommentMapper;
import com.imzhitu.admin.interact.service.InteractPlanCommentService;

@Service
public class InteractPlanCommentServiceImpl extends BaseServiceImpl implements InteractPlanCommentService{

	@Autowired
	private InteractPlanCommentMapper interactPlanCommentMapper;
	@Autowired
	private InteractPlanCommentDao interactPlanCommentDao;

	
	@Autowired
	private InteractCommentDao interactCommentDao;
	
	@Autowired
	private KeyGenService keyGenService;



	@Override
	public void  updatePlanCommentValidById(Integer id,Integer valid,Integer operatorId)throws Exception{
		
	}
	
	
	@Override
	public void updatePlanCommentByRowJson(String rowJson,Integer operatorId)throws Exception{
		Date now = new Date();
		JSONArray jsArray = JSONArray.fromObject(rowJson);
		for(int i=0 ; i < jsArray.size() ; i++){
			InteractPlanComment dto = new InteractPlanComment();
			JSONObject jsObj = jsArray.getJSONObject(i);
			dto.setId(jsObj.getInt("id"));
			dto.setGroupId(jsObj.getInt("groupId"));
			dto.setContent(jsObj.getString("content"));
			dto.setValid(jsObj.getInt("valid"));
			dto.setInteractCommentId(jsObj.getInt("interactCommentId"));
			dto.setModifyDate(now);
			interactCommentDao.updateComment(jsObj.getInt("interactCommentId"),jsObj.getString("content"),0);//跟新interact_comment表
			interactPlanCommentMapper.updateCommentContentById(dto);//跟新interact_plan_comment表
		}
	}
	
	@Override
	public void addPlanComment(File file,Integer groupId,String content,Integer valid,Integer operatorId)throws Exception{
		if(null == groupId)throw new  Exception("标签组id 不能为空");
		if(file != null && file.exists()) {
			batchAddPlanComment(file,groupId,operatorId,valid);
		}else{
			Date now = new Date();
			Integer interactCommentId = keyGenService.generateId(Admin.INTERACT_COMMENT_KEYID);
			if(!"".equals(content)) {
				
				InteractPlanComment dto = new InteractPlanComment();
				dto.setGroupId(groupId);
				dto.setContent(content);
				dto.setAddDate(now);
				dto.setModifyDate(now);
				dto.setOperatorId(operatorId);
				dto.setValid(valid);
				dto.setInteractCommentId(interactCommentId);
				interactCommentDao.saveComment(new InteractComment(interactCommentId,content, 0));//先忘interact_comment表中添加数据
				interactPlanCommentMapper.addPlanComment(dto);//再往interact_plan_comment表中添加数据
			}
		}
		
	}
	
	public void batchAddPlanComment(File file,Integer groupId,Integer operatorId,Integer valid)throws Exception{
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false)); 
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance()); 
		detector.add(UnicodeDetector.getInstance()); 
		java.nio.charset.Charset set = null;
		set = detector.detectCodepage(file.toURI().toURL());
		String charsetName = set.name();
		
		// 除了GB开头的编码，其他一律用UTF-8
		String charset = charsetName != null && charsetName.startsWith("GB") ? charsetName : "UTF-8";
		BufferedReader reader = null;
		Date now = new Date();
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
			String line = null;
			while((line = reader.readLine()) != null) {
				String content = line.trim();
				if(!"".equals(content)) {
					Integer interactCommentId = keyGenService.generateId(Admin.INTERACT_COMMENT_KEYID);
					InteractPlanComment dto = new InteractPlanComment();
					dto.setGroupId(groupId);
					dto.setContent(content);
					dto.setAddDate(now);
					dto.setModifyDate(now);
					dto.setOperatorId(operatorId);
					dto.setValid(valid);
					dto.setInteractCommentId(interactCommentId);
					interactCommentDao.saveComment(new InteractComment(interactCommentId,content, 0));//先忘interact_comment表中添加数据
					interactPlanCommentMapper.addPlanComment(dto);//再往interact_plan_comment表中添加数据
				}
			}
		}finally {
			reader.close();
		}
	}
	
	@Override
	public void delPlanCommentByIds(String idsStr,Integer operatorId)throws Exception{
		if(null == idsStr || idsStr.trim().equals(""))return;
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		Date now = new Date();
		for(Integer id:ids){
			try{
				InteractPlanComment dto = new InteractPlanComment();
				dto.setId(id);
				dto.setValid(Tag.FALSE);
				dto.setOperatorId(operatorId);
				dto.setModifyDate(now);
				interactPlanCommentMapper.updateCommentContentValid(dto);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	
	@Override
	/**
	 * 查询计划评论标签
	 * @param maxId
	 * @param page
	 * @param rows
	 * @param jsonMap
	 * @throws Exception
	 */
	public void queryPlanCommentForTable(Integer  maxId,Integer groupId, String content,Integer page, Integer rows,Map<String , Object> jsonMap)throws Exception{
		InteractPlanComment dto = new InteractPlanComment();
		if(groupId != null )
			dto.setGroupId(groupId);
		if(content != null)
			dto.setContent(content);
		buildNumberDtos(dto,page,rows,jsonMap,new NumberDtoListAdapter<InteractPlanComment>(){
			@Override
			public long queryTotal(InteractPlanComment d){
				return interactPlanCommentMapper.queryInteractPlanCommentTotalCount(d);
			}
			@Override
			public List< ? extends AbstractNumberDto> queryList(InteractPlanComment d){
				return interactPlanCommentMapper.queryInteractPlanComment(d);
			}
		});
	}
	
	
	public List<InteractPlanComment> queryNRandomPlanCommentByGroupId(Integer n,Integer groupId)throws Exception{
		long count = interactPlanCommentMapper.queryInteractPlanCommentCountByGroupId(groupId);
		if(n<1 )throw new Exception("参数出错，随机参数不能小于1");
		if(count < n)throw new Exception("计划评论数不足");
		int c = (int)(count/n);
		int beginNum = (int)(Math.random()*1000007)%(c);
		Integer maxId = 0;
		List <InteractPlanComment> list = new ArrayList<InteractPlanComment>(n);
		for(int i=0;i<n;i++){
			Integer start = beginNum+i*c+1;
			List <InteractPlanComment> tmplist = null;
			InteractPlanComment dto = new InteractPlanComment();
			if(maxId!=0){
				dto.setMaxId(maxId);
			}
			dto.setGroupId(groupId);
			dto.setLimit(1);
			dto.setFirstRow(start);
			try{
				tmplist = interactPlanCommentMapper.queryInteractPlanComment(dto);
			}catch(EmptyResultDataAccessException e){
				Log.info(e.getMessage());
				tmplist = null;
			}
			if(tmplist.size()>0){
				InteractPlanComment tmp = tmplist.get(0);
				list.add(tmp);
				maxId = tmp.getId();
			}
		}
		return list;
		
	}

}
