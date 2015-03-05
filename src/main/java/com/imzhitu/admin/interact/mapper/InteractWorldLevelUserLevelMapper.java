package com.imzhitu.admin.interact.mapper;

import java.util.List;

import com.imzhitu.admin.common.pojo.InteractWorldLevelUserLevel;

public interface InteractWorldLevelUserLevelMapper {
	public List<InteractWorldLevelUserLevel> queryWorldLevelUserLevel(InteractWorldLevelUserLevel dto);
	public long queryWorldLevelUserLevelTotal(InteractWorldLevelUserLevel dto);
	public InteractWorldLevelUserLevel queryWorldLevelUserLevelByUid(InteractWorldLevelUserLevel dto);
	public void addWorldLevelUserLevel(InteractWorldLevelUserLevel  dto);
	public void delWorldLevelUserLevelById(Integer id);
}
