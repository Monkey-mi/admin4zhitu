package com.imzhitu.admin.op.mapper;

import org.apache.ibatis.annotations.Param;

import com.imzhitu.admin.common.dataSourceMasterSlave.DataSource;
import com.imzhitu.admin.common.pojo.NoticeMsgTemplate;

/**
 * 通知信息模板数据接口 
 * 作为持久层操作，对数据库进行的一系列操作
 * 
 * @author zhangbo 2015年9月2日
 *
 */
public interface NoticeMsgTemplateMapper {

	/**
	 * 插入数据到通知信息模板表
	 * 保存两个内容：
	 * 1、通知信息内容模板	noticeMsgTmpl.contentTmpl
	 * 2、操作者			noticeMsgTmpl.operator
	 * 
	 * @param NoticeMsgTemplate
	 *            通知信息模板对象
	 * @author zhangbo 2015年9月2日
	 * @return 通知信息模板对象主键id	noticeMsgTmpl.id
	 */
	@DataSource("master")
	public Integer insertNoticeMsgTemplate(NoticeMsgTemplate noticeMsgTmpl);

	/**
	 * 更新通知信息模板表数据
	 * 
	 * @param id
	 *            通知信息模板表主键id
	 * @param contentTmpl
	 *            通知信息内容模板
	 * @param operator
	 *            操作者，存储的为管理员的主键id
	 * @author zhangbo 2015年9月2日
	 */
	@DataSource("master")
	public void updateNoticeMsgTemplate(NoticeMsgTemplate noticeMsgTmpl);
	
	/**
	 * 根据通知信息内容模板获取对应的主键id
	 * 
	 * @param contentTemplate	通知信息内容模板
	 * @return	id	通知信息模板表主键id
	 * @author zhangbo	2015年9月6日
	 */
	@DataSource("slave")
	public Integer getContentTmplId(NoticeMsgTemplate noticeMsgTmpl);

	/**
	 * 根据id获取通知信息模板内容
	 * 
	 * @param tmplId	通知信息模板表主键id	
	 * @return
	 * @author zhangbo	2015年9月6日
	 */
	public String getContentTmplById(@Param("id")Integer tmplId);

}
