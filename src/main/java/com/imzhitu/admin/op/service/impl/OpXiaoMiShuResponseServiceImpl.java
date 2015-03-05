package com.imzhitu.admin.op.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.OpXiaoMiShuResponse;
import com.imzhitu.admin.common.service.KeyGenService;
import com.imzhitu.admin.op.mapper.OpXiaoMiShuResponseMapper;
import com.imzhitu.admin.op.service.OpXiaoMiShuResponseService;

@Service
public class OpXiaoMiShuResponseServiceImpl implements OpXiaoMiShuResponseService{
	
	@Autowired
	private OpXiaoMiShuResponseMapper opXiaoMiShuResponseMapper;
	
	@Autowired
	private KeyGenService keyGenService;
	
	/**
	 * 插入小秘书回复字典
	 * @param id can be null. 
	 * 
	 */
	public void insertResponse(Integer id,String content)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		if(id == null){
			id = keyGenService.generateId(Admin.KEYGEN_XIAOMISHU_RESPONSE_ID);
		}
		vo.setResponseId(id);
		vo.setContent(content);
		opXiaoMiShuResponseMapper.deleteResponse(vo);
	}
	
	/**
	 * 插入小秘书回复key字典
	 * @param vo
	 */
	public void insertResponseKey(Integer id,String module,String key,Integer keyLength,Integer responseId)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		if( id == null ) {
			id = keyGenService.generateId(Admin.KEYGEN_XIAOMISHU_RESPONSE_KEY_ID);
		}
		vo.setKeyId(id);
		vo.setKey(key);
		vo.setModule(module);
		vo.setKeyLength(keyLength);
		vo.setResponseId(responseId);
		opXiaoMiShuResponseMapper.insertResponseKey(vo);
	}
	
	/**
	 * 删除小秘书回复字典
	 * @param vo
	 */
	public void deleteResponse(Integer id)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		vo.setResponseId(id);
		opXiaoMiShuResponseMapper.deleteResponse(vo);
	}
	
	/**
	 * 删除小秘书回复key字典
	 * @param vo
	 */
	public void deleteResponseKey(Integer id)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		vo.setKeyId(id);
		opXiaoMiShuResponseMapper.deleteResponseKey(vo);
	}
	
	/**
	 * 更新小秘书回复字典
	 * @param vo
	 */
	public void updateResponse(Integer id,String content)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		vo.setResponseId(id);
		vo.setContent(content);
		opXiaoMiShuResponseMapper.updateResponse(vo);
	}
	
	/**
	 * 查询
	 * @param vo
	 * @return
	 */
	public List<OpXiaoMiShuResponse> queryResponseAndKey(Integer keyId,String module,String key,Integer keyLength,Integer responseId)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		vo.setKeyId(keyId);
		vo.setKey(key);
		vo.setModule(module);
		vo.setKeyLength(keyLength);
		vo.setResponseId(responseId);
		return opXiaoMiShuResponseMapper.queryResponseAndKey(vo);
	}
}
