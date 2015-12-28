package com.imzhitu.admin.addr;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.StrutsKey;
import com.hts.web.base.constant.OptResult;
import com.hts.web.common.util.JSONUtil;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.addr.pojo.City;
import com.imzhitu.admin.addr.service.AddrService;
import com.imzhitu.admin.common.BaseCRUDAction;

import net.sf.json.JSONArray;

/**
 * @author zhangbo	2015年11月20日
 *
 */
public class AddrAction extends BaseCRUDAction {
	
	/**
	 * 序列号
	 * @author zhangbo	2015年11月20日
	 */
	private static final long serialVersionUID = -2079849197360560718L;
	
	/**
	 * 城市信息
	 * @author lynch 2015-12-05
	 */
	private City city = new City();
	
	private String idsStr;
	
	@Autowired
	private AddrService addrService;
	
	/**
	 * 查询省份列表
	 * 
	 * @return
	 * @author zhangbo	2015年11月20日
	 */
	public void getProvinceMap() {
		PrintWriter out;
		try {
			out = response.getWriter();
			JSONArray jsArray = JSONArray.fromObject(addrService.queryProvinces());
			out.print(jsArray.toString());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询城市列表
	 * 
	 * @return
	 * @author zhangbo	2015年11月20日
	 */
	public void getCityMap() {
		PrintWriter out;
		try {
			out = response.getWriter();
			JSONArray jsArray = JSONArray.fromObject(addrService.queryCities());
			out.print(jsArray.toString());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询行政区列表
	 * 
	 * @return
	 * @author zhangbo	2015年11月20日
	 */
	public void getDistrictMap() {
		PrintWriter out;
		try {
			List<Map<String,Serializable>> queryDistricts = new ArrayList<Map<String,Serializable>>();
			if ( city.getId() == null ) {
				queryDistricts = addrService.queryDistricts();
			} else {
				queryDistricts = addrService.queryDistrictsByCityId(city.getId());
			}
			out = response.getWriter();
			JSONArray jsArray = JSONArray.fromObject(queryDistricts);
			out.print(jsArray.toString());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新城市缓存
	 * 
	 * @return
	 */
	public String updateCityCache() {
		try {
			addrService.updateCityCache();
			JSONUtil.optSuccess(OptResult.UPDATE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询城市列表
	 * 
	 * @return
	 */
	public String queryCity() {
		try {
			addrService.queryCity(city, page, rows, jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 查询城市列表
	 * 
	 * @return
	 */
	public String queryCityDistictList() {
		try {
			addrService.queryCityDistict(city.getId(), jsonMap);
			JSONUtil.optSuccess(jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 批量删除城市行政区域
	 * 
	 * @return
	 */
	public String batchDeleteCityDistict() {
		try {
			Integer[] distictIds = StringUtil.convertStringToIds(idsStr);
			addrService.batchDeleteCityDistict(distictIds);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}
	
	/**
	 * 添加城市行政区域关系到MongoDB
	 * 
	 * @return
	 */
	public String addCityDistict() {
		try {
			Integer[] distictIds = StringUtil.convertStringToIds(idsStr);
			addrService.addCityDistict(city.getId(), distictIds);
			JSONUtil.optSuccess(OptResult.DELETE_SUCCESS, jsonMap);
		} catch(Exception e) {
			JSONUtil.optFailed(e.getMessage(), jsonMap);
		}
		return StrutsKey.JSON;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * @return the idsStr
	 */
	public String getIdsStr() {
		return idsStr;
	}

	/**
	 * @param idsStr the idsStr to set
	 */
	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}
	
}
