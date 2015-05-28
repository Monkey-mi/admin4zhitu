/**
 * 
 */
package com.imzhitu.admin.op.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.common.pojo.ChannelWorldRecyclerDto;
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.common.pojo.OpWorldOfCannelDto;
import com.imzhitu.admin.op.mapper.ChannelWorldMapper;
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

    @Autowired
    private ChannelWorldMapper channelWorldMapper;

    @Override
    public void setTopToCache(Integer id, Integer channelId, Integer worldId,
	    boolean isTop) throws Exception {
	// 定义DTO，并设置id
	OpWorldOfCannelDto dto = new OpWorldOfCannelDto();
	dto.setId(id);
	dto.setChannelId(channelId);
	dto.setWorldId(worldId);
	// 因为操作的数据都是有效的，所以设置有效值为1
	dto.setValid(1);

	// 若置顶，weight设置为1，不置顶，设置为0
	if (isTop) {
	    dto.setWeight(1);
	} else {
	    dto.setWeight(0);
	}

	setDataToCache(dto);
    }

    @Override
    public void setSuperbById(Integer id, Integer channelId, Integer worldId,
	    boolean isSuperb) throws Exception {
	// 定义DTO，并设置id
	OpWorldOfCannelDto dto = new OpWorldOfCannelDto();
	dto.setId(id);
	dto.setChannelId(channelId);
	dto.setWorldId(worldId);
	// 因为操作的数据都是有效的，所以设置有效值为1
	dto.setValid(1);

	// 若加精，superb设置为1，不置顶，设置为0
	if (isSuperb) {
	    dto.setSuperb(1);
	} else {
	    dto.setSuperb(0);
	}

	setDataToCache(dto);
    }

    @Override
    public void deleteByIdsFromCache(Integer[] ids) throws Exception {
	opWorldOfCannelServiceMapper.deleteByIdsFromCache(ids);
    }

    @Override
    public void syncDataFromCache(Integer channelId) throws Exception {
	// 根据频道id，从cache表中查询出当前频道下已经操作过的数据
	List<OpWorldOfCannelDto> cacheList = opWorldOfCannelServiceMapper
		.queryFromCacheByCannelId(channelId);

	if (!cacheList.isEmpty()) {
	    // 定义要删除的id集合
	    Integer[] ids = new Integer[cacheList.size()];

	    /*
	     * 本循环做两个事情： 1、循环刷新到正式表 2、获取要删除的id集
	     */
	    for (int i = 0; i < cacheList.size(); i++) {
		OpChannelWorld world = new OpChannelWorld();
		world.setId(cacheList.get(i).getId());
		world.setWeight(cacheList.get(i).getWeight());
		world.setSuperb(cacheList.get(i).getSuperb());
		channelWorldMapper.update(world);

		ids[i] = cacheList.get(i).getId();
	    }

	    // 刷新到正式表后，cache表中对当前频道下的操作过数据删除
	    opWorldOfCannelServiceMapper.deleteByIdsFromCache(ids);
	}
    }

    public void setDataToCache(OpWorldOfCannelDto dto) throws Exception {
	// 根据查询要操作的数据，在中间表中是否存在
	OpWorldOfCannelDto queryResultDto = opWorldOfCannelServiceMapper
		.queryFromCacheById(dto.getId());

	// 若查询为空，则证明中间表中不存在，要执行插入中间表数据，否则执行更新
	if (queryResultDto == null) {
	    opWorldOfCannelServiceMapper.insertDataToCacheByDto(dto);
	} else {
	    opWorldOfCannelServiceMapper.updateDataToCacheByDto(dto);
	}
    }

    @Override
    public void buildRecyclerList(ChannelWorldRecyclerDto dto, Integer page,
	    Integer rows, Map<String, Object> jsonMap) throws Exception {
	buildNumberDtos("getChannelWorldId", dto, page, rows, jsonMap,
		new NumberDtoListAdapter<ChannelWorldRecyclerDto>() {
		    @Override
		    public long queryTotal(ChannelWorldRecyclerDto dto) {
			return opWorldOfCannelServiceMapper
				.queryRecyclerCountByDTO(dto);
		    }

		    @Override
		    public List<? extends AbstractNumberDto> queryList(
			    ChannelWorldRecyclerDto dto) {
			return opWorldOfCannelServiceMapper
				.queryRecyclerByDTO(dto);
		    }
		});
    }

}
