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
     * 频道id
     */
    private Integer channelId;
    
    /**
     * 织图id
     */
    private Integer worldId;

    /**
     * 织图在频道中的权重，1为置顶，0为不置顶
     */
    private Integer weight;

    /**
     * 织图在频道中加精，1为加精，0为不加精
     */
    private Integer superb;
    
    /**
     * 织图在频道中是否生效，1为生效，0为不生效
     */
    private Integer valid;
    
    /**
     * 织图在频道被删除的原因
     */
    private String deleteReason;

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
     * @return the channelId
     */
    public Integer getChannelId() {
        return channelId;
    }

    /**
     * @param channelId the channelId to set
     */
    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    /**
     * @return the worldId
     */
    public Integer getWorldId() {
        return worldId;
    }

    /**
     * @param worldId the worldId to set
     */
    public void setWorldId(Integer worldId) {
        this.worldId = worldId;
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

    /**
     * @return the valid
     */
    public Integer getValid() {
        return valid;
    }

    /**
     * @param valid the valid to set
     */
    public void setValid(Integer valid) {
        this.valid = valid;
    }

    /**
     * @return the deleteReason
     */
    public String getDeleteReason() {
        return deleteReason;
    }

    /**
     * @param deleteReason the deleteReason to set
     */
    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
    }

}
