package com.imzhitu.admin.op.dao.mongo;

import java.util.List;

import com.hts.web.common.dao.BaseMongoDao;
import com.imzhitu.admin.op.pojo.NearBulletinCityDto;
import com.imzhitu.admin.op.pojo.NearBulletinDto;

/**
 * 附近公告数据访问接口
 * 
 * @author lynch 2015-12-14
 *
 */
public interface NearBulletinMongoDao extends BaseMongoDao {

	/**
	 * @param bulletin
	 * @return
	 *  @author lynch 2015-12-15
	 */
	public List<NearBulletinDto> queryBulletin(NearBulletinDto bulletin);
	
	
	/**
	 * 查询总数
	 * 
	 * @param bulletin
	 * @return
	 */
	public long queryCount(NearBulletinDto bulletin);
	
	/**
	 * 保存附近公告
	 * 
	 * @param bulletin
	 * @return
	 * @author lynch 2015-12-15
	 */
	public void save(NearBulletinDto bulletin);
	
	/**
	 * 根据城市id更新公告信息
	 * 
	 * @param bulletin
	 * @author lynch 2015-12-15
	 */
	public void update(NearBulletinDto bulletin);

	/**
	 * 根据id批量删除
	 * 
	 * @param ids
	 * @author lynch 2015-12-15
	 */
	public void delByIds(Integer[] ids);
	
	/**
	 * 更新附近公告排序
	 * 
	 * @param id
	 * @param serial
	 * @author lynch 2015-12-15
	 */
	public void updateSerial(Integer id, Integer serial);
	
	/**
	 * 根据系统公告id批量删除
	 * 
	 * @param bulletinId
	 * @author lynch 2015-12-15
	 */
	public void delByBulletinId(Integer bulletinId);
	
	/**
	 * 此接口只返回城市信息
	 * 
	 * @param bulletinIds
	 * @return
	 */
	public List<NearBulletinCityDto> queryCityByBulletinIds(Integer[] bulletinIds);
}
