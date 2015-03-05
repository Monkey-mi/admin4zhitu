package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

public class UserLevelDto implements Serializable{
	private static final long serialVersionUID = 2882098862068972348L;
	private Integer id;
	private Integer min_fans_count;
	private Integer max_fans_count;		//最大粉丝数
	private Integer min_liked_count;
	private Integer max_liked_count;	//最大喜欢数
	private Integer min_comment_count;
	private Integer max_comment_count;	//最大评论数
	private Integer min_play_times;
	private Integer max_play_times;		//最大播放数
	private Integer time;				//为期，单位：小时
	private String level_description;	//等级描述
	private Integer weight;				//权重
	
	
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return this.id;
	}

	public void setMin_fans_count(Integer min_fans_count){
		this.min_fans_count = min_fans_count;
	}
	public Integer getMin_fans_count(){
		return this.min_fans_count;
	}
	
	public void setMax_fans_count(Integer max_fans_count){
		this.max_fans_count = max_fans_count;
	}
	public Integer getMax_fans_count(){
		return this.max_fans_count;
	}
	
	public void setMin_liked_count(Integer min_liked_count){
		this.min_liked_count = min_liked_count;
	}
	public Integer getMin_liked_count(){
		return this.min_liked_count;
	}
	
	public void setMax_liked_count(Integer max_liked_count){
		this.max_liked_count = max_liked_count;
	}
	public Integer getMax_liked_count(){
		return this.max_liked_count;
	}
	
	public void setMin_comment_count(Integer min_comment_count){
		this.min_comment_count = min_comment_count;
	}
	public Integer getMin_comment_count(){
		return this.min_comment_count;
	}
	
	public void setMax_comment_count(Integer max_comment_count){
		this.max_comment_count = max_comment_count;
	}
	public Integer getMax_comment_count(){
		return this.max_comment_count;
	}
	
	public void setMin_play_times(Integer min_play_times){
		this.min_play_times = min_play_times;
	}
	public Integer getMin_play_times(){
		return this.min_play_times;
	}
	
	public void setMax_play_times(Integer max_play_times){
		this.max_play_times = max_play_times;
	}
	public Integer getMax_play_times(){
		return this.max_play_times;
	}
	
	public void setTime(Integer time){
		this.time = time;
	}
	public Integer getTime(){
		return this.time;
	}
	
	public void setLevel_description(String level_description){
		this.level_description = level_description;
	}
	public String getLevel_description(){
		return this.level_description;
	}
	
	public UserLevelDto(){
		super();
	}
	
	public UserLevelDto(Integer id,Integer min_fans_count,Integer max_fans_count,
			Integer min_liked_count,Integer max_liked_count,
			Integer min_comment_count,Integer max_comment_count,
			Integer min_play_times,Integer max_play_times,
			Integer time,String level_description,Integer weight){
		super();
		this.id = id;
		this.min_fans_count = min_fans_count;
		this.max_fans_count = max_fans_count;
		this.min_liked_count = min_liked_count;
		this.max_liked_count = max_liked_count;
		this.min_comment_count = min_comment_count;
		this.max_comment_count = max_comment_count;
		this.min_play_times = min_play_times;
		this.max_play_times = max_play_times;
		this.time = time;
		this.level_description = level_description;
		this.weight = weight;
	}

}
