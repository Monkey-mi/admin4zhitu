package com.imzhitu.admin.trade.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.imzhitu.admin.trade.shop.mapper.ShopBaseMapper;
import com.imzhitu.admin.trade.shop.mapper.ShopMapper;
import com.imzhitu.admin.trade.shop.pojo.Shop;
import com.imzhitu.admin.trade.shop.pojo.ShopBase;
import com.imzhitu.admin.trade.shop.pojo.ShopDTO;
import com.imzhitu.admin.trade.shop.service.ShopService;

/**
 * 商家业务逻辑实现类
 * 
 * @author zhangbo	2015年11月19日
 *
 */
@Service
public class ShopServiceImpl implements ShopService {
	
	@Autowired
	private ShopMapper shopMapper;
	
	@Autowired
	private ShopBaseMapper shopBaseMapper;
	
	@Override
	public void addShop(String shopName, String description, Integer countryId, Integer provinceId, Integer cityId, Integer districtId, String address, String email, String zipcode, String website, String phone, String telephone, Integer qq,
			String typeIds) {
		Shop shop = new Shop();
		shopMapper.insertShop(shop);
	}

	@Override
	public void buildBaseShopList(Integer cityId, int page, int rows, Map<String, Object> jsonMap) throws Exception {
		
		// 定义返回商家前台展示信息列表
		List<ShopDTO> rtnlist = new ArrayList<ShopDTO>();
		// 定义商家总数
		Integer total = 0;
		
		// 定义查询起始位置，由查询页数与每页多少数据决定
		Integer start = (page - 1) * rows;
		Integer limit = rows;
		
		// 得到商家信息列表
		List<ShopBase> shopList =  shopBaseMapper.queryShopListByLimit(cityId, start,limit);
		
		rtnlist = convertShopBaseToShopDTO(shopList);
		
		total = shopBaseMapper.getShopTotalCount();
		
		jsonMap.put(OptResult.JSON_KEY_ROWS, rtnlist);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
	}
	
	/**
	 * 根据获取的商家信息集合，转化为要展示到前台的商家展示列
	 * 
	 * @param list<Shop>	商家信息对象集合
	 * @return	list<ShopDTO>	商家信息展示对象集合
	 * @author zhangbo	2015年11月20日
	 */
	private List<ShopDTO> convertShopBaseToShopDTO(List<ShopBase> list) {
		List<ShopDTO> rtnlist = new ArrayList<ShopDTO>();
		
		for (ShopBase shop : list) {
			ShopDTO dto = new ShopDTO();
			dto.setId(shop.getId());
			dto.setName(shop.getName());
			dto.setDescription(shop.getDescription());
			dto.setStarAvg(shop.getStarAvg());
			dto.setTasteAvg(shop.getTasteAvg());
			dto.setViewAvg(shop.getViewAvg());
			dto.setServiceAvg(shop.getServiceAvg());
			dto.setPCD(null, null, null);
			
			rtnlist.add(dto);
		}
		
		return rtnlist;
	}
	
	/**
	 * 根据获取的商家信息集合，转化为要展示到前台的商家展示列
	 * 
	 * @param list<Shop>	商家信息对象集合
	 * @return	list<ShopDTO>	商家信息展示对象集合
	 * @author zhangbo	2015年11月20日
	 */
	private List<ShopDTO> convertShopToShopDTO(List<Shop> list) {
		List<ShopDTO> rtnlist = new ArrayList<ShopDTO>();
		
		for (Shop shop : list) {
			ShopDTO dto = new ShopDTO();
			dto.setId(shop.getId());
			dto.setName(shop.getName());
			dto.setDescription(shop.getDescription());
			dto.setPCD(null, null, null);
			
			rtnlist.add(dto);
		}
		
		return rtnlist;
	}
	
	@Override
	public void buildShopList(int page, int rows, Map<String, Object> jsonMap) throws Exception {
		
		// 定义返回商家前台展示信息列表
		List<ShopDTO> rtnlist = new ArrayList<ShopDTO>();
		// 定义商家总数
		Integer total = 0;
		
		// 定义查询起始位置，由查询页数与每页多少数据决定
		Integer start = (page - 1) * rows;
		Integer limit = rows;
		
		// 得到商家信息列表
		List<Shop> shopList =  shopMapper.queryShopListByLimit(start,limit);
		
		rtnlist = convertShopToShopDTO(shopList);
		
		total = shopMapper.getShopTotalCount();
		
		jsonMap.put(OptResult.JSON_KEY_ROWS, rtnlist);
		jsonMap.put(OptResult.JSON_KEY_TOTAL, total);
	}

	@Override
	public void deleteShop(Integer id) {
		shopMapper.delete(id);
	}

	@Override
	public void addShopByBase(Integer[] sids) {
		shopMapper.queryShopByIds(sids);
	}

}
