package com.imzhitu.admin.qiniu.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.imzhitu.admin.qiniu.service.QiniuService;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.rs.PutPolicy;

@Service
public class QiniuServiceImpl implements QiniuService {

	@Value("${qiniu.accessKey}")
	public String accessKey = "alJXpWJFt2HW_r4UjYdyge9ut_FzXrWo4TILwnOD";
	
	@Value("${qiniu.secretKey}")
	public String secretKey = "ees7veb5AFEZtj8Q3orisFFfw0VZ8vjyrDmcxaD6";
	
	@Value("${qiniu.bucket}")
	public String bucket = "imzhitu";

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	@Override
	public String getToken() throws Exception {
		Config.ACCESS_KEY = accessKey;
		Config.SECRET_KEY = secretKey;
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		PutPolicy putPolicy = new PutPolicy(bucket);
		return putPolicy.token(mac);
	}

}
