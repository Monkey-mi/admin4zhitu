package com.imzhitu.admin.privileges.service.impl;

import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hts.web.common.util.MD5Encrypt;

public class PasswordEncoderImpl implements  PasswordEncoder{

	@Override
	public String encode(CharSequence rawPassword) {
		try {
			byte[] digestPass = MD5Encrypt.encryptByMD5(rawPassword.toString());
			return new String(Hex.encode(digestPass));
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		try {
			return MD5Encrypt.validatePassword(rawPassword.toString(), Hex.decode(encodedPassword));
		} catch (NoSuchAlgorithmException e) {
			return false;
		}
	}
}
