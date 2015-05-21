/**
 * 
 */
package com.imzhitu.admin.common.pojo;

import java.io.Serializable;

/**
 * 织图与频道关联表数据传输对象
 *
 * @author zhangbo 2015年5月20日
 */
public class OpWorldOfCannelDto implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -5924092140274146109L;

    /**
     * 织图与频道关联表主键id
     */
    private Integer id;

    /**
     * 织图在频道中的权重，1为置顶，0为不置顶
     */
    private Integer weight;

    /**
     * 织图在频道中加精，1为加精，0为不加精
     */
    private Integer superb;

    /**
     * @return the id
     */
    public Integer getId() {
	return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
	this.id = id;
    }

    /**
     * @return the weight
     */
    public Integer getWeight() {
	return weight;
    }

    /**
     * @param weight
     *            the weight to set
     */
    public void setWeight(Integer weight) {
	this.weight = weight;
    }

    /**
     * @return the superb
     */
    public Integer getSuperb() {
	return superb;
    }

    /**
     * @param superb
     *            the superb to set
     */
    public void setSuperb(Integer superb) {
	this.superb = superb;
    }

}
