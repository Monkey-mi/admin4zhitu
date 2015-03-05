package com.imzhitu.admin.common.pojo;

public class StatisticsCountGroupByDay {

	private String day;
	private Long count;

	public StatisticsCountGroupByDay() {
		super();
	}

	public StatisticsCountGroupByDay(String day, Long count) {
		super();
		this.day = day;
		this.count = count;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
