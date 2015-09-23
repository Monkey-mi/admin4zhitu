package com.imzhitu.admin.interact.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;
import com.imzhitu.admin.common.pojo.StarModule;
public class InteractStarModuleServiceTest extends BaseTest{
	
	@Autowired
	private InteractStarModuleService interactStarModuleService;
	
	
	@Test
	public void  update() throws Exception{
			interactStarModuleService.update(3,"田东","fjdksl",456852,"d.jsp","Iamhere");
	}
	
	@Test
	public void  destroy() throws Exception{
			interactStarModuleService.destory(2);
	}
	
	@Test
	public void get() throws Exception{
		List<StarModule> list  =  interactStarModuleService.get(0);
		System.out.println("******************************" + list.toString());
	}
}
