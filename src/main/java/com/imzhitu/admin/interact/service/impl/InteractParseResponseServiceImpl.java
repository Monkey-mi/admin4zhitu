package com.imzhitu.admin.interact.service.impl;

import org.springframework.stereotype.Service;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.interact.service.InteractParseResponseService;
import com.imzhitu.admin.constant.Emoji;

@Service
public class InteractParseResponseServiceImpl extends BaseServiceImpl implements InteractParseResponseService{
	
	@Override
	public String parserQString(String qStr)throws Exception{
		if(qStr==null || qStr.trim().equals(""))return null;
		String l = qStr.replaceAll("\n", "");
		String s = l.replaceAll("：", ":");
		int i =s.indexOf(":");
		String r = s.substring(i+1);
		String result = r.replaceAll("\\[.*\\]", "").trim();
		if(result == null || result.equals("") || result.trim().equals(""))return Emoji.emojiTag;
		return result;
	}
	
	@Override
	public String parserAString(String aStr)throws Exception{
		return null;
	}
}
