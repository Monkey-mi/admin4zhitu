package com.imzhitu.admin.interact.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.InteractCommentLabel;


public interface InteractCommentLabelMapper {
	@DataSource("master")
	public List<InteractCommentLabel> selectInteractCommentLabel() throws Exception;
}
