/**
 * 
 */
package com.imzhitu.admin.constant;

/**
 * 权限常量类
 *
 * @author zhangbo 2015年5月15日
 */
public interface Permission {
    
    /**
     * 超级管理员，拥有系统所有权限
     */
    public String SUPER_ADMIN = "super_admin";
    
    /**
     * 运营管理员
     */
    public String OP_ADMIN = "op_admin";
    
    /**
     * 维护系统
     */
    public String ADMIN = "admin";
    
    /**
     * 运营成员
     */
    public String OP_ROLE = "op_role";
    
    /**
     * 游客
     */
    public String GUEST = "guest";
}
