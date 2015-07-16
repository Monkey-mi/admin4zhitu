package com.imzhitu.admin.ztworld.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.ZTWorldCommentDto;
import com.imzhitu.admin.ztworld.mapper.ADCommentMapper;
import com.imzhitu.admin.ztworld.service.ADCommentService;

/**
 * @author zhangbo 2015年7月14日
 *
 */
@Service
public class ADCommentServiceImpl extends BaseServiceImpl implements ADCommentService {

    @Autowired
    private ADCommentMapper mapper;

    @Override
    public void queryADCommentList(ZTWorldCommentDto dto, Integer page, Integer rows, Map<String, Object> jsonMap)
	    throws Exception {
	buildNumberDtos(dto, page, rows, jsonMap, new NumberDtoListAdapter<ZTWorldCommentDto>() {
	    
	    @Override
	    public long queryTotal(ZTWorldCommentDto dto) {
		return mapper.queryADCommentTotalCount(dto);
	    }

	    @Override
	    public List<? extends AbstractNumberDto> queryList(ZTWorldCommentDto dto) {
		return mapper.queryADComment(dto);
	    }

	});
    }

    @Override
    public void updateADComment(ZTWorldCommentDto dto) throws Exception {
	mapper.updateADComment(dto);
    }

    @Override
    public void deleteADCommentByIds(Integer[] toIds) {
	mapper.deleteADCommentByIds(toIds);
    }

}
