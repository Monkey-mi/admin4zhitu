package com.imzhitu.admin.ztworld.service.impl;

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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.ZTWorldSubtitle;
import com.imzhitu.admin.common.service.KeyGenService;
import com.imzhitu.admin.ztworld.dao.SubtitleCacheDao;
import com.imzhitu.admin.ztworld.mapper.SubtitleMapper;
import com.imzhitu.admin.ztworld.service.SubtitleService;

@Service
public class SubtitleServiceImpl extends BaseServiceImpl implements
		SubtitleService {

	private static Logger log = Logger.getLogger(SubtitleServiceImpl.class);
	
	@Autowired
	private SubtitleCacheDao subCacheDao;
	
	@Autowired
	private SubtitleMapper subMapper;
	
	@Autowired
	private KeyGenService keygenService;
	
	private Integer subtitleCacheLimit = 30;

	@Override
	public void updateSubtitleCache() throws Exception {
		subCacheDao.update(subtitleCacheLimit);
	}

	@Override
	public void saveSubtitleByFile(File file, String transTo) throws Exception {
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
			int i = 0;
			String title = null;
			String titleEn = null;
			while((line = reader.readLine()) != null) {
				String content = line.trim();
				if(!"".equals(content)) {
					log.debug(content);
					if(i == 0) {
						title = content;
						++i;
						continue;
					}
					if(i == 1) {
						titleEn = content;
						ZTWorldSubtitle t = new ZTWorldSubtitle();
						Integer serial = keygenService.generateId(Admin.KEYGEN_WORLD_SUBTITLE_ID);
						t.setSubtitle(title);
						t.setSubtitleEn(titleEn);
						t.setSerial(serial);
						subMapper.saveSubtitle(t);
						--i;
					}
				}
			}
		} finally {
			reader.close();
		}
	}

	@Override
	public void buildTitles(final ZTWorldSubtitle title, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		buildNumberDtos(OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID, 
				"getSerial", title, start, limit, jsonMap, new NumberDtoListAdapter<ZTWorldSubtitle>(){

					@Override
					public List<? extends Serializable> queryList(
							ZTWorldSubtitle dto) {
						List<ZTWorldSubtitle> list = subMapper.queryTitles(title);
						return list;
					}

					@Override
					public long queryTotal(ZTWorldSubtitle dto) {
						return subMapper.queryTotal(dto);
					}
		});
		
	}
	
	@Override
	public void saveTitle(ZTWorldSubtitle title) throws Exception {
		Integer serial = keygenService.generateId(Admin.KEYGEN_WORLD_SUBTITLE_ID);
		title.setSerial(serial);
		subMapper.saveSubtitle(title);
	}

	@Override
	public ZTWorldSubtitle queryTitleById(Integer id) throws Exception {
		return subMapper.queryById(id);
	}

	@Override
	public void deleteTitleByIds(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		subMapper.deleteByIds(ids);
	}

	@Override
	public void updateTitle(ZTWorldSubtitle title) throws Exception {
		subMapper.update(title);
	}

	
}
