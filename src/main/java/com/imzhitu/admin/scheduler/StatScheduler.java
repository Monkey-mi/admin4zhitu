package com.imzhitu.admin.scheduler;

import java.util.Calendar;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.statistics.dao.PVCacheDao;
import com.imzhitu.admin.statistics.mapper.PvMapper;
import com.imzhitu.admin.statistics.pojo.StatPvDto;

/**
 * 统计计划
 * 
 * @author lynch
 *
 */
public class StatScheduler {

	private static Logger log = Logger.getLogger(StatScheduler.class);
	
	@Autowired
	private PVCacheDao pvCacheDao;

	@Autowired
	private PvMapper pvMapper;
	
	/**
	 * 更新PV到数据库
	 */
	public void refreshPv() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		
		long now = ca.getTimeInMillis();
		Set<String> keies = pvCacheDao.queryAllPvKey();
		
		StatPvDto pv;
		Integer pvid;
		for(String key : keies) {
			pv = pvCacheDao.getPvDtoByKey(key);
			pv.setPv(pvCacheDao.getPv(key));
			pv.setPvtime(now);
			
			pvCacheDao.clearPv(pv.getPvkey(), pv.getSubkey());
			
			try {
				if((pvid = pvMapper.queryPvId(pv.getPvkey(), pv.getSubkey(), pv.getPvtime())) == null)
					pvMapper.savePv(pv);
				else
					pvMapper.addPvById(pvid, pv.getPv());
			} catch(Exception e) {
				log.warn("save or update pv error, " + e.getMessage(), e);
				// 保存数据回滚到缓存
				pvCacheDao.incPv(pv.getPvkey(), pv.getSubkey(), pv.getPv());
			}
			
		}
	}
	
}
