package com.imzhitu.admin.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class PropertiesFileAddAndQuery {
	/**
	 * 向properties文件中增加记录的通用方法
	 * @param key  
	 * @param value
	 * @param filePath 文件路径
	 * @param comments 插入数据是的注释
	 * @throws Exception 
		*	2015年8月26日
		*	mishengliang
	 */
	public static void add(String key,String value,String filePath,String comments) throws Exception{
		InputStream in = new FileInputStream(filePath);
		BufferedReader bf = new BufferedReader(new InputStreamReader(in, "utf-8"));
		Properties props = new Properties();
		props.load(bf);
		props.setProperty(key, value);
		OutputStream fos = new FileOutputStream(filePath);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
		props.store(bw, comments);
		fos.close();
		in.close();
	} 
	
	/**
	 * 从properties中依照key值取出value值
	 * @param key
	 * @param filePath 配置文件路径
	 * @return
	 * @throws Exception 
		*	2015年8月26日
		*	mishengliang
	 */
	public static String  query(String key,String filePath) throws Exception {
		Properties prop = new Properties();
		InputStream in = new FileInputStream(filePath); 
		BufferedReader bf = new BufferedReader(new InputStreamReader(in, "utf-8"));
		prop.load(bf);
		in.close();
		
		String value = prop.getProperty(key);
		return value;
	}

}
