package com.imzhitu.admin.interact.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hts.web.common.pojo.AbstractNumberDto;
import com.hts.web.common.service.impl.BaseServiceImpl;
import com.hts.web.common.util.StringUtil;
import com.imzhitu.admin.common.pojo.InteractRefreshZombieHtworld;
import com.imzhitu.admin.common.pojo.UserZombieHtworld;
import com.imzhitu.admin.interact.mapper.InteractRefreshZombieHtworldMapper;
import com.imzhitu.admin.interact.service.InteractRefreshZombieHtWorldService;

@Service
public class InteractRefreshZombieHtWorldServiceImpl extends BaseServiceImpl implements InteractRefreshZombieHtWorldService{
	
	@Autowired
	InteractRefreshZombieHtworldMapper refreshZombieHtworlMapper;
	
	@Value("${urlPrefix}")
	private String linkPre;
	
	public String getLinkPre() {
		return linkPre;
	}

	public void setLinkPre(String linkPre) {
		this.linkPre = linkPre;
	}

	@Override
	public void queryZombieHtworldList(Integer days,int page,int row ,Map<String,Object> jsonMap)throws Exception{
		
		UserZombieHtworld userZombieHtworld = new UserZombieHtworld();
		if(days != null && days > 0 && days < 360){
			Date now = new Date();
			Calendar rightnow = Calendar.getInstance(Locale.CHINA);
			rightnow.setTime(now);
			rightnow.set(Calendar.DATE, rightnow.get(Calendar.DATE) - days);
			userZombieHtworld.setAfterDate(new Date(rightnow.getTimeInMillis()));
		}
		
		
		buildNumberDtos("getWorldId",userZombieHtworld,page,row,jsonMap,new NumberDtoListAdapter<UserZombieHtworld>(){
			@Override
			public long queryTotal(UserZombieHtworld dto){
				return refreshZombieHtworlMapper.queryZombieHtworldTotalByMaxDate(dto);
			}
			
			@Override
			public List< ? extends AbstractNumberDto> queryList(UserZombieHtworld dto){
				List<UserZombieHtworld> list = refreshZombieHtworlMapper.queryZombieHtworld(dto);
				for(UserZombieHtworld o:list){
					o.setShortLink(linkPre+o.getShortLink());
				}
				return list;
			}
		});
	}
	
	@Override
	public void refreshWorldCreateDate(Integer[] ids,Date refreshDate)throws Exception{
		for(int i=0; i< ids.length; i++){
			InteractRefreshZombieHtworld dto = new InteractRefreshZombieHtworld();
			dto.setWid(ids[i]);
			dto.setRefreshDate(refreshDate);
			refreshZombieHtworlMapper.refreshWorldCreateDate(dto);
		}
	}
	
	@Override
	public void refreshCommentCreateDate(Integer[] ids,Date refreshDate)throws Exception{
		for(int i=0 ; i< ids.length; i++){
			InteractRefreshZombieHtworld dto = new InteractRefreshZombieHtworld();
			dto.setCid(ids[i]);
			dto.setRefreshDate(refreshDate);
			refreshZombieHtworlMapper.refreshCommentCreateDate(dto);
		}
	}
	
	@Override
	public void updateZombieHtworld(String idsStr,Date refreshDate)throws Exception{
		Integer[] wids = StringUtil.convertStringToIds(idsStr);
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		for(int i=0; i<wids.length; i++){
			InteractRefreshZombieHtworld wdto = new InteractRefreshZombieHtworld();
			wdto.setWid(wids[i]);
			UserZombieHtworld tmp = refreshZombieHtworlMapper.queryZombieHtworldByWid(wdto);
			
			//时分秒要跟原来的数据相同
			calendar.setTime(tmp.getCreateDate());
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);
			int second = calendar.get(Calendar.SECOND);
			calendar.setTime(refreshDate);
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, minute);
			calendar.set(Calendar.SECOND, second);
			wdto.setRefreshDate(new Date(calendar.getTimeInMillis()));
			//更新织图时间
			refreshZombieHtworlMapper.refreshWorldCreateDate(wdto);
			
			//更新评论时间
			List<UserZombieHtworld> commentList = refreshZombieHtworlMapper.queryZombieComment(wdto);
			for(UserZombieHtworld co:commentList){
				//时分秒要跟原来的数据相同
				calendar.setTime(co.getCreateDate());
				hour = calendar.get(Calendar.HOUR_OF_DAY);
				minute = calendar.get(Calendar.MINUTE);
				second = calendar.get(Calendar.SECOND);
				calendar.setTime(refreshDate);
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				calendar.set(Calendar.MINUTE, minute);
				calendar.set(Calendar.SECOND, second);
				
				InteractRefreshZombieHtworld cdto = new InteractRefreshZombieHtworld();
				cdto.setCid(co.getCommentId());
				cdto.setRefreshDate(new Date(calendar.getTimeInMillis()));
				refreshZombieHtworlMapper.refreshCommentCreateDate(cdto);
			}
		}
		
		
	}
	
	
	
	@Override
	public void updateZombieHtworldByUserIds(String uidsStr,Date refreshDate,Integer daySpan)throws Exception{
		Integer[] userIds = StringUtil.convertStringToIds(uidsStr);
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date companyCreateDay = df.parse("2012-8-20");
		boolean repeatflag;
		for(int i=0; i<userIds.length; i++){
			//检测是否userId重复
			repeatflag = false;
			for(int j=0; j<i; j++){
				if(userIds[i].equals(userIds[j])){
					repeatflag = true;
					break;
				}
			}
			
			if(repeatflag == true)continue;//若是重复id则跳过当前抄作
			
			calendar.setTime(refreshDate);
			
			InteractRefreshZombieHtworld rDto = new InteractRefreshZombieHtworld();
			rDto.setUid(userIds[i]);
			List<Integer> widList = refreshZombieHtworlMapper.queryWidByUid(rDto);
			for(int k=0; k<widList.size(); k++){
				if((calendar.getTimeInMillis() > companyCreateDay.getTime())&& (k!=0) ){//判断年份是否超过公司创建时间
					int day = calendar.get(Calendar.DAY_OF_YEAR);
					calendar.set(Calendar.DAY_OF_YEAR, day - daySpan);
				}
				updateZombieHtworld(widList.get(k).toString(),new Date(calendar.getTimeInMillis()));
			}
		}
	}
}
