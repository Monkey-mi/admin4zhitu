var baseTools = {
		/**
		 * 从URL获取指定参数
		 * @param name 指定的参数key
		 * @returns
		 */
		getQueryString : function(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
			var r = window.location.search.substr(1).match(reg);
			if (r != null)
				return unescape(r[2]);
			return null;
		},

		/**
		 * 图片载入出错回调
		 * @param img 图片
		 * @param path 出错路径
		 */
		imgErrorCallback : function(img,path) {
			img.src=path;
		},
		/**
		 * 图片载入出错回调
		 * @param img
		 * @param path
		 * @param width 指定宽
		 * @param height 指定高
		 */
		imgErrorCallbackWithWH : function(img,path, width, height) {
			img.src=path;
			img.width = width;
			img.height = height;
			$(img).attr('iserror',1);
		},
		/**
		 * 计算总页数
		 * @param limit 每页记录条数
		 * @param total 总记录条数
		 * @returns
		 */
		caculateTotalPage : function(limit,total) {
			var totalPage = parseInt(total / limit);
			if(total % limit > 0) {
				return ++totalPage;
			}
			return totalPage;
		},
		/**
		 * 
		 * @param params
		 * @param pixel 像素
		 */
		createQRCodeByGoogle : function(params,pixel) {
			var url = 'https://chart.googleapis.com/chart?cht=qr&chld=H&chs=' + pixel + '&chl=' + params;
			$.post(url,function(data) {
				console.log(data);
			});
		},
		/**
		 * 判断是否禁用cookie
		 * @returns
		 */
		checkCookieEnable : function() {
			return navigator.cookieEnabled;
		},
		/**
		 * 设置cookie,默认过期时间为1天
		 */
		setCookie : function(name,value) {
		    var exp = new Date(); 
		    exp.setTime(exp.getTime() + 24*60*60*1000); 
		    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
		},
		/**
		 * 设置cookie
		 */
		setCookie : function(name,value,time) {
		    var exp = new Date(); 
		    exp.setTime(exp.getTime() + time); 
		    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
		},
		/**
		 * 获取cookie
		 * @param name
		 * @returns
		 */
		getCookie : function(name) {
			var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		    if(arr=document.cookie.match(reg))
		        return unescape(arr[2]); 
		    else 
		        return null; 
		},
		/**
		 * 删除cookie
		 * @param name
		 */
		delCookie : function(name) {
		    var exp = new Date(); 
		    exp.setTime(exp.getTime() - 1); 
		    var cval=getCookie(name); 
		    if(cval!=null) 
		        document.cookie= name + "="+cval+";expires="+exp.toGMTString(); 
		},
		
		/**
		 * 重置窗口大小
		 */
		resizeWindow : function($target,minWidth,maxWidth) {
			var winWidth = $(window).width();
			if(winWidth < minWidth) {
				winWidth = minWidth;
			} else if(winWidth >= maxWidth) {
				winWidth = maxWidth;
			}
			$target.css({'width' : winWidth});
		},
		
		/**
		 * 获取本周第一天和最后一天
		 */
		getThisWeekDay:function() {
			var thisWeek = {},
				a = new Date(),
				b = a.getDate() - a.getDay(),
				thisWeekLastDay = new Date(),
				thisWeekFirstDay = new Date();
			
			thisWeekFirstDay.setDate(b+1);//本周第一天
			thisWeekLastDay.setDate(b+7);//本周最后一天
			thisWeek.firstDay = thisWeekFirstDay;
			thisWeek.lastDay = thisWeekLastDay;
			return thisWeek;
		},
		/**
		 * 获取上周第一天和最后一天
		 */
		getLastWeekDay:function() {
			var lastWeek = {},
				a = new Date(),
				b = a.getDate() - a.getDay()-7,
				lastWeekLastDay = new Date(),
				lastWeekFirstDay = new Date();
			
			lastWeekFirstDay.setDate(b+1);//上周第一天
			lastWeekLastDay.setDate(b+7);//上周最后一天
			lastWeek.firstDay = lastWeekFirstDay;
			lastWeek.lastDay = lastWeekLastDay;
			return lastWeek;
		},
		/**
		 * 获取本月第一天和最后一天
		 */
		getThisMonthDay:function() {
			var thisMonth = {},
			firstDate = new Date(),
			endDate = new Date(firstDate);
			
			firstDate.setDate(1); //第一天
			endDate.setMonth(firstDate.getMonth()+1);//最后一天 
			endDate.setDate(0);

			thisMonth.firstDay = firstDate;
			thisMonth.lastDay = endDate;
			return thisMonth;
		},
		/**
		 * 获取上月第一天和最后一天
		 */
		getLastMonthDay:function() {
			var lastMonth = {},
			month = new Date().getMonth(),
			firstDate = new Date(),
			endDate = new Date(firstDate);
			if(month==0){
				month = 11;
			}else{
				month = month - 1;
			}
			firstDate.setMonth(month, 1); //第一天
			endDate.setMonth(firstDate.getMonth()+1);//最后一天 
			endDate.setDate(0);

			lastMonth.firstDay = firstDate;
			lastMonth.lastDay = endDate;
			return lastMonth;
		},
		/**
		 * 格式化日期
		 */
		simpleFormatDate : function(date) {
			var year = date.getFullYear(),
				month = (date.getMonth() + 1),
				day = date.getDate();
			if(month < 10) {month = "0" + month;}
			if(day < 10) {day = "0" + day;}
			return year + '-' + month + "-" + day;	
		},
		/**
		 * 名字过滤
		 * @param authorName
		 */
		nameFilter : function(authorName) {
			if(authorName == null || authorName == '' || authorName == '(null)') {
				authorName = '织图用户';
			}
			return authorName;
		},
		isNULL : function(str) {
			if(str == null || str == '' || str == '(null)') {
				return true;
			}
			return false;
		},
		/**
		 * 图片路径过滤
		 * @param imgPath
		 * @returns
		 */
		imgPathFilter : function(imgPath,errorPath) {
			if(imgPath == null || imgPath == '' || imgPath == '(null)') {
				imgPath = errorPath;
			}
			return imgPath;
		},
		
		parseDate : function(s) {
			return new Date(s.replace(/-/g,"/"));
		},
		
		getShortDate : function(now, date) {
			var s = null;
			if (date == null || date == 'undefined')
				return "";
			var deltime = (now.getTime() - date.getTime()) / 1000;
			if (deltime > 365*24*60*60) {
				s = parseInt(deltime / (365 * 24 * 60 * 60)) + "年前";
			} else if (deltime > 30*24*60*60) {
				s = parseInt(deltime / (30*24*60*60)) + "个月前";
			} else if (deltime > 24*60*59) {
				s = parseInt(deltime / (23*60*60)) + "天前";
			} else {
				s = '今天 ' + date.format('hh:mm');
			}
			return s;
		}
};

Date.prototype.format = function(format) {  
	 /*  
	  * eg:format="YYYY-MM-dd hh:mm:ss";  
	  */ 
     var o = {  
	     "M+" : this.getMonth() + 1, // month  
	     "d+" : this.getDate(), // day  
	     "h+" : this.getHours(), // hour  
	     "m+" : this.getMinutes(), // minute  
	     "s+" : this.getSeconds(), // second  
	     "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter  
	     "S"  : this.getMilliseconds()  
     }; 
     if (/(y+)/.test(format)) {  
	     format = format.replace(RegExp.$1, (this.getFullYear() + "")  
	            .substr(4 - RegExp.$1.length));  
     }  
     for ( var k in o) {  
    	 if (new RegExp("(" + k + ")").test(format)) {  
    		 format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]  
                   : ("00" + o[k]).substr(("" + o[k]).length));  
       	}  
     }  
     return format;  

};

