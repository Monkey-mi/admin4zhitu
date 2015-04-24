package com.imzhitu.admin.ztworld.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.ZTWorldSticker;
import com.imzhitu.admin.common.pojo.ZTWorldStickerType;

public class ZTWorldStickerServiceTest extends BaseTest {
	
	private static Logger log = Logger.getLogger(ZTWorldStickerServiceTest.class);

	@Autowired
	private ZTWorldStickerService service;
	
	@Test
	public void buildTypesTest() throws Exception {
		logNumberList(log, new TestNumberListAdapter() {

			@Override
			public void buildTestNumberList(Map<String, Object> jsonMap)
					throws Exception {
				ZTWorldStickerType type = new ZTWorldStickerType();
				service.buildTypes(type, 1, 10, jsonMap, false);
			}
			
		});
	}
	
	@Test
	public void updateTypeSerserviceialTest() throws Exception {
		service.updateStickerSerial(new String[]{"2"});
	}
	
	@Test
	public void saveTypeTest() throws Exception {
		ZTWorldStickerType type = new ZTWorldStickerType();
		type.setTypeName("海贼王");
		type.setValid(0);
		type.setWeight(0);
		service.saveType(type);
	}
	
	@Test
	public void updateTypeValid() throws Exception {
		service.updateTypeValid("8", 1);
	}
	
	@Test
	public void updateTypeTest() throws Exception {
		ZTWorldStickerType type = new ZTWorldStickerType();
		type.setId(8);
		type.setTypeName("海贼王1");
		type.setValid(0);
		service.updateType(type);
	}
	
	@Test
	public void deleteTypesTest() throws Exception {
		service.deleteTypes("10,11");
	}
	
	@Test
	public void buildStickersTest() throws Exception {
		logNumberList(log, new TestNumberListAdapter() {

			@Override
			public void buildTestNumberList(Map<String, Object> jsonMap)
					throws Exception {
				ZTWorldSticker sticker = new ZTWorldSticker();
				sticker.setValid(0);
				sticker.setWeight(1);
				service.buildStickers(sticker, 1, 10, jsonMap);
			}
		});
	}
	
	@Test
	public void saveStickerTest() throws Exception {
		ZTWorldSticker sticker = new ZTWorldSticker();
		sticker.setStickerPath("http://imzhitu.qiniudn.com/world/sticker/fart-L.png");
		sticker.setStickerThumbPath("http://imzhitu.qiniudn.com/world/sticker/fart-S.png");
		sticker.setTypeId(8);
		sticker.setValid(0);
		sticker.setTopWeight(0);
		sticker.setWeight(1);
		sticker.setHasLock(1);
		sticker.setLabelId(0);
		sticker.setFill(0);
		service.saveSticker(sticker);
	}
	
	@Test
	public void updateStickerTest() throws Exception {
		ZTWorldSticker sticker = new ZTWorldSticker();
		sticker.setStickerPath("http://imzhitu.qiniudn.com/world/sticker/fart-L.png");
		sticker.setStickerThumbPath("http://imzhitu.qiniudn.com/world/sticker/fart-S.png");
		sticker.setTypeId(8);
		sticker.setValid(0);
		sticker.setTopWeight(0);
		sticker.setWeight(1);
		sticker.setId(26);
		service.updateSticker(sticker);
	}
	
	@Test
	public void deleteStickersTest() throws Exception {
		service.deleteStickers("10,12");
	}
	
	@Test
	public void updateStickerCacheTest() throws Exception {
		service.updateStickerCache(new String[]{"1","2"});
	}
}
