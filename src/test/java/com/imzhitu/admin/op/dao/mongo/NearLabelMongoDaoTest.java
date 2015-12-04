package com.imzhitu.admin.op.dao.mongo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.common.pojo.OpNearLabelDto;
import com.imzhitu.admin.base.BaseTest;

/**
 * 附近标签Mongo数据访问接口
 * 
 * @author lynch 2015-12-03
 *
 */
public class NearLabelMongoDaoTest extends BaseTest {
	
	@Autowired
	private NearLabelMongoDao dao;
	
	@Test
	public void saveLabelTest() throws Exception {
		OpNearLabelDto dto = new OpNearLabelDto();
		dto.setId(2);
		dto.setDescription("描述测试");
		dto.setLabelName("名称");
		dto.setLoc(new Double[]{113.937538, 22.539017});
		dto.setSerial(1);
		dao.saveLabel(dto);
		dao.deleteById(2);
	}
	
	@Test
	public void updateLabelTest() throws Exception {
		OpNearLabelDto dto = new OpNearLabelDto();
		dto.setId(1);
		dto.setDescription("描述测试哈哈");
		dto.setLabelName("名称1");
		dto.setLoc(new Double[]{113.937538, 22.539017});
		dto.setSerial(1);
		dao.updateLabel(dto);
	}
	
	@Test
	public void deleteByIdsTest() throws Exception {
		dao.deleteByIds(new Integer[]{2,3});
	}
	
	@Test
	public void updateSerialTest() throws Exception {
		dao.updateSerial(1, 20);
	}
	
}
