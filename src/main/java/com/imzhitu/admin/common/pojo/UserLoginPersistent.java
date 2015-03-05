package com.imzhitu.admin.common.pojo;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 后台管理用户权限信息POJO
 * </p>
 * 
 * 创建时间：2013-8-24
 * @author ztj
 *
 */
public class UserLoginPersistent implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6013649717973912633L;
	private final String userId;
    private final Set<GrantedAuthority> authorities;
    
    
	public UserLoginPersistent(String userId,
			Set<GrantedAuthority> authorities) {
		this.userId = userId;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getUsername() {
		return userId;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
