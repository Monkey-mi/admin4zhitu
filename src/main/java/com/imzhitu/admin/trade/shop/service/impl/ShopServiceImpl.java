package com.imzhitu.admin.trade.shop.service.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.OptResult;
import com.imzhitu.admin.addr.AddrUtil;
import com.imzhitu.admin.trade.shop.mapper.ShopMapper;
import com.imzhitu.admin.trade.shop.pojo.Shop;
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
	
	@Override
	public void addShop(String shopName, String description, Integer countryId, Integer provinceId, Integer cityId, Integer districtId, String address, String email, String zipcode, String website, String phone, String telephone, Integer qq,
			String typeIds) {
		Shop shop = new Shop();
		shopMapper.insertShop(shop);
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
			dto.setStarAvg(shop.getStarAvg());
			dto.setTasteAvg(shop.getTasteAvg());
			dto.setViewAvg(shop.getViewAvg());
			dto.setServiceAvg(shop.getServiceAvg());
			dto.setPCD(AddrUtil.getProvince(shop.getProvinceId()), AddrUtil.getCity(shop.getCityId()), AddrUtil.getDistrict(shop.getDistrictId()));
			
			rtnlist.add(dto);
		}
		
		return rtnlist;
	}

	@Override
	public void deleteShop(Integer id) {
		shopMapper.delete(id);
	}
	
	/**
	 * @param args
	 * @author zhangbo	2015年11月23日
	 * @throws ScriptException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, ScriptException {
		
//		String poi = "ISHWBIZUADGUSU";
//		String poi = "ISRIDGZUAGUFDQ";
//		String poi = "ISRHGHZUAGUBWP";
		String poi = "ISRIISZUAGUSUT";
//		Map<String, Float> decode = decode(poi);
//		System.out.println(decode.get("lat"));
//		System.out.println(decode.get("lng"));
		
		
		
//		System.out.println(baseString(1295,36));
//		BigInteger big=new BigInteger("999999999999");
//		System.out.println(baseString(big,36));
		
		
		ScriptEngineManager sem=new ScriptEngineManager();
		ScriptEngine se=sem.getEngineByExtension("js");
		se.eval(new FileReader("C:\\Users\\PicWaeaver\\Desktop\\decode.js"));
		Double lat=(Double) se.eval("eval(\"decodeLat('"+poi+"')\")");
		Double lng=(Double) se.eval("eval(\"decodeLng('"+poi+"')\")");
		
		System.out.println(lat);
		System.out.println(lng);
	}
	
	public static Map<String,Float> decode(String C) {  
        int digi=16;  
        int add= 10;  
        int plus=7;  
        int cha=36;  
        int I = -1;  
        int H = 0;  
        String B = "";  
        int J = C.length();  
        char G = C.charAt(J - 1);
        System.out.println(G);
        C = C.substring(0, J - 1);  
        J--;  
        for (int E = 0; E < J; E++) {
            Integer D = Integer.getInteger(String.valueOf(C.charAt(E)), cha) - add;  
            if (D >= add) {
                D = D - plus;
            }  
            B += baseString(D, cha);
            if (D > H) {
                I = E;  
                H = D;
            }  
        }  
        System.out.println(B.substring(I + 1));
        Integer A = Integer.getInteger(B.substring(0, I), digi);  
        Integer F = Integer.getInteger(B.substring(I + 1), digi);  
        System.out.println(A);
        System.out.println(F);
        Float L = (float) ((A + F - Integer.parseInt(String.valueOf(G),36)) / 2);  
        Float K = (F - L) / 100000;  
        L /= 100000;
        
        Map<String,Float> map = new HashMap<String,Float>();
        map.put("lat", K);
        map.put("lng", L);
        return map;
	}
	
	/** 
	* baseString 递归调用 
	* @param num 十进制数 
	* @param base 要转换成的进制数 
	*/ 
	public static String baseString(int num,int base) { 
		String str = "", digit = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
		if(num == 0){ 
		return ""; 
		}else { 
		str = baseString(num / base,base); 
		return str + digit.charAt(num % base); 
		} 
	}

	public static String baseString(BigInteger num,int base) { 
		String str = "", digit = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
		if(num.shortValue() == 0){ 
		return ""; 
		}else { 
		BigInteger valueOf = BigInteger.valueOf(base);
		str = baseString(num.divide(valueOf),base); 
		return str + digit.charAt(num.mod(valueOf).shortValue()); 
		} 
	}

	/** 
	* 将字符串转成unicode 
	* @param str 待转字符串 
	* @return unicode字符串 
	*/ 
//	public static String convert(String str) {
//		str = (str == null ? "" : str); 
//		String tmp; 
//		StringBuffer sb = new StringBuffer(1000); 
//		char c; 
//		int i, j; 
//		sb.setLength(0); 
//		for (i = 0; i < str.length(); i++) 
//		{ 
//		c = str.charAt(i); 
//		sb.append("\\u"); 
//		j = (c >>>8); //取出高8位 
//		tmp = Integer.toHexString(j); 
//		if (tmp.length() == 1) 
//		sb.append("0"); 
//		sb.append(tmp); 
//		j = (c & 0xFF); //取出低8位 
//		tmp = Integer.toHexString(j); 
//		if (tmp.length() == 1) 
//		sb.append("0"); 
//		sb.append(tmp); 
//	
//		} 
//		return (new String(sb)); 
//	} 

}
