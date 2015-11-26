package com.imzhitu.admin.addr;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;

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
			out = response.getWriter();
			JSONArray jsArray = JSONArray.fromObject(addrService.queryDistricts());
			out.print(jsArray.toString());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}