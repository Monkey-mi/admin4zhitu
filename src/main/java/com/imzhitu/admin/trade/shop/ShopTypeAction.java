package com.imzhitu.admin.trade.shop;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.common.BaseCRUDAction;
import com.imzhitu.admin.trade.shop.service.ShopTypeService;

import net.sf.json.JSONArray;

/**
 * 商家类型控制类
 * 
 * @author zhangbo	2015年11月20日
 *
 */
public class ShopTypeAction extends BaseCRUDAction {

	/**
	 * 序列号
	 * @author zhangbo	2015年11月20日
	 */
	private static final long serialVersionUID = -7142934368922928757L;
	
	@Autowired
	private ShopTypeService shopTypeService;
	
	/**
	 * 查询省份列表
	 * 
	 * @return
	 * @author zhangbo	2015年11月20日
	 */
	public void queryShopType() {
		PrintWriter out;
		try {
			out = response.getWriter();
			JSONArray jsArray = JSONArray.fromObject(shopTypeService.queryShopType());
			out.print(jsArray.toString());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
