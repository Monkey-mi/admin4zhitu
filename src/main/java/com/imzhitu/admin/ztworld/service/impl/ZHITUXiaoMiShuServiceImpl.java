package com.imzhitu.admin.ztworld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.ZTWorldDto;
import com.imzhitu.admin.ztworld.dao.ZHITUXiaoMiShuDao;
import com.imzhitu.admin.ztworld.service.ZHITUXiaoMiShuService;

@Service
public class ZHITUXiaoMiShuServiceImpl extends BaseServiceImpl implements
		ZHITUXiaoMiShuService {

	@Value("${push.customerServiceId}")
	private Integer authorId;
	
	@Autowired
	private ZHITUXiaoMiShuDao xiaoMiShuDao;
	
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Integer getAuthorId() {
		return this.authorId;
	}

	@Override
	public void delZTWorldByIds(String idsStr) throws Exception {
		if (idsStr == null || idsStr.equals(""))
			return;
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		xiaoMiShuDao.delZTWorldByIds(ids);
	}

	@Override
	public void updateWorldDescByWid(Integer wid, String worldDesc)
			throws Exception {
		if (wid == null || worldDesc == null)
			return;
		xiaoMiShuDao.updateZTWorldDesc(wid, worldDesc);
	}

	@Override
	public void updateWorldLabelByWid(Integer wid, String worldLable)
			throws Exception {
		xiaoMiShuDao.updateWorldLabel(wid, worldLable);
	}

}
