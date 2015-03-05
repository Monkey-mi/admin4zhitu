package com.imzhitu.admin.common.pojo;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

public class AdminUserDetails implements UserDetails {

	private Integer id;
	private String loginCode;
	private String name;
	private byte[] password;
	private List<AdminRole> roles;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3449051974577238563L;
	
	public AdminUserDetails(Integer id, String loginCode, String name,
			byte[] password, List<AdminRole> roles) {
		super();
		this.id = id;
		this.loginCode = loginCode;
		this.name = name;
		this.password = password;
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return new String(Hex.encode(this.password));
	}

	@Override
	public String getUsername() {
		return loginCode;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
