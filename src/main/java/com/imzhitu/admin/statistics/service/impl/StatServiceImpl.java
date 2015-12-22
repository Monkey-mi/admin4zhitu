package com.imzhitu.admin.statistics.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.stat.StatKey;
import com.hts.web.stat.StatKeyRemark;
import com.imzhitu.admin.statistics.dao.PVCacheDao;
import com.imzhitu.admin.statistics.mapper.PvMapper;
import com.imzhitu.admin.statistics.pojo.StatPvDto;
import com.imzhitu.admin.statistics.service.StatService;

@Service
public class StatServiceImpl extends BaseServiceImpl implements StatService {

	@Autowired
	private PVCacheDao pvCacheDao;

	@Autowired
	private PvMapper pvMapper;

	@Override
	public void buildPV(StatPvDto pv, String beginDateStr, String endDateStr,
			Integer start, Integer limit, Map<String, Object> jsonMap) throws Exception {

		if(beginDateStr != null && !beginDateStr.equals(""))
			pv.setBegintime(getTimeByDateStr(beginDateStr));
		
		if(endDateStr != null && !endDateStr.equals(""))
			pv.setEndtime(getTimeByDateStr(endDateStr));

		buildNumberDtos(pv, start, limit, jsonMap, new NumberDtoListAdapter<StatPvDto>() {

			@Override
			public List<? extends Serializable> queryList(StatPvDto dto) {
				List<StatPvDto> list = pvMapper.queryList(dto);
//				pvCacheDao.addReadTimePv(list);
				setKeyName(list);
				return list;
			}

			@Override
			public long queryTotal(StatPvDto dto) {
				return pvMapper.queryTotal(dto);
			}
		});
	}
	
	private long getTimeByDateStr(String dateStr) throws ParseException {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = fm.parse(dateStr + " 00:00:00");
		return d.getTime();
	}
	
	/**
	 * 设置统计名称
	 * 
	 * @param list
	 * @throws Exception 
	 */
	private void setKeyName(List<StatPvDto> list) {
		Map<Integer, String> nameMap = null;
		try {
			nameMap = getAllStatKeyName();
		} catch(Exception e) {
		}
		
		for(StatPvDto pv : list) {
			pv.setKeyname(nameMap.get(pv.getPvkey()));
		}
		
	}
	
	/**
	 * 获取所有统计键
	 * 
	 * @return
	 */
	public Map<Integer, String> getAllStatKeyName() throws Exception {
		Map<Integer, String> map = new HashMap<Integer, String>();
		Class<StatKey> c = StatKey.class;
		Field[] field = c.getDeclaredFields();
		for(int i = 0; i < field.length; i++) {
			map.put((Integer)field[i].get(c), 
					field[i].getAnnotation(StatKeyRemark.class).name());
		}
		return map;
	}

	@Override
	public List<StatPvDto> getStatKeyNameList() throws Exception {
		Map<Integer, String> keyMap = getAllStatKeyName();
		List<StatPvDto> list = new ArrayList<StatPvDto>();
		StatPvDto dto;
		for(Entry<Integer, String> entry : keyMap.entrySet()) {
			dto = new StatPvDto();
			dto.setPvkey(entry.getKey());
			dto.setKeyname(entry.getValue());
			list.add(dto);
		}
		return list;
	}

}
