package com.imzhitu.admin;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.addr.service.AddrService;
import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.trade.shop.ShopBAK;
import com.imzhitu.admin.trade.shop.mapper.ShopBAKMapper;
import com.imzhitu.admin.trade.shop.mapper.ShopCommentMapper;
import com.imzhitu.admin.trade.shop.mapper.ShopMapper;
import com.imzhitu.admin.trade.shop.pojo.Shop;

/**
 * @author zhangbo	2015年11月23日
 *
 */
public class Test extends BaseTest{
	
	private static ScriptEngine se;
	
	static {
		ScriptEngineManager sem=new ScriptEngineManager();
		se=sem.getEngineByExtension("js");
		try {
			se.eval(new FileReader("C:\\Users\\PicWaeaver\\Desktop\\decode.js"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Autowired
	private ShopMapper shopMapper;
	
	@Autowired
	private ShopBAKMapper bakMapper;
	
	@Autowired
	private ShopCommentMapper cMapper;	
	
	@Autowired
	private AddrService addrService;
	
	@org.junit.Test
	public void test() throws FileNotFoundException, ScriptException {
		
		List<ShopBAK> shopBakList = bakMapper.queryShopBakListByLimit(0, 5000);
		
		for (ShopBAK shopBAK : shopBakList) {
			Shop shop = new Shop();
			
			shop.setId(shopBAK.getId());
			
			shop.setName(shopBAK.getName());
			shop.setDescription(shopBAK.getDescription());
			
			
			shop.setAddress(shopBAK.getAddress());
			
			// TODO 等小米的接口
			shop.setCountryId(10238);
			shop.setProvinceId(addrService.getProvinceId(shopBAK.getProvinceId()));
			shop.setCityId(addrService.getCityId(shopBAK.getCityId()));
			Integer districtId = addrService.getDistrictId(shopBAK.getDistrictId(), addrService.getCityId(shopBAK.getCityId()));
			shop.setDistrictId(districtId == null ? 0: districtId);
			
			if (shopBAK.getPoi() != null && !shopBAK.getPoi().equals("")) {
				Map<String, Double> m = decode(shopBAK.getPoi());
				shop.setLatitude(m.get("lat"));
				shop.setLongitude(m.get("lng"));
			} else {
				shop.setLatitude(0d);
				shop.setLongitude(0d);
			}
			
			
			shop.setEmail(shop.getEmail());
			shop.setZipcode(shopBAK.getZipcode());
			shop.setWebsite(shopBAK.getWebsite());
			shop.setPhoneCode(shopBAK.getPhoneCode());
			shop.setPhone(shopBAK.getPhone());
			shop.setTelCode(shop.getTelCode());
			shop.setTel(shopBAK.getTel());
			shop.setQq(shopBAK.getQq());
			shop.setType(shopBAK.getType());
			shop.setStarAvg(shopBAK.getStarAvg());
			shop.setTasteAvg(shopBAK.getTasteAvg());
			shop.setViewAvg(shopBAK.getTasteAvg());
			shop.setServiceAvg(shopBAK.getServiceAvg());
			
			String comment = shopBAK.getComment();
			saveComments(shopBAK.getId(),comment);
			
			// 连带插入id
			shopMapper.insertShopLinshi(shop);
			
//			shopMapper.insertShop(shop);
		}
		
	}
	
	private static Map<String,Double> decode(String poi) throws ScriptException, FileNotFoundException {
		
//		ScriptEngineManager sem=new ScriptEngineManager();
//		ScriptEngine se=sem.getEngineByExtension("js");
//		se.eval(new FileReader("C:\\Users\\PicWaeaver\\Desktop\\decode.js"));
		Double lat=(Double) se.eval("eval(\"decodeLat('"+poi+"')\")");
		Double lng=(Double) se.eval("eval(\"decodeLng('"+poi+"')\")");
		
		Map<String, Double> m = new HashMap<String, Double>();
		m.put("lat", lat);
		m.put("lng", lng);
		return m;
	}
	
	private void saveComments(Integer shopId, String comment){
//		comment="要从电影院的电梯上来，一路像住宿区，进一个大门里面，包括好几个餐厅都在里面挤着。"
//				+ "<br/><br/>味道还是可以，和黄记煌差不多，主要是比较便宜哈哈哈哈哈哈哈～ ！！<br/><br/>二人餐分量蛮多，两个人吃完全足够吃得撑撑的那种，饮料自助，其实就是可乐雪碧芬达…妹子秒到了一堆的王府井1元代金券带我来一日游，之前就在群里看到了说这家是要再加两个菜才能吃的，最开始以为是加两个素菜之后就可以，跟妹子来了之后店家说是两个肉菜，看我们......"
//+"和闺蜜一起秒杀的券，一人秒杀到一份牛肉，以为就一人一份，结果去了以后才知道还必须点两个荤菜，四个荤菜才是一锅，团购又不写清楚，太坑爹了，本来想不吃就走了，因为逛街到......"
//+"跟同事四个人团了一个四人餐，分量很足，吃的很饱最后还没吃完。可乐，芬达，雪碧汽水饮料免费，非常划算！位置就在王府井八楼，挨着鹿港小镇和鱼旨寿司，很好找！"
//+"环境：店不大，看着挺干净的<br/>口味：团购的套餐，88元好多东西，选的鲶鱼，很好吃，还送了小吃香芋卷，味道很棒。香辣味的真的很辣，建议吃不了辣的朋友还是就点微辣吧，吃完了加水煮蔬菜面，味道真是一级棒！！<br/>服务：还可以，就是不提供纸巾，大家要自己准备哦"
//+"大众点评的活动我一直都是很喜欢参加的，也是点评网的忠实粉丝，但这次秒杀遇到这样的问题，我真的很头痛，投诉后客服的处理结果还是很满意。团购页面上写的是 王府井百货秒杀套......"
//+"相当喜感的用餐体验，和基友一起去吃的。前台的服务员态度很好，跟你推荐酱料哪种比较好吃。主要是负责下菜的服务员特别奇葩，客人聊天，他还会参与进来……语气也很讽刺……醉了……总的来说性价比比黄家高多了啊，人均40，味道还行。不过环境不太好，有点狭窄，卫生间也很远……"
//+"当时王府井美食搞秒杀才来的，这一片吃的也太偏僻了，下次应该不会再来了";
		comment = comment.replace("/", "");
		
//		System.out.println(comment);
		
		
		String[] comments = comment.split("<br>");
		
		for (String c : comments) {
			if (!c.trim().equals("")){
				if ( c.contains("大众点评") || c.contains("大众") || c.contains("点评") ) {
					continue;
				} else {
					cMapper.insert(shopId,c);
				}
			}
		}
		
	}
	
	public static void main(String[] args) throws FileNotFoundException, ScriptException {
//		saveComments(null,null);
		Map<String, Double> decode = decode("HETCDTZVVJWBHK");
		System.out.println(decode.get("lat"));
		System.out.println(decode.get("lng"));
	}
}
