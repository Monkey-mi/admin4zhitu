package com.imzhitu.admin.interact.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hts.web.base.constant.Tag;
import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.InteractTracker;

/**
 * <p>
 * 互动跟踪数据访问接口单元测试
 * </p>
 * 
 * 创建时间：2014-2-27
 * @author tianjie
 *
 */
public class InteractTrackerDaoTest extends BaseTest {

	@Autowired
	private InteractTrackerDao dao;
	
	@Test
	public void testSaveTracker() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = format.parse("2014-02-27 00:00:00");
		dao.saveTrack(new InteractTracker(1, "粉丝互动", 
				10, 9, 24, d, new Date(), Tag.TRUE));
		
		
	}
	
}
