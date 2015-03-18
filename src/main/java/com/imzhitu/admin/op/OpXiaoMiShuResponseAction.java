package com.imzhitu.admin.op;

import java.io.PrintWriter;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.base.constant.Tag;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.OpXiaoMiShuResponse;
import com.imzhitu.admin.op.service.OpXiaoMiShuResponseService;
/**
 * 小秘书自动回复控制器
 * @author zxx
 *
 */
public class OpXiaoMiShuResponseAction extends BaseCRUDAction{

	private static final long serialVersionUID = 4192197970982541739L;
	@Autowired
	private OpXiaoMiShuResponseService service;
	
	private Integer keyId;
	private Integer moduleId;
	private Integer moduleValid;
	private Integer responseId;
	private Integer keyValid;
	private String content;
	private String key;
	private String moduleName;
	private String keyStr;
	private String keyIdStr;
	

	/**
	 * 分页查询 key
	 * @return
	 */
	public String queryXiaoMiShuResponseForTable(){
		try{
			service.queryResponseAndKey(moduleValid, page, rows, keyId, moduleId, key, keyValid, responseId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 分页查询 模块
	 * @return
	 */
	public String queryXiaoMiShuResponseModuleForTable(){
		try{
			service.queryResponseModuleForTable(keyId, page, rows, moduleId, moduleValid, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 分页查询 回复
	 * @return
	 */
	public String queryXiaoMiShuResponseContentForTable(){
		try{
			service.queryResponseContentForTable(keyId, page, rows, moduleId, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询所有模块,一组关键字，对应一个模块
	 * @return
	 */
	public String queryResponseModule(){
		PrintWriter out = null;
		try{
			out = response.getWriter();
			List<OpXiaoMiShuResponse> list = service.queryResponseModule(null,null, Tag.TRUE); 
			JSONArray jsArray = JSONArray.fromObject(list);
			out.print(jsArray.toString());
			out.flush();
		}catch(Exception e){
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(json.toString());
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 新增回复
	 * @return
	 */
	public String insertResponse(){
		try{
			service.insertResponse(null, content,moduleId,getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.ADD_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 新增小秘书key
	 * @return
	 */
	public String insertResponseKey(){
		try{
			service.insertResponseKey(null, moduleId, key, Tag.TRUE, responseId,getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.ADD_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 新增小秘书模块
	 * @return
	 */
	public String insertResponseModule(){
		try{
			service.insertResponseModule(null,moduleName, Tag.TRUE,getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.ADD_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新回复内容
	 * @return
	 */
	public String updateResponse(){
		try{
			service.updateResponse(responseId, content,getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.UPDATE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新回复key
	 * @return
	 */
	public String updateResponseKey(){
		try{
			service.updateResponseKey(keyId, key, keyValid,getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.UPDATE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 更新回复模块
	 * @return
	 */
	public String updateResponseModule(){
		try{
			service.updateResponseModule(moduleId, moduleValid,getCurrentLoginUserId());;
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.UPDATE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量增加关键字
	 * @return
	 */
	public String batchAddResponseKey(){
		try{
			service.batchAddResponseKey(keyStr, moduleId, responseId, getCurrentLoginUserId());
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.ADD_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public String batchDelResponseKey(){
		try{
			service.batchDelResponseKey(keyIdStr);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(OptResult.DELETE_FAILED, jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	public Integer getKeyId() {
		return keyId;
	}
	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public Integer getModuleValid() {
		return moduleValid;
	}
	public void setModuleValid(Integer moduleValid) {
		this.moduleValid = moduleValid;
	}
	public Integer getResponseId() {
		return responseId;
	}
	public void setResponseId(Integer responseId) {
		this.responseId = responseId;
	}
	public Integer getKeyValid() {
		return keyValid;
	}
	public void setKeyValid(Integer keyValid) {
		this.keyValid = keyValid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getKeyStr() {
		return keyStr;
	}

	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}

	public String getKeyIdStr() {
		return keyIdStr;
	}

	public void setKeyIdStr(String keyIdStr) {
		this.keyIdStr = keyIdStr;
	}
	
	
}
