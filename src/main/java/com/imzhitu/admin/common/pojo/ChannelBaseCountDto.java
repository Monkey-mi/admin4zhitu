/**
 * 
 */
package com.imzhitu.admin.common.pojo;

import com.hts.web.common.pojo.AbstractNumberDto;

/**
 * 频道基数管理数据传输对象 
 * @author zhangbo 2015年5月29日
 */
public class ChannelBaseCountDto extends AbstractNumberDto {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -4398741724604343580L;
    
    /**
     * 频道id
     */
    private Integer channelId;
    
    /**
     * 频道中织图基数
     */
    private Integer worldBaseCount;
    
    /**
     * 频道中所有图片基数
     */
    private Integer childBaseCount;
    
    /**
     * 频道成员基数
     */
    private Integer memberBaseCount;
    
    /**
     * 频道中加精基数
     */
    private Integer superbBaseCount;
    
    /**
     * 频道中真实织图数量
     */
    private Integer trueWorldCount;
    
    /**
     * 频道主题id
     */
    private Integer channelThemeId;
    
    /**
     * 当前登陆管理员账号id
     */
    private Integer adminId;

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
     * @return the worldBaseCount
     */
    public Integer getWorldBaseCount() {
        return worldBaseCount;
    }

    /**
     * @param worldBaseCount the worldBaseCount to set
     */
    public void setWorldBaseCount(Integer worldBaseCount) {
        this.worldBaseCount = worldBaseCount;
    }

    /**
     * @return the childBaseCount
     */
    public Integer getChildBaseCount() {
        return childBaseCount;
    }

    /**
     * @param childBaseCount the childBaseCount to set
     */
    public void setChildBaseCount(Integer childBaseCount) {
        this.childBaseCount = childBaseCount;
    }

    /**
     * @return the memberBaseCount
     */
    public Integer getMemberBaseCount() {
        return memberBaseCount;
    }

    /**
     * @param memberBaseCount the memberBaseCount to set
     */
    public void setMemberBaseCount(Integer memberBaseCount) {
        this.memberBaseCount = memberBaseCount;
    }

    /**
     * @return the superbBaseCount
     */
    public Integer getSuperbBaseCount() {
        return superbBaseCount;
    }

    /**
     * @param superbBaseCount the superbBaseCount to set
     */
    public void setSuperbBaseCount(Integer superbBaseCount) {
        this.superbBaseCount = superbBaseCount;
    }

    /**
     * @return the trueWorldCount
     */
    public Integer getTrueWorldCount() {
        return trueWorldCount;
    }

    /**
     * @param trueWorldCount the trueWorldCount to set
     */
    public void setTrueWorldCount(Integer trueWorldCount) {
        this.trueWorldCount = trueWorldCount;
    }

    /**
     * @return the channelThemeId
     */
    public Integer getChannelThemeId() {
        return channelThemeId;
    }

    /**
     * @param channelThemeId the channelThemeId to set
     */
    public void setChannelThemeId(Integer channelThemeId) {
        this.channelThemeId = channelThemeId;
    }

    /**
     * @return the adminId
     */
    public Integer getAdminId() {
        return adminId;
    }

    /**
     * @param adminId the adminId to set
     */
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

}
