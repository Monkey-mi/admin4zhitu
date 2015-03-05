package com.imzhitu.admin.interact.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.hts.web.base.database.RowSelection;
import com.hts.web.common.SerializableListAdapter;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.InteractCommentLabel;
import com.imzhitu.admin.common.pojo.InteractWorldLabelTreeDto;
import com.imzhitu.admin.common.pojo.WorldLabelCommentLabelDto;
import com.imzhitu.admin.interact.dao.InteractCommentLabelDao;
import com.imzhitu.admin.interact.dao.InteractWorldLabelCommentLabelDao;
import com.imzhitu.admin.interact.service.InteractWorldLabelCommentLabelService;

@Service
public class InteractWorldLabelCommentLabelServiceImpl extends BaseServiceImpl implements InteractWorldLabelCommentLabelService{
	
	@Autowired
	private InteractWorldLabelCommentLabelDao interactWorldLabelCommentLabelDao;
	
	@Autowired
	private InteractCommentLabelDao interactCommentLabelDao;
	
	@Override
	public void QueryULCLList(int maxId,int start,int limit,Map<String ,Object> jsonMap) throws Exception{
		buildSerializables( maxId, start, limit, jsonMap, new SerializableListAdapter<WorldLabelCommentLabelDto>(){
			@Override
			public List<WorldLabelCommentLabelDto> getSerializables(RowSelection rowSelection){
				return interactWorldLabelCommentLabelDao.QueryWorldLabelCommentLabel(rowSelection);
			}
			
			@Override
			public List<WorldLabelCommentLabelDto> getSerializableByMaxId(int maxId,RowSelection rowSelection){
				return interactWorldLabelCommentLabelDao.QueryWorldLabelCommentLabel(maxId, rowSelection);
			}
			
			@Override
			public long getTotalByMaxId(int maxId){
				return interactWorldLabelCommentLabelDao.GetWorldLabelCommentLabelCount(maxId);
			}
		},OptResult.ROWS, OptResult.TOTAL, OptResult.JSON_KEY_MAX_ID);
	}
	
	@Override
	public List<InteractCommentLabel> QueryCommentLabel(){
		return interactCommentLabelDao.queryLabel();
	}
	
	/**
	 * 查询分组树
	 */
	@Override
	public List<InteractWorldLabelTreeDto> GetWorldLabelTree() throws Exception{
		List<InteractWorldLabelTreeDto> treeList = null;
		treeList = interactWorldLabelCommentLabelDao.QueryAllWorldTypeToTree();
		for(InteractWorldLabelTreeDto o:treeList){
			List<InteractWorldLabelTreeDto> t = interactWorldLabelCommentLabelDao.QueryWorldLabelToTreeByTypeId(o.getId());
			o.setChildren(t);
		}
		return treeList;
		
	}
	
	@Override
	public void DeleteWorldLabelCommentLabelByIds(String idsStr){
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		interactWorldLabelCommentLabelDao.DeleteWorldLabelCommentLabelByIds(ids);
	}
	
	@Override
	public void AddWorldLabelCommentLabelCount(WorldLabelCommentLabelDto userLabelCommentLabelDto)throws Exception{
		interactWorldLabelCommentLabelDao.AddWorldLabelCommentLabelCount(userLabelCommentLabelDto);
	}

}
