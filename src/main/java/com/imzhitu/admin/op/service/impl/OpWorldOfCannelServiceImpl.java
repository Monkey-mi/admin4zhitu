/**
 * 
 */
package com.imzhitu.admin.op.service.impl;

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
    public void setTopById(Integer id, boolean isTop) throws Exception {
	OpWorldOfCannelDto dto = new OpWorldOfCannelDto();
	dto.setId(id);
	
	// 若置顶，weight设置为1，不置顶，设置为0
	if (isTop) {
	    dto.setWeight(1);
	} else {
	    dto.setWeight(0);
	}
	
	opWorldOfCannelServiceMapper.setTopByDto(dto);
    }

    @Override
    public void setSuperbById(Integer id, boolean isSuperb) throws Exception {
	OpWorldOfCannelDto dto = new OpWorldOfCannelDto();
	dto.setId(id);
	
	// 若加精，superb设置为1，不置顶，设置为0
	if (isSuperb) {
	    dto.setSuperb(1);
	} else {
	    dto.setSuperb(0);
	}
		
	opWorldOfCannelServiceMapper.setSuperbByDto(dto);
    }

    @Override
    public void deleteById(Integer id) throws Exception {
	opWorldOfCannelServiceMapper.deleteById(id);
    }

}
