package com.imzhitu.admin.op.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.database.Admin;
import com.imzhitu.admin.common.pojo.OpXiaoMiShuResponse;
import com.imzhitu.admin.common.service.KeyGenService;
import com.imzhitu.admin.op.mapper.OpXiaoMiShuResponseMapper;
import com.imzhitu.admin.op.service.OpXiaoMiShuResponseService;

@Service
public class OpXiaoMiShuResponseServiceImpl extends BaseServiceImpl implements OpXiaoMiShuResponseService{
	
	@Autowired
	private OpXiaoMiShuResponseMapper opXiaoMiShuResponseMapper;
	
	@Autowired
	private KeyGenService keyGenService;
	
	/**
	 * 插入小秘书回复字典
	 * @param id can be null. 
	 * 
	 */
	@Override
	public void insertResponse(Integer responseId,String content,Integer moduleId,Integer operatorId)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		if(responseId == null){
			responseId = keyGenService.generateId(Admin.KEYGEN_XIAOMISHU_RESPONSE_ID);
		}
		Date now = new Date();
		vo.setOperatorId(operatorId);
		vo.setModifyDate(now);
		vo.setResponseId(responseId);
		vo.setContent(content);
		vo.setModuleId(moduleId);
		opXiaoMiShuResponseMapper.insertResponse(vo);
	}
	
	/**
	 * 插入小秘书回复key字典
	 * @param vo
	 */
	@Override
	public void insertResponseKey(Integer keyId,Integer moduleId,String key,Integer keyValid,Integer responseId,Integer operatorId)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		if( keyId == null ) {
			keyId = keyGenService.generateId(Admin.KEYGEN_XIAOMISHU_RESPONSE_KEY_ID);
		}
		Date now = new Date();
		vo.setOperatorId(operatorId);
		vo.setModifyDate(now);
		vo.setKeyId(keyId);
		vo.setKey(key);
		vo.setModuleId(moduleId);
		vo.setKeyValid(keyValid);
		vo.setResponseId(responseId);
		opXiaoMiShuResponseMapper.insertResponseKey(vo);
	}
	
	
	/**
	 * 插入小秘书模块
	 * @param vo
	 */
	@Override
	public void insertResponseModule(Integer moduleId,String moduleName,Integer moduleValid,Integer operatorId)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		if( moduleId == null){
			moduleId = keyGenService.generateId(Admin.KEYGEN_XIAOMISHU_RESPONSE_MODULE_ID);
		}
		Date now = new Date();
		vo.setOperatorId(operatorId);
		vo.setModifyDate(now);
		vo.setModuleId(moduleId);
		vo.setModuleValid(moduleValid);
		vo.setModuleName(moduleName);
		opXiaoMiShuResponseMapper.insertResponseModule(vo);
	}
	
	/**
	 * 删除小秘书回复字典
	 * @param vo
	 */
	@Override
	public void deleteResponse(Integer responseId)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		vo.setResponseId(responseId);
		opXiaoMiShuResponseMapper.deleteResponse(vo);
	}
	
	/**
	 * 删除小秘书回复key字典
	 * @param vo
	 */
	@Override
	public void deleteResponseKey(Integer keyId)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		vo.setKeyId(keyId);
		opXiaoMiShuResponseMapper.deleteResponseKey(vo);
	}
	
	/**
	 * 删除小秘书模块
	 * @param vo
	 */
	@Override
	public void deleteResponseModule(Integer moduleId,Integer moduleValid)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		vo.setModuleId(moduleId);
		vo.setModuleValid(moduleValid);
		opXiaoMiShuResponseMapper.deleteResponseModule(vo);
	}
	
	/**
	 * 更新小秘书回复字典
	 * @param vo
	 */
	@Override
	public void updateResponse(Integer responseId,String content,Integer operatorId)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		Date now = new Date();
		vo.setOperatorId(operatorId);
		vo.setModifyDate(now);
		vo.setResponseId(responseId);
		vo.setContent(content);
		opXiaoMiShuResponseMapper.updateResponse(vo);
	}
	
	
	/**
	 * 更新小秘书key
	 * @param vo
	 */
	@Override
	public void updateResponseKey(Integer keyId,String key,Integer keyValid,Integer operatorId)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		Date now = new Date();
		vo.setOperatorId(operatorId);
		vo.setModifyDate(now);
		vo.setKeyId(keyId);
		vo.setKey(key);
		vo.setKeyValid(keyValid);
		opXiaoMiShuResponseMapper.updateResponseKey(vo);
	}
	
	
	/**
	 * 更新小秘书模块
	 * @param vo
	 */
	@Override
	public void updateResponseModule(Integer moduleId,Integer moduleValid,Integer operatorId)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		Date now = new Date();
		vo.setOperatorId(operatorId);
		vo.setModifyDate(now);
		vo.setModuleId(moduleId);
		vo.setModuleValid(moduleValid);
		opXiaoMiShuResponseMapper.updateResponseModule(vo);		
	}
	
	
	/**
	 * 查询
	 * @param vo
	 * @return
	 */
	@Override
	public void queryResponseAndKey(Integer maxId,int page,int rows,
			Integer keyId,Integer moduleId,String key,Integer keyValid,Integer responseId,Map<String, Object> jsonMap)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		vo.setMaxId(maxId);
		vo.setKeyId(keyId);
		vo.setKey(key);
		vo.setModuleId(moduleId);
		vo.setKeyValid(keyValid);
		vo.setResponseId(responseId);
		buildNumberDtos("getKeyId",vo,page,rows,jsonMap,new NumberDtoListAdapter<OpXiaoMiShuResponse>(){
			@Override
			public long queryTotal(OpXiaoMiShuResponse vo){
				return opXiaoMiShuResponseMapper.queryResponseAndKeyTotalCount(vo);
			}
			@Override
			public List< ? extends AbstractNumberDto> queryList(OpXiaoMiShuResponse vo){
				return opXiaoMiShuResponseMapper.queryResponseAndKey(vo);
			}
		});
	}
	
	
	/**
	 * 查询所有的小秘书模块
	 * @param vo
	 * @return
	 */
	@Override
	public List<OpXiaoMiShuResponse>queryResponseModule(Integer moduleId,String moduleName,Integer moduleValid)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		vo.setModuleId(moduleId);
		vo.setModuleValid(moduleValid);
		vo.setModuleName(moduleName);
		return opXiaoMiShuResponseMapper.queryResponseModule(vo);
	}
	
	
	/**
	 * 分页查询模块
	 * @param vo
	 * @return
	 */
	@Override
	public void queryResponseModuleForTable(Integer maxId,int page,int rows,
			Integer moduleId,Integer moduleValid,Map<String, Object> jsonMap)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		vo.setMaxId(maxId);
		vo.setModuleId(moduleId);
		vo.setModuleValid(moduleValid);
		buildNumberDtos("getModuleId",vo,page,rows,jsonMap,new NumberDtoListAdapter<OpXiaoMiShuResponse>(){
			@Override
			public long queryTotal(OpXiaoMiShuResponse vo){
				return opXiaoMiShuResponseMapper.queryResponseModuleTotalCount(vo);
			}
			@Override
			public List< ? extends AbstractNumberDto> queryList(OpXiaoMiShuResponse vo){
				return opXiaoMiShuResponseMapper.queryResponseModuleForTable(vo);
			}
		});
	}
	
	
	/**
	 * 分页查询回复内容 
	 * @param vo
	 * @return
	 */
	@Override
	public void queryResponseContentForTable(Integer maxId,int page,int rows,
			Integer moduleId,Map<String, Object> jsonMap)throws Exception{
		OpXiaoMiShuResponse vo = new OpXiaoMiShuResponse();
		vo.setMaxId(maxId);
		vo.setModuleId(moduleId);
		buildNumberDtos("getResponseId",vo,page,rows,jsonMap,new NumberDtoListAdapter<OpXiaoMiShuResponse>(){
			@Override
			public long queryTotal(OpXiaoMiShuResponse vo){
				return opXiaoMiShuResponseMapper.queryResponseContentTotalCount(vo);
			}
			@Override
			public List< ? extends AbstractNumberDto> queryList(OpXiaoMiShuResponse vo){
				return opXiaoMiShuResponseMapper.queryResponseContentForTable(vo);
			}
		});
	}
	
	/**
	 * 批量增加key
	 * @param keyStr
	 * @param responseId
	 * @throws Exception
	 */
	public void batchAddResponseKey(String keyStr,Integer moduleId,Integer responseId,Integer operatorId)throws Exception{
		if( null == keyStr)return;
		String key[] = keyStr.split("\r\n");
		for(int i=0; i<key.length; i++){
			insertResponseKey(null, moduleId, key[i], Tag.TRUE, responseId, operatorId);
		}
	}
	
}
