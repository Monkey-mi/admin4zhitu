package com.imzhitu.admin.statistics;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.common.util.JSONUtil;
import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.statistics.pojo.StatPvDto;
import com.imzhitu.admin.statistics.service.StatService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 统计RESTFUL接口
 * 
 * @author lynch 2015-12-19
 *
 */
public class PvAction extends BaseCRUDAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1957414256734115538L;

	@Autowired
	private StatService statService;

	private String beginDateStr;
	private String endDateStr;

	private StatPvDto pv = new StatPvDto();

	/**
	 * 查询PV
	 * 
	 * @return
	 */
	public String queryPv() {
		try {
			statService.buildPV(pv, beginDateStr, endDateStr,
					page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch (Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 获取统计键
	 * 
	 * @return
	 */
	public String queryPvKey() {
		PrintWriter out = null;
		try{
			out = response.getWriter();
			List<StatPvDto> list = statService.getStatKeyNameList();
			JSONArray jsArray = JSONArray.fromObject(list);
			out.print(jsArray.toString());
			out.flush();
		}catch(Exception e){
			jsonMap.clear();
			JSONUtil.optFailed(e.getMessage(), jsonMap);
			JSONObject json = JSONObject.fromObject(jsonMap);
			out.print(json.toString());
			out.flush();
		}finally{
			out.close();
		}
		return null;
	}

	public StatPvDto getPv() {
		return pv;
	}

	public void setPv(StatPvDto pv) {
		this.pv = pv;
	}

	public String getBeginDateStr() {
		return beginDateStr;
	}

	public void setBeginDateStr(String beginDateStr) {
		this.beginDateStr = beginDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

}
