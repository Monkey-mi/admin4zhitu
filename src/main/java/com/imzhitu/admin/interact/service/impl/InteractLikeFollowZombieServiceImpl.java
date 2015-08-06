package com.imzhitu.admin.interact.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.InteractLikeFollowZombie;
import com.imzhitu.admin.interact.mapper.InteractLikeFollowZombieMapper;
import com.imzhitu.admin.interact.service.InteractLikeFollowZombieService;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

public class InteractLikeFollowZombieServiceImpl extends BaseServiceImpl implements InteractLikeFollowZombieService{

	@Autowired
	private InteractLikeFollowZombieMapper likeFollowZobmieMapper;
	
	private Logger logger = Logger.getLogger(InteractLikeFollowZombieServiceImpl.class);
	
	@Override
	public void batchInsertLikeFollowZombie(File file) throws Exception {
		// TODO Auto-generated method stub
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
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
			String line = null;
			while((line = reader.readLine()) != null) {
				try{
					String userIdStr = line.trim();
					if( !"".equals(userIdStr)){
						Integer userId = Integer.parseInt(userIdStr);
						InteractLikeFollowZombie dto = new InteractLikeFollowZombie();
						dto.setZombieId(userId);
						likeFollowZobmieMapper.insertLikeFollowZombie(dto);
					}
				}catch(Exception e){
					logger.warn("batchInsertLikeFollowZombie error. line:"+line+"\ncause:"+e.getMessage());
				}
			}
		}finally {
			reader.close();
		}
	
	}

	@Override
	public void batchDeleteLikeFollowZombie(String idsStr) throws Exception {
		// TODO Auto-generated method stub
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		likeFollowZobmieMapper.batchDeleteLikeFollowZombie(ids);
	}

	@Override
	public List<Integer> queryNRandomNotCommentNotFollowZombieId(Integer userId, Integer worldId) throws Exception {
		// TODO Auto-generated method stub
		return likeFollowZobmieMapper.queryNRandomNotCommentNotFollowZombieId(userId, worldId);
	}

	@Override
	public void queryLikeFollowZombie(Integer id, Integer zombieId, Integer maxId, int page, int rows,
			Map<String, Object> jsonMap) throws Exception {
		// TODO Auto-generated method stub
		InteractLikeFollowZombie dto = new InteractLikeFollowZombie();
		dto.setFirstRow(rows*(page-1));
		dto.setLimit(rows);
		dto.setMaxId(maxId);
		dto.setZombieId(zombieId);
		dto.setId(id);
		
		List<InteractLikeFollowZombie> list 	= null;
		Integer reMaxId	= 0;
		long total = likeFollowZobmieMapper.queryLikeFollowZombieTotalCount(dto);
		
		
		if ( total > 0 ) {
			list = likeFollowZobmieMapper.queryLikeFollowZombie(dto);
			if ( list != null){
				reMaxId = list.get(0).getId();
			}
		}
		
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID, reMaxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
	}

}
