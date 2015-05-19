package com.imzhitu.admin.op.service.impl;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.OpSysDanmu;
import com.imzhitu.admin.common.pojo.ZTWorldSubtitle;
import com.imzhitu.admin.common.service.KeyGenService;
import com.imzhitu.admin.op.mapper.SysDanmuMapper;
import com.imzhitu.admin.op.service.DanmuService;
import com.imzhitu.admin.op.service.OpUserService;

/**
 * <p>
 * 弹幕业务逻辑访问接口
 * </p>
 * 
 * 创建时间: 2015-05-18
 * @author lynch
 *
 */
@Service
public class DanmuServiceImpl extends BaseServiceImpl implements DanmuService {

	@Autowired
	private SysDanmuMapper sysDanmuMapper;
	
	@Autowired
	private KeyGenService keygenService;

	@Autowired
	private OpUserService opUserService;
	
	@Override
	public void buildSysDanmu(OpSysDanmu sysDanmu, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		buildNumberDtos(OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID, 
				"getSerial", sysDanmu, start, limit, jsonMap, new NumberDtoListAdapter<OpSysDanmu>(){

					@Override
					public List<? extends Serializable> queryList(
							OpSysDanmu dto) {
						List<OpSysDanmu> list = sysDanmuMapper.querySysDanmu(dto);
						return list;
					}

					@Override
					public long queryTotal(OpSysDanmu dto) {
						return sysDanmuMapper.queryTotal(dto);
					}
		});
	}

	@Override
	public void saveSysDanmu(Integer channelId, File file) throws Exception{
		if(file == null) {
			throw new NullPointerException("please select danmu file.");
		}
		if(channelId == null) {
			throw new NullPointerException("please select channel id.");
		}
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
				List<Integer> zids = opUserService.getRandomUserZombieIds(2); // 获取2个，取第1个
				String content = line.trim();
				OpSysDanmu dm = new OpSysDanmu();
				dm.setChannelId(channelId);
				dm.setContent(content);
				dm.setAuthorId(zids.get(0));
				
				saveSysDanmu(dm);
			}
		} finally {
			reader.close();
		}
	}

	@Override
	public void saveSysDanmu(OpSysDanmu sysDanmu) {
		Integer serial = keygenService.generateId(Admin.KEYGEN_OP_SYS_DANMU_ID);
		sysDanmu.setSerial(serial);
		if(sysDanmu.getAuthorId() == null) {
			List<Integer> zids = opUserService.getRandomUserZombieIds(2); // 获取2个，取第1个
			sysDanmu.setAuthorId(zids.get(0));
		}
		sysDanmuMapper.save(sysDanmu);
	}

	@Override
	public void deleteSysDanmu(String idsStr) {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		sysDanmuMapper.deleteByIds(ids);
	}

	@Override
	public void updateSysDanum(OpSysDanmu sysDanmu) {
		sysDanmuMapper.update(sysDanmu);
	}

	@Override
	public OpSysDanmu querySysDanmuById(Integer id) {
		return sysDanmuMapper.queryById(id);
	}

	@Override
	public void updateSysDanmuSerial(String[] idStrs) {
		for(int i = idStrs.length - 1; i >= 0; i--) {
			if(StringUtil.checkIsNULL(idStrs[i]))
				continue;
			int id = Integer.parseInt(idStrs[i]);
			Integer serial = keygenService.generateId(Admin.KEYGEN_OP_SYS_DANMU_ID);
			sysDanmuMapper.updateSerialById(id, serial);
		}
	}

}
