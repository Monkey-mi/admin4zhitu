package com.imzhitu.admin.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 运营系统公共方法类，存放一些工具方法
 * 
 * @author zhangbo	2015年11月2日
 *
 */
public class AdminUtil {

	/**
	 * 随机函数生成介于min，max之间的数,最大是10000000
	 * 
	 * @param min	随机数最小边界
	 * @param max	随机数最大边界
	 * @return
	 * @author zhangbo	2015年11月2日
	 */
	public static Integer GetRandamNum(Integer min,Integer max){
		int ma = max.intValue();
		int mi = min.intValue();
		if (mi>ma||mi<0) {
			return 1;
		}
		if (mi==ma) {
			return max;
		}
		int l=0;
		l = (int)(Math.random()*100007)%(ma-mi+1);
		l = mi+l;
		return new Integer(l);
	}
	
	/**
	 * 判断字符串是否为数字类型
	 * 
	 * @param str	要判断的字符串
	 * @return
	 * @author zhangbo	2015年12月17日
	 */
	public static boolean isNumeric(String str){ 
	   Pattern pattern = Pattern.compile("[0-9]*"); 
	   Matcher isNum = pattern.matcher(str);
	   if( isNum.matches() ){
	       return true; 
	   }
	   return false; 
	}
	
}
