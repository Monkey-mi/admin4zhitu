package com.imzhitu.admin.op.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.OpZombieChannel;
import com.imzhitu.admin.op.mapper.OpZombieChannelMapper;
import com.imzhitu.admin.op.service.OpZombieChannelService;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

@Service
public class OpZombieChannelServiceImpl extends BaseServiceImpl implements OpZombieChannelService{
	
	@Autowired
	private OpZombieChannelMapper zombieChannelMapper;
	
	private Logger log = Logger.getLogger(OpZombieChannelServiceImpl.class);

	@Override
	public void insertZombieChannel(Integer userId, Integer channelId)
			throws Exception {
		OpZombieChannel dto = new OpZombieChannel();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		zombieChannelMapper.insertZombieChannel(dto);
	}

	@Override
	public void batchDeleteZombieChannel(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		zombieChannelMapper.batchDeleteZombieChannel(ids);
	}

	@Override
	public void queryZombieChannel(Integer id, Integer userId,
			Integer channelId, Integer maxId, int page, int rows,
			Map<String, Object> jsonMap) throws Exception {
		OpZombieChannel dto = new OpZombieChannel();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		dto.setId(id);
		dto.setMaxId(maxId);
		dto.setFirstRow(rows*(page-1));
		dto.setLimit(rows);
		long total 			= 0;
		Integer reMaxId 	= 0;
		List<OpZombieChannel> list = null;
		
		total = zombieChannelMapper.queryZombieChannelTotalCount(dto);
		if(total > 0){
			list = zombieChannelMapper.queryZombieChannel(dto);
			if(list  != null && list.size() > 0 ){
				reMaxId = list.get(0).getId();
			}
		}
		
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
		jsonMap.put(OptResult.JSON_KEY_MAX_ID,reMaxId);
		jsonMap.put(OptResult.JSON_KEY_ROWS, list);
	}

	@Override
	public long queryZombieChannelTotalCount(Integer id, Integer userId,
			Integer channelId, Integer maxId) throws Exception {
		OpZombieChannel dto = new OpZombieChannel();
		dto.setChannelId(channelId);
		dto.setUserId(userId);
		dto.setId(id);
		dto.setMaxId(maxId);
		return zombieChannelMapper.queryZombieChannelTotalCount(dto);
	}

	@Override
	public List<Integer> queryNotInteractNRandomNotFollowZombie(Integer userId,
			Integer channelId, Integer worldId, Integer limit) throws Exception {
		return zombieChannelMapper.queryNotInteractNRandomNotFollowZombie(userId, channelId, worldId, limit);
	}

	@Override
	public void batchInsertZombieChannel(File file, Integer channelId) throws Exception {
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
						insertZombieChannel(userId,channelId);
					}
				}catch(Exception e){
					log.warn("batchInsertZombieChannel error. line:"+line+".channelId:"+channelId+"\ncause:"+e.getMessage());
				}
			}
		}finally {
			reader.close();
		}
	}

}
