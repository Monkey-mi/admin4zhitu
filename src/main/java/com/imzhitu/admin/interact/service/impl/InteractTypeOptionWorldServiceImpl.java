package com.imzhitu.admin.interact.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hts.web.base.constant.Tag;
import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.InteractTypeOptionWorldDto;
import com.imzhitu.admin.common.pojo.OpChannelWorld;
import com.imzhitu.admin.common.pojo.UserLevelListDto;
import com.imzhitu.admin.common.pojo.UserTrust;
import com.imzhitu.admin.common.pojo.ZTWorldTypeWorldDto;
import com.imzhitu.admin.interact.dao.InteractUserlevelListDao;
import com.imzhitu.admin.interact.mapper.InteractTypeOptionWorldMapper;
import com.imzhitu.admin.interact.service.InteractTypeOptionWorldService;
import com.imzhitu.admin.interact.service.InteractWorldlevelListService;
import com.imzhitu.admin.op.mapper.ChannelWorldMapper;
import com.imzhitu.admin.userinfo.dao.UserTrustDao;
import com.imzhitu.admin.ztworld.mapper.ZTWorldTypeWorldMapper;
import com.imzhitu.admin.ztworld.service.ZTWorldTypeService;
import com.imzhitu.admin.ztworld.service.ZTWorldTypeWorldSchedulaService;

@Service("com.imzhitu.admin.interact.service.impl.InteractTypeOptionWorldServiceImpl")
public class InteractTypeOptionWorldServiceImpl  extends BaseServiceImpl implements InteractTypeOptionWorldService{
	@Autowired
	InteractTypeOptionWorldMapper typeOptionWorldMapper;
	@Autowired
	private UserTrustDao userTrustDao;
	@Autowired
	private ChannelWorldMapper channelWorldMapper;
	@Autowired
	private InteractUserlevelListDao interactUserlevelListDao;
	@Autowired
	private InteractWorldlevelListService interactWorldlevelListService;
	@Autowired
	private ZTWorldTypeService worldTypeService;
	@Autowired
	private ZTWorldTypeWorldSchedulaService typeWorldSchedulaService;
	@Autowired
	private ZTWorldTypeWorldMapper typeWorldMapper;
	
	@Value("${urlPrefix}")
	private String urlPrefix;
	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}
	
	private Logger logger = Logger.getLogger(InteractTypeOptionWorldServiceImpl.class);
	
	public static Integer workTime = 30*60*1000;
	
	@Override
	public void insertTypeOptionWorld(Integer worldId,Integer userId,Integer valid,Integer superb,Integer operatorId)throws Exception{
		Date now = new Date();
		InteractTypeOptionWorldDto dto = new InteractTypeOptionWorldDto();
		dto.setWorldId(worldId);
		dto.setUserId(userId);
		boolean exist = isExist(worldId);
		if (exist) {
			return;
		} else {
			dto.setValid(valid);
			dto.setSuperb(superb);
			dto.setOperatorId(operatorId);
			dto.setAddDate(now);
			dto.setModifyDate(now);
			typeOptionWorldMapper.insertTypeOptionWorld(dto);
		}
	}
	
	public void delTypeOptionWorldByIds(String idsStr)throws Exception{
		Integer[] ids = StringUtil.convertStringToIds(idsStr);
		typeOptionWorldMapper.delTypeOptionWorldByIds(ids);
	}
	
	public void delTypeOptionWorldByWIds(String widsStr)throws Exception{
		Integer[] wids = StringUtil.convertStringToIds(widsStr);
		typeOptionWorldMapper.delTypeOptionWorldByWIds(wids);
	}
	
	public void updateTypeOptionWorld(Integer id,Integer worldId,Integer userId,Integer valid,Integer superb,Integer top,Integer operatorId)throws Exception{
		Date now = new Date();
		InteractTypeOptionWorldDto dto = new InteractTypeOptionWorldDto();
		dto.setId(id);
		dto.setWorldId(worldId);
		dto.setUserId(userId);
		dto.setValid(valid);
		dto.setSuperb(superb);
		dto.setOperatorId(operatorId);
		dto.setModifyDate(now);
		dto.setTop(top);
		typeOptionWorldMapper.updateTypeOptionWorld(dto);
	}
	
	public void queryTypeOptionWorldForList(Integer maxId, int page, int rows,
			Integer id,Integer worldId,Integer userId,Integer valid,Integer superb,Integer top,Map<String, Object> jsonMap) throws Exception{
		Date now = new Date();
		InteractTypeOptionWorldDto dto = new InteractTypeOptionWorldDto();
		dto.setModifyDate(now);
		dto.setAddDate(new Date(now.getTime()-4*24*60*60*1000));
		dto.setId(id);
		dto.setWorldId(worldId);
		dto.setUserId(userId);
		dto.setValid(valid);
		dto.setSuperb(superb);
		dto.setTop(top);
		buildNumberDtos(dto,page,rows,jsonMap,new NumberDtoListAdapter<InteractTypeOptionWorldDto>(){
			@Override
			public long queryTotal(InteractTypeOptionWorldDto dto){
				return typeOptionWorldMapper.queryTypeOptionWorldCount(dto);
			}
			
			@Override
			public List< ? extends AbstractNumberDto> queryList(InteractTypeOptionWorldDto dto){
				List<InteractTypeOptionWorldDto> list = typeOptionWorldMapper.queryTypeOptionWorldForList(dto);
				for(InteractTypeOptionWorldDto o:list){
					//shortLink
//					o.setShortLink(urlPrefix+o.getShortLink());
					
					//查询每个用户的权限
					UserLevelListDto userlevel = interactUserlevelListDao.QueryUserlevelByUserId(o.getUserId());
					if(userlevel != null){
						o.setUserLevel(userlevel.getId());
						o.setUserLevelDesc(userlevel.getLevel_description());
					}
					try{
						if(interactWorldlevelListService.chechWorldLevelListIsExistByWId(dto.getWorldId())){
							dto.setTypeInteracted(1);
						}else{
							dto.setTypeInteracted(0);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					
					//查询信任操作人
					UserTrust userTrust = userTrustDao.queryUserTrustByUid(o.getUserId());
					if(userTrust != null){
						o.setTrustModifyDate(userTrust.getModifyDate());
						o.setTrustOperatorId(userTrust.getOperatorId());
						o.setTrustOperatorName(userTrust.getOperatorName());
					}else{
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						try{
							Date trustModifyDate = df.parse("2011-08-20 00:00:00");
							o.setTrustModifyDate(trustModifyDate);
							o.setTrustOperatorId(0);
							o.setTrustOperatorName("暂无");
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					
					//查询频道信息
					OpChannelWorld world = new OpChannelWorld();
					world.setWorldId(o.getWorldId());
//					world.setValid(Tag.TRUE);
					long r = channelWorldMapper.queryChannelWorldCount(world);
					if(r == 0)
						o.setChannelName("NO_EXIST");
					else {
						List<String>strList = channelWorldMapper.queryChannelNameByWorldId(o.getWorldId());
						o.setChannelName(strList.toString());
					}
				}
				return list;
			}
		});
	}
	
	@Override
	public void autoAddStarWorld()throws Exception{
		// TODO 这个可以移到scheduler包中执行
		Date now = new Date();
		logger.info("自动添加明星用户发的图到广场备选列表中。开始时间为：" + now);
		InteractTypeOptionWorldDto dto = new InteractTypeOptionWorldDto();
		dto.setModifyDate(now);
		dto.setAddDate(new Date(now.getTime()-workTime));
		List<InteractTypeOptionWorldDto> list = typeOptionWorldMapper.queryStarWorld(dto);
		for(InteractTypeOptionWorldDto o:list){
			insertTypeOptionWorld(o.getWorldId(),o.getUserId(),Tag.TRUE,Tag.FALSE,0);
		}
		Date end = new Date();
		logger.info("自动添加明星用户发的图到广场备选列表中结束。共添加了："+(list==null?0:list.size()) +"个。花费时间为：" + (end.getTime()-now.getTime()));
	}
	
	/**
	 * 重新排序
	 * @param wids
	 * @param schedula
	 * @param operatorId
	 * @throws Exception
	 */
	@Override
	public void reSort(String[] wids,Date schedula,Integer operatorId)throws Exception{
		ZTWorldTypeWorldDto worldDto = new ZTWorldTypeWorldDto();
		for(int i= wids.length -1;i >= 0; i--){
			String wid = wids[i];
			if(wid != null && wid != ""){
				int worldId = Integer.parseInt(wids[i]);
				long t = schedula.getTime() - i*1000;//用以排序
				try{
					InteractTypeOptionWorldDto dto = queryTypeOptionWorld(worldId);
					worldTypeService.saveTypeWorld(worldId, 5, "其他", operatorId);
					worldTypeService.updateTypeWorldReview(worldId, dto.getReView());
					typeWorldSchedulaService.addTypeWorldSchedula(worldId, new Date(t),operatorId, 0);
					worldDto.setWorldId(worldId);
					worldDto.setIsSorted(1);
					typeWorldMapper.updateTypeWorld(worldDto);
					typeOptionWorldMapper.delTypeOptionWorldByWid(worldId);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 修改精选点评
	 * @param dto
	 */
	@Override
	public void updateReview(Integer worldId,String review)throws Exception{
		InteractTypeOptionWorldDto dto = new InteractTypeOptionWorldDto();
		dto.setWorldId(worldId);
		dto.setReView(review);
		typeOptionWorldMapper.updateReview(dto);
	}
	
	/**
	 * 查询某个精选备选
	 * @param dto
	 * @return
	 */
	@Override
	public InteractTypeOptionWorldDto queryTypeOptionWorld(Integer worldId)throws Exception{
		InteractTypeOptionWorldDto dto = new InteractTypeOptionWorldDto();
		dto.setWorldId(worldId);
		return typeOptionWorldMapper.queryTypeOptionWorld(dto);
	}

	@Override
	public boolean isExist(Integer worldId) throws Exception {
		InteractTypeOptionWorldDto dto = new InteractTypeOptionWorldDto();
		dto.setWorldId(worldId);
		long r = typeOptionWorldMapper.chechIsExist(dto);
		if ( r > 0 ) {
			return true;
		} else {
			return false;
		}
	}
}
