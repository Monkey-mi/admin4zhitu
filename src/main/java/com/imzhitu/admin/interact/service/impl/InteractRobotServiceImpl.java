package com.imzhitu.admin.interact.service.impl;

import java.io.IOException;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hts.web.common.service.impl.BaseServiceImpl;
import com.imzhitu.admin.interact.service.InteractRobotService;

@Service
public class InteractRobotServiceImpl extends BaseServiceImpl implements InteractRobotService{
	
	private static String TULINGURL = "http://www.tuling123.com/openapi/api?key=";
	
	@Value("${robot.app.key}")
	private String robotAppKey;
	
	@Value("${robot.app.secret}")
	private String robotAppSecret;
	
	@Value("${robot.userName}")
	private String userName;
	
	@Value("${robot.tulingKey}")
	private String tulingKey;
	
	public void setTulingKey(String tulingKey){
		this.tulingKey = tulingKey;
	}
	public String getTulingKey(){
		return this.tulingKey;
	}
	public void setRobotAppKey(String robotAppKey){
		this.robotAppKey = robotAppKey;
	}
	public String getRobotAppKey(){
		return this.robotAppKey;
	}
	
	public void setRobotAppSecret(String robotAppSecret){
		this.robotAppSecret = robotAppSecret;
	}
	public String getRobotAppSecret(){
		return this.robotAppSecret;
	}
	
	public String getUserName(){
		return this.userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	public String getAnswer(String question)throws Exception{
		
		if(null == question || "".equals(question))return null;
		
		String realm = "xiaoi.com";
        String method = "POST";
        String uri = "/robot/ask.do";
        byte[] b = new byte[20];
        new Random().nextBytes(b);
        String nonce = new String(Hex.encodeHex(b));
        String HA1 = DigestUtils.shaHex(StringUtils.join(new String[] {
        		robotAppKey, realm, robotAppSecret }, ":"));
        String HA2 = DigestUtils.shaHex(StringUtils.join(new String[] { method,
                uri }, ":"));
        String sign = DigestUtils.shaHex(StringUtils.join(new String[] { HA1,
                nonce, HA2 }, ":"));
 
        String str = null;
 
        HttpClient hc = new HttpClient();
        PostMethod pm = new PostMethod("http://nlp.xiaoi.com/robot/ask.do");
        pm.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
                "utf-8");
        pm.addRequestHeader("X-Auth", "app_key=\""+ robotAppKey + "\", nonce=\""
                + nonce + "\", signature=\"" + sign + "\"");
        pm.setParameter("platform", "custom");
        pm.setParameter("type", "0");
        pm.setParameter("userId", userName);
        pm.setParameter("question", question);
        int re_code;
        try {
            re_code = hc.executeMethod(pm);
            if (re_code == 200) {
                str = pm.getResponseBodyAsString();
            }
        } catch (HttpException e) {
            e.printStackTrace();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return str;
        }
		return str;
	}
	
	@Override
	public String getAnswerFromTuLing(String question)throws Exception{
		if(question == null || question.trim().equals(""))return null;
		String requestUrl = TULINGURL + tulingKey + "&info="+question;
		try{
			HttpGet request = new HttpGet(requestUrl);
			HttpResponse response = HttpClients.createDefault().execute(request);
			if(response.getStatusLine().getStatusCode() == 200){
				String result = EntityUtils.toString(response.getEntity());
				JSONObject obj = JSONObject.fromObject(result);
				return obj.getString("text");
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
}
