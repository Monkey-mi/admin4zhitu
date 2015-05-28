/**
 * 
 */
package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * 频道织图回收站数据传输对象
 * 
 * @author zhangbo 2015年5月27日
 */
public class ChannelWorldRecyclerDto extends AbstractNumberDto {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6176925902260898650L;

    /**
     * 织图与频道关联表主键id
     */
    private Integer channelWorldId;

    /**
     * 频道id
     */
    private Integer channelId;

    /**
     * 织图id
     */
    private Integer worldId;

    /**
     * 用户id
     */
    private Integer authorId;

    /**
     * 缩略图链接
     */
    private String thumbPath;

    /**
     * 织图描述
     */
    private String worldDesc;

    /**
     * 删除原因
     */
    private String deleteReason;

    /**
     * 处理时间
     */
    private String updateTime;

    /**
     * @return the channelWorldId
     */
    public Integer getChannelWorldId() {
	return channelWorldId;
    }

    /**
     * @param channelWorldId
     *            the channelWorldId to set
     */
    public void setChannelWorldId(Integer channelWorldId) {
	this.channelWorldId = channelWorldId;
    }

    /**
     * @return the channelId
     */
    public Integer getChannelId() {
	return channelId;
    }

    /**
     * @param channelId
     *            the channelId to set
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
     * @param worldId
     *            the worldId to set
     */
    public void setWorldId(Integer worldId) {
	this.worldId = worldId;
    }

    /**
     * @return the authorId
     */
    public Integer getAuthorId() {
	return authorId;
    }

    /**
     * @param authorId
     *            the authorId to set
     */
    public void setAuthorId(Integer authorId) {
	this.authorId = authorId;
    }

    /**
     * @return the worldDesc
     */
    public String getWorldDesc() {
	return worldDesc;
    }

    /**
     * @param worldDesc
     *            the worldDesc to set
     */
    public void setWorldDesc(String worldDesc) {
	this.worldDesc = worldDesc;
    }

    /**
     * @return the thumbPath
     */
    public String getThumbPath() {
	return thumbPath;
    }

    /**
     * @param thumbPath
     *            the thumbPath to set
     */
    public void setThumbPath(String thumbPath) {
	this.thumbPath = thumbPath;
    }

    /**
     * @return the deleteReason
     */
    public String getDeleteReason() {
	return deleteReason;
    }

    /**
     * @param deleteReason
     *            the deleteReason to set
     */
    public void setDeleteReason(String deleteReason) {
	this.deleteReason = deleteReason;
    }

    /**
     * @return the updateTime
     */
    public String getUpdateTime() {
	return updateTime;
    }

    /**
     * @param updateTime
     *            the updateTime to set
     */
    public void setUpdateTime(String updateTime) {
	this.updateTime = updateTime;
    }
}
