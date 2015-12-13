package com.imzhitu.admin.trade.item.service;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.trade.item.pojo.Item;

/**
 * 商品业务逻辑访问接口单元测试
 * 
 * @author lynch
 *
 */
public class ItemServiceTest extends BaseTest {

	private static Logger log = Logger.getLogger(ItemServiceTest.class);
	
	@Autowired
	private ItemService service;
	
	@Test
	public void saveItemTest() throws Exception {
		Item item = new Item();
		item.setName("哈哈");
		item.setSummary("哇哈哈哈");
		item.setDescription("哈哈哈");
		item.setPrice(new BigDecimal(12.0));
		item.setImgPath("imzhiasdf");
		item.setTaobaoId(123123l);
		item.setTaobaoType(1);
		item.setLink("imzhitu.com");
		Integer id = service.saveItem(item);
		service.batchDeleteItem(id.toString());
	}
	
	@Test
	public void updateItemTest() throws Exception {
		Item item = new Item();
		item.setId(131);
		item.setName("夜夜夜夜");
		service.updateItem(item);
	}
	
	@Test
	public void buildItemTest() throws Exception {
		logNumberList(log, new TestNumberListAdapter() {
			
			@Override
			public void buildTestNumberList(Map<String, Object> jsonMap) throws Exception {
				service.buildItemList(new Item(), 1, 10, jsonMap);
			}
		});
	}

	@Test
	public void saveSetItemTest() throws Exception {
		service.saveSetItem(1, 131);
	}
	
	@Test
	public void buildSetItemTest() throws Exception {
		final Item item = new Item();
		item.setItemSetId(1);
		logNumberList(log, new TestNumberListAdapter() {
			
			@Override
			public void buildTestNumberList(Map<String, Object> jsonMap) throws Exception {
				service.buildItemList(item, 1, 10, jsonMap);
			}
		});
	}
	
	@Test
	public void batchDeleteSetItemTest() throws Exception {
		service.batchDeleteSetItem(1, "122");
	}
	
	@Test
	public void updateSerialTest() throws Exception {
		service.updateSetItemSerial(1, new String[]{"131"});
	}
	
	
}
