package com.imzhitu.admin.interact.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imzhitu.admin.base.BaseTest;

public class InteractZombieWorldServiceTest extends BaseTest{
	@Autowired
	private InteractZombieService service;
	
	Logger log = Logger.getLogger(InteractZombieWorldServiceTest.class);
	
	@Test
	public void queryZombieWorldTest()throws Exception{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		service.queryZombieWorld(jsonMap, 10);
		log.info("=====>>>>"+jsonMap);
	}
	
	@Test
	public void saveZombieWorldTest()throws Exception{
		String childsJSON = "{\"1\":[{\"width\":640,\"childWorldDesc\":\"\",\"path\":\"1807891425.jpg\",\"height\":640},{\"toId\":3,\"type\":1,\"coordinatey\":54,\"atId\":1,\"coordinatex\":28,\"angle\":-1,\"thumbPath\":\"1990574217.jpg.thumbnail\"}],\"3\":[{\"width\":640,\"childWorldDesc\":\"\",\"path\":\"1990574217.jpg\",\"height\":640},{\"toId\":5,\"type\":1,\"coordinatey\":40,\"atId\":3,\"coordinatex\":41,\"angle\":-1,\"thumbPath\":\"2038307498.jpg.thumbnail\"}],\"5\":[{\"width\":640,\"childWorldDesc\":\"\",\"path\":\"2038307498.jpg\",\"height\":640}]}";
		String coverPath = "1807891425.jpg";
		String titlePath = "1807891425.jpg";
		String titleThumbPath = "1807891425.jpg.middleImage";
		service.saveZombieWorld(childsJSON, 1, 527, null,null,null, null, coverPath, titlePath, titleThumbPath, null, null, null, 238823,null);
	}
	
	@Test
	public void savaZombieWorldToHtWorldTest()throws Exception{
		service.saveZombieWorldToHtWorld(399);
//		String localPath="e:\\your\\1419493801.jpg.thumbnail";
//		if(localPath == null || localPath.equals(""))return ;
//		int nameBeginPos = localPath.lastIndexOf('\\');
//		if (nameBeginPos < 0)
//			nameBeginPos = -1;
//		int nameEndPos = localPath.indexOf(".jpg");
//		String str = localPath.substring(nameBeginPos+1, nameEndPos);
//		try{
//			Long time = Long.parseLong(str);
//			Date date = new Date(time*1000);
//			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
//			String dateStr = df.format(date);
//			log.info("http://static.imzhitu.com/ios/image/" + dateStr + "/"+localPath.substring(nameBeginPos+1));
//		}catch(Exception e){
//			throw new Exception("名字出错");
//		}
		
	}
	
	@Test
	public void batchSaveZombieWorldToHTWorldTest()throws Exception{
//		Date now = new Date();
//		service.batchSaveZombieWorldToHTWorld("384", new Date(now.getTime() + 10*1000), 1);
//		Thread.sleep(121*1000);
	}
	
	@Test
	public void batchUpdateZombieWorldLabelTest()throws Exception{
		service.batchUpdateZombieWorldLabel("299,300,301,302", "japan,abc,fuck,nothing,right,不行,随便测");
	}
}
