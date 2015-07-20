package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * 广告关键词数据传输对象
 * 
 * @author zhangbo 2015年7月13日
 *
 */
public class ADKeywordDTO extends AbstractNumberDto implements Serializable {

    /**
     * 序列号
     * 
     * @author zhangbo 2015年7月13日
     */
    private static final long serialVersionUID = -5124602568979541775L;

    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 广告关键字
     */
    private String ADKeyword;

    /**
     * 关键字命中次数
     */
    private Integer hitCount;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the aDKeyword
     */
    public String getADKeyword() {
        return ADKeyword;
    }

    /**
     * @param aDKeyword the aDKeyword to set
     */
    public void setADKeyword(String aDKeyword) {
        ADKeyword = aDKeyword;
    }

    /**
     * @return the hitCount
     */
    public Integer getHitCount() {
        return hitCount;
    }

    /**
     * @param hitCount the hitCount to set
     */
    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }

}
