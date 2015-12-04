package com.imzhitu.admin.op.dao.mongo;

import com.hts.web.common.dao.BaseMongoDao;
import com.hts.web.common.pojo.OpNearLabelDto;

/**
 * 附近标签信息MongoDB数据访问接口
 * 
 * @author lynch 2015-12-03
 *
 */
public interface NearLabelMongoDao extends BaseMongoDao {
	
	/**
	 * 保存标签, 必须设置loc字段
	 * 
	 * @param label
	 */
	public void saveLabel(OpNearLabelDto label);
	
	/**
	 * 根据id删除标签
	 * 
	 * @param id
	 */
	public void deleteById(Integer id);

	/**
	 * 更新标签
	 * 
	 * @param label
	 */
	public void updateLabel(OpNearLabelDto label);
	
	/**
	 * 更新序号
	 * 
	 * @param id
	 * @param serial
	 */
	public void updateSerial(Integer id, Integer serial);

}
