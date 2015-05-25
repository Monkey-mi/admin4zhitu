/**
 * 
 */
package com.imzhitu.admin.op.service.impl;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.OpWorldOfCannelDto;
import com.imzhitu.admin.op.mapper.OpWorldOfCannelMapper;
import com.imzhitu.admin.op.service.OpWorldOfCannelService;

/**
 * 织图在频道中相关联操作的业务逻辑实现
 * 
 * @author zhangbo 2015年5月19日
 */
@Service
public class OpWorldOfCannelServiceImpl extends BaseServiceImpl implements
	OpWorldOfCannelService {
    
    @Autowired
    private OpWorldOfCannelMapper opWorldOfCannelServiceMapper;

    @Override
    public void setTopToCache(Integer id, Integer channelId, Integer worldId, boolean isTop) throws Exception {
	// 定义DTO，并设置id
	OpWorldOfCannelDto dto = new OpWorldOfCannelDto();
	dto.setId(id);
	dto.setChannelId(channelId);
	dto.setWorldId(worldId);
	
	// 若置顶，weight设置为1，不置顶，设置为0
	if (isTop) {
	    dto.setWeight(1);
	} else {
	    dto.setWeight(0);
	}
	
	setDataToCache(dto);
    }

    @Override
    public void setSuperbById(Integer id, Integer channelId, Integer worldId, boolean isSuperb) throws Exception {
	// 定义DTO，并设置id
	OpWorldOfCannelDto dto = new OpWorldOfCannelDto();
	dto.setId(id);
		
	// 若加精，superb设置为1，不置顶，设置为0
	if (isSuperb) {
	    dto.setSuperb(1);
	} else {
	    dto.setSuperb(0);
	}
	dto.setChannelId(channelId);
	dto.setWorldId(worldId);
		
	setDataToCache(dto);
    }
    
    @Override
    public void setValidById(Integer id, boolean isValid) throws Exception {
	// 定义DTO，并设置id
	OpWorldOfCannelDto dto = new OpWorldOfCannelDto();
	dto.setId(id);
	
	// 若加精，superb设置为1，不置顶，设置为0
	if (isValid) {
	    dto.setValid(1);
	} else {
	    dto.setValid(0);
	}
	opWorldOfCannelServiceMapper.setValidById(dto);
    }

    @Override
    public void deleteById(Integer id) throws Exception {
	opWorldOfCannelServiceMapper.deleteById(id);
    }

    @Override
    public void syncDataFromCache(Integer channelId) throws Exception {
	// 根据频道id，从cache表中查询出当前频道下已经操作过的数据
	List<OpWorldOfCannelDto> cacheList = opWorldOfCannelServiceMapper.queryFromCacheByCannelId(channelId);
	
	// 定义要删除的id集合
	Integer[] ids = new Integer[cacheList.size()];
	
	/*
	 *  本循环做两个事情：
	 *  1、循环刷新到正式表	
	 *  2、获取要删除的id集
	 */
	for (int i = 0; i < cacheList.size(); i++) {
	    opWorldOfCannelServiceMapper.syncDataToMainTable(cacheList.get(i));
	    
	    ids[i] = cacheList.get(i).getId();
	}
	
	// 刷新到正式表后，cache表中对当前频道下的操作过数据删除
	opWorldOfCannelServiceMapper.deleteByIds(ids);
	
    }
    
    /**
     * 通过DTO往中间表设值
     *
     * @param dto	织图与频道关联表数据传输对象
     * @throws Exception
     * @author zhangbo 2015年5月22日
     */
    private void setDataToCache(OpWorldOfCannelDto dto) throws Exception {
	// 根据查询要操作的数据，在中间表中是否存在
	OpWorldOfCannelDto queryResultDto = opWorldOfCannelServiceMapper.queryFromCacheById(dto.getId());
		
	// 若查询为空，则证明中间表中不存在，要执行插入中间表数据，否则执行更新
	if ( queryResultDto == null) {
	    opWorldOfCannelServiceMapper.insertDataToCacheByDto(dto);
	} else {
	    opWorldOfCannelServiceMapper.updateDataToCacheByDto(dto);
	}
    }

}
