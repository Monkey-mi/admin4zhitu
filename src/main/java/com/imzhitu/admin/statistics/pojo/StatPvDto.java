package com.imzhitu.admin.statistics.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * PV数据访问接口
 * 
 * @author lynch 2015-12-19
 *
 */
public class StatPvDto extends AbstractNumberDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3920326466793120692L;
	private Integer id;
	private Integer pvkey;
	private Integer subkey;
	private Long pv;
	private Long pvtime;
	
	private Long begintime;
	private Long endtime;
	
	private String keyname;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPvkey() {
		return pvkey;
	}

	public void setPvkey(Integer pvkey) {
		this.pvkey = pvkey;
	}

	public Integer getSubkey() {
		return subkey;
	}

	public void setSubkey(Integer subkey) {
		this.subkey = subkey;
	}

	public Long getPv() {
		return pv;
	}

	public void setPv(Long pv) {
		this.pv = pv;
	}

	public Long getPvtime() {
		return pvtime;
	}

	public void setPvtime(Long pvtime) {
		this.pvtime = pvtime;
	}

	public Long getBegintime() {
		return begintime;
	}

	public void setBegintime(Long begintime) {
		this.begintime = begintime;
	}

	public Long getEndtime() {
		return endtime;
	}

	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}

	public String getKeyname() {
		return keyname;
	}

	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}
	
}
