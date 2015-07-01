package com.imzhitu.admin.op;

import java.io.PrintWriter;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.common.pojo.OpZombieDegree;
import com.imzhitu.admin.op.service.OpZombieDegreeService;

public class OpZombieDegreeAction extends BaseCRUDAction{
	private static final long serialVersionUID = 8010060301346856716L;

	@Autowired
	private OpZombieDegreeService zombieDegreeService;
	
	private Integer id;
	private String degreeName;
	private Integer weight;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDegreeName() {
		return degreeName;
	}
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public String queryZombieDegree(){
		try{
			zombieDegreeService.queryZombieDegree(id, weight, maxId, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 跟新
	 * @return
	 */
	public String updateZombieDegree(){
		try{
			zombieDegreeService.updateZombieDegree(id, degreeName, weight);
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 插入
	 * @return
	 */
	public String insertZombieDegree(){
		try{
			zombieDegreeService.insertZombieDegree(degreeName, weight);
			JSONUtil.optSuccess(OptResult.ADD_SUCCESS, jsonMap);
		}catch(Exception e){
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	
	public String queryAllZombieDegree(){
		PrintWriter out = null;
		try {
			out = response.getWriter();
			List<OpZombieDegree> list = zombieDegreeService.queryAllZombieDegree();
			JSONArray jsArray = JSONArray.fromObject(list);
			out.print(jsArray.toString());
			out.flush();
		} catch(Exception e) {
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
	
}
