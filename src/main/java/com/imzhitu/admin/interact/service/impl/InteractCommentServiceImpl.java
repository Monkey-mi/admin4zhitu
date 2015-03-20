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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.NumberUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.InteractComment;
import com.imzhitu.admin.common.pojo.InteractCommentLabel;
import com.imzhitu.admin.common.pojo.InteractCommentLabelTree;
import com.imzhitu.admin.common.service.KeyGenService;
import com.imzhitu.admin.interact.dao.InteractCommentDao;
import com.imzhitu.admin.interact.dao.InteractCommentLabelDao;
import com.imzhitu.admin.interact.dao.InteractWorldCommentDao;
import com.imzhitu.admin.interact.service.InteractCommentService;

@Service
public class InteractCommentServiceImpl extends BaseServiceImpl implements
		InteractCommentService {

	private Logger logger = Logger.getLogger(InteractCommentServiceImpl.class);
	
	@Autowired
	private KeyGenService keyGenService;
	
	@Autowired
	private InteractCommentDao interactCommentDao;
	
	@Autowired
	private InteractCommentLabelDao interactCommentLabelDao;
	
	@Autowired
	private InteractWorldCommentDao interactWorldCommentDao;
	
	
	@Override
	public void batchSaveComment(File file, Integer labelId) throws Exception {
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
				String content = line.trim();
				Integer id = keyGenService.generateId(Admin.INTERACT_COMMENT_KEYID);
				if(!"".equals(content)) {
					interactCommentDao.saveComment(new InteractComment(id,content, labelId));
				}
			}
		} finally {
			reader.close();
		}
	}
	
	@Override
	public void saveComment(File file, String content, Integer labelId)
			throws Exception {
		if(file != null && file.exists()) {
			batchSaveComment(file, labelId);
		} else {
			Integer id = keyGenService.generateId(Admin.INTERACT_COMMENT_KEYID);
			interactCommentDao.saveComment(new InteractComment(id,content, labelId));
		}
	}

	@Override
	public void buildComments(final int labelId, final String comment, int maxId, int start,
			int limit, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<InteractComment>() {

			@Override
			public List<InteractComment> getSerializables(
					RowSelection rowSelection) {
				return interactCommentDao.queryComment(labelId, comment, rowSelection);
			}

			@Override
			public List<InteractComment> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return interactCommentDao.queryComment(labelId, comment, maxId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return interactCommentDao.queryCommentTotal(labelId, comment, maxId);
			}

		}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
	}

	@Override
	public List<Integer> getRandomCommentIds(int labelId, int size) throws Exception {
		Set<Integer> usedIndex = new HashSet<Integer>();
		Long total = interactCommentDao.queryCommentTotal(labelId);
		if(total < size) {
			throw new IndexOutOfBoundsException("评论数量不足，只有" + total + "条");
		}
		List<Integer> ids = new ArrayList<Integer>();
		for(int i = 0; i < size; i++) {
			int index = NumberUtil.getRandomIndex(total.intValue(), usedIndex);
			usedIndex.add(index);
			Integer id = interactCommentDao.queryIdByPageIndex(labelId, index);
			ids.add(id);
		}
		return ids;
	}
	
	@Override
	public void deleteCommentByIds(String idsStr) {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		interactCommentDao.deleteByIds(ids);
	}

	@Override
	public InteractComment getCommentById(Integer id) throws Exception {
		return interactCommentDao.queryCommentById(id);
	}

	@Override
	public void updateComment(Integer id, String content, Integer labelId)
			throws Exception {
		interactCommentDao.updateComment(id, content, labelId);
		
	}
	
	@Override
	public void saveLabel(String labelName, Integer groupId) throws Exception {
		boolean b = interactCommentLabelDao.checkLabelExsistByLabelName(labelName);
		if(b == false)
		interactCommentLabelDao.saveLabel(new InteractCommentLabel(labelName, groupId));
	}
	
	@Override
	public List<InteractCommentLabel> getAllLabels() throws Exception {
		return interactCommentLabelDao.queryLabel();
	}

	@Override
	public void buildLabel(Integer maxId, int start, int limit,
			final Integer groupId, Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<InteractCommentLabel>() {

			@Override
			public List<InteractCommentLabel> getSerializables(
					RowSelection rowSelection) {
				return interactCommentLabelDao.queryLabel(groupId, rowSelection);
			}

			@Override
			public List<InteractCommentLabel> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return interactCommentLabelDao.queryLabel(maxId, groupId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return interactCommentLabelDao.queryLabelCount(maxId,groupId);
			}

		}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
	}
	
	
	@Override
	public List<InteractCommentLabel> getAllLabelGroup() throws Exception {
		return interactCommentLabelDao.queryLabelGroup();
	}
	
	@Override
	public void buildLabelGroup(Integer maxId, int start, int limit,
			Map<String, Object> jsonMap) throws Exception {
		buildSerializables(maxId, start, limit, jsonMap, new SerializableListAdapter<InteractCommentLabel>() {

			@Override
			public List<InteractCommentLabel> getSerializables(
					RowSelection rowSelection) {
				return interactCommentLabelDao.queryLabelGroup(rowSelection);
			}

			@Override
			public List<InteractCommentLabel> getSerializableByMaxId(int maxId,
					RowSelection rowSelection) {
				return interactCommentLabelDao.queryLabelGroup(maxId, rowSelection);
			}

			@Override
			public long getTotalByMaxId(int maxId) {
				return interactCommentLabelDao.queryLabelGroupCount(maxId);
			}

		}, OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
	}

	@Override
	public void updateLabelByJSON(String labelJSON) throws Exception {
		JSONArray jsArray = JSONArray.fromObject(labelJSON);
		for(int i = 0; i < jsArray.size(); i++) {
			JSONObject jsObj = jsArray.getJSONObject(i);
			int id = jsObj.optInt("id");
			String labelName = jsObj.getString("labelName");
			int groupId = jsObj.getInt("groupId");
			interactCommentLabelDao.updateLabel(id, labelName, groupId);
		}
	}

	@Override
	public void deleteLabelGroups(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		for(Integer id : ids) {
			interactCommentLabelDao.deleteByGroupId(id);
		}
		interactCommentLabelDao.deleteByIds(Admin.INTERACT_COMMENT_LABEL, ids);
	}

	@Override
	public void deleteLabels(String idsStr) throws Exception {
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		interactCommentLabelDao.deleteByIds(Admin.INTERACT_COMMENT_LABEL, ids);
	}
	
	@Override
	public List<InteractCommentLabelTree> getLabelTree(Integer groupId, Integer selected, Boolean hasTotal) throws Exception {
		List<InteractCommentLabelTree> treeList = null;
		if(groupId == null || groupId.equals(0)) {
			treeList = interactCommentLabelDao.queryLabelGroupTree(selected);
			if(hasTotal) 
				treeList.add(0,new InteractCommentLabelTree(0, "全部标签", "opend", true));
		} else 
			treeList = interactCommentLabelDao.queryLabelTree(groupId,selected);
		return treeList;
	}

	@Override
	public List<InteractCommentLabelTree> getAllLabelTree()throws Exception {
		List<InteractCommentLabelTree> treeList = null;
		treeList = interactCommentLabelDao.queryLabelGroupTree(null);
		for(InteractCommentLabelTree t:treeList){
			List<InteractCommentLabelTree> childList = interactCommentLabelDao.queryLabelTree(t.getId(),null);
			if(!childList.isEmpty()){
				t.setChildren(childList);
			}
		}
		return treeList;
	}
	
	@Override
	public void updateCommentContentById(String content,Integer id)throws Exception{
		interactCommentDao.updateCommentContentById(content, id);
	}
	
	
	@Override
	public void updateCommentContentByJSON(String jsString)throws Exception{
		JSONArray jsArray = JSONArray.fromObject(jsString);
		for(int i=0;i<jsArray.size();i++){
			JSONObject jsObj = jsArray.getJSONObject(i);
			int commentId = jsObj.optInt("commentId");
			String content = jsObj.getString("content");
			Integer preId = jsObj.optInt("id");
			InteractComment ic = interactCommentDao.queryCommentById(commentId);
			Integer id = keyGenService.generateId(Admin.INTERACT_COMMENT_KEYID);
			interactCommentDao.saveComment(new InteractComment(id,content,ic.getLabelId()));
			interactWorldCommentDao.updateCommentIdById(preId, id);
		}
	}

}
