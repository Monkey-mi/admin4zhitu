/**
 * 
 */
package com.imzhitu.admin.op.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.ChannelBaseCountDto;
import com.imzhitu.admin.op.mapper.OpChannelBaseCountMapper;
import com.imzhitu.admin.op.service.OpChannelBaseCountService;
import com.imzhitu.admin.privileges.dao.RoleDao;

/**
 * 频道基数管理实现类
 * 
 * @author zhangbo 2015年5月29日
 */
@Service
public class OpChannelBaseCountServiceImpl extends BaseServiceImpl implements OpChannelBaseCountService {

    @Autowired
    private OpChannelBaseCountMapper mapper;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private com.hts.web.operations.service.ChannelService webChannelService;

    @Override
    public void saveChannelBaseCount(ChannelBaseCountDto dto) throws Exception {
	// 若存在，更新，不存在，插入
	if (isExist(dto.getChannelId())) {
	    mapper.updateChannelBaseCount(dto);
	} else {
	    mapper.insertChannelBaseCount(dto);
	}

	webChannelService.updateWorldAndChildCount(dto.getChannelId()); // 更新织图和图片总数
	webChannelService.updateMemberCount(dto.getChannelId()); // 更新频道成员订阅总数
	webChannelService.updateSuperbCount(dto.getChannelId()); // 更新频道加精总数
    }

    @Override
    public void buildChannelBaseCountList(ChannelBaseCountDto dto, Integer page, Integer rows,
	    Map<String, Object> jsonMap) throws Exception {
	buildNumberDtos("getChannelId", dto, page, rows, jsonMap, new NumberDtoListAdapter<ChannelBaseCountDto>() {

	    @Override
	    public List<? extends Serializable> queryList(ChannelBaseCountDto dto) {
		if (roleDao.isSuperOrOpAdmin(dto.getAdminId())) {
		    // 若为super_admin或op_admin权限，则把adminId置空，进行全表查询
		    dto.setAdminId(null);
		}
		List<ChannelBaseCountDto> resultList = mapper.queryChannelBaseCountList(dto);

		// 后台查询出的trueXX字段，为展示在客户端给用户看的数据，真实数据要减去base值，故做下列循环操作，方便运营端查看真实数据
		for (ChannelBaseCountDto cbcDto : resultList) {
		    cbcDto.setTrueWorldCount(cbcDto.getTrueWorldCount() - cbcDto.getWorldBaseCount());
		    cbcDto.setTrueChildCount(cbcDto.getTrueChildCount() - cbcDto.getChildBaseCount());
		    cbcDto.setTrueMemberCount(cbcDto.getTrueMemberCount() - cbcDto.getMemberBaseCount());
		    cbcDto.setTrueSuperbCount(cbcDto.getTrueSuperbCount() - cbcDto.getSuperbBaseCount());
		}
		return resultList;
	    }

	    @Override
	    public long queryTotal(ChannelBaseCountDto dto) {
		if (roleDao.isSuperOrOpAdmin(dto.getAdminId())) {
		    // 若为super_admin或op_admin权限，则把adminId置空，进行全表查询
		    dto.setAdminId(null);
		}
		return mapper.queryChannelBaseCount(dto);
	    }
	});
    }

    @Override
    public void deleteChannelBaseCount(Integer[] channelIds) throws Exception {
	mapper.deleteChannelBaseCountByChannelIds(channelIds);

	for (Integer channelId : channelIds) {
	    webChannelService.updateWorldAndChildCount(channelId); // 更新织图和图片总数
	    webChannelService.updateMemberCount(channelId); // 更新频道成员订阅总数
	    webChannelService.updateSuperbCount(channelId); // 更新频道加精总数
	}
    }

    /**
     * 判断是否存在于频道基数管理表
     *
     * @param channelId
     *            频道id
     * @return
     * @throws Exception
     * @author zhangbo 2015年6月1日
     */
    private boolean isExist(Integer channelId) throws Exception {
	long resultCount = mapper.isExistChannelBaseCount(channelId);
	if (resultCount == 0) {
	    return false;
	} else {
	    return true;
	}
    }

}
