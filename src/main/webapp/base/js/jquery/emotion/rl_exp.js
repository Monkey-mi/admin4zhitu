/*
*	评论表情渲染JS
*	@author:	小毛(微博：BUPT朱小毛)
*	@data:		2013年2月17日
*	@version:	1.0
*	@rely:		jQuery
*/
$(function(){
	/*
	*		参数说明
	*		baseUrl:	【字符串】表情路径的基地址
	*		pace:		【数字】表情弹出层淡入淡出的速度
	*		dir:		【数组】保存表情包文件夹名字
	*		text:		【二维数组】保存表情包title文字
	*		size:		【数组】保存表情包表情个数
	*		isExist:	【数组】保存表情是否加载过,对于加载过的表情包不重复请求。
	*/
	var rl_exp = {
		baseUrl:	'./base/js/jquery/emotion/images/',
		pace:		200,
		dir:		['emoji/'],
		text:[			/*表情包title文字，自己补充*/
		     [
				'高兴','可爱','笑脸','害羞','媚眼','花痴','飞吻','mua','惊讶','得意','呲牙','鬼脸','哎呀','委屈','哼哼',
				'我汗','忧郁','囧','苦逼','尴尬','生病','坑爹啊','累死了','哭','激动','刺瞎','生气','愤怒','瞌睡',
				'口罩','怕怕咯','大笑','汗','额','酷死了','我擦','外星人','ET','红心','心碎','赞','丘比特','厉害',
				'傻叉','好的','拳头','石头','胜利','禁止','向上','向下','向左','向右','三胖鼓掌','给力','祈祷','叹号','问号',
				'对','错','男孩','女孩','爸爸','妈妈','散步','跑步','冲浪','滑雪','骑车','游泳','顶','口水','呆萌',
				'耳朵','嘴唇','鼻子','猪头','泡澡','绵羊','呆鸡','野牛','大象','章鱼','小熊熊','骆驼','老鼠','老虎','兔子',
				'喵喵','马驹','猴头','狗狗','熊猫','猪鼻','青蛙','猫爪','小鱼','玫瑰','花','小花','郁金香','鲜花','椰树',
				'仙人掌','衣服','皮鞋','照相','电话','高尔夫','网球','棒球','足球','写字','衬衫','气球','炸弹','喝彩',
				'剪子','蝴蝶结','机密','铃声','女帽','裙子','理发店','和服','比基尼','拎包','拍摄','铃铛','月亮','戒指','葡萄',
				'香蕉','樱桃','汉堡','披萨','冰激凌','面包圈','火苗','礼物','钻石','火箭','锤子','蛋糕','南瓜灯','圣诞树','手枪',
				'火车','警车','出租车','小货车','卡拉ok','录像','贝斯','钢琴','小提琴','喜欢','橄榄球','灯泡','皇冠','眼睛','口红',
				'向日葵','音乐','吹气','绿茶','面包','面条','咖喱饭','饭团','麻辣烫','寿司','苹果','橙子','草莓','西瓜','柿子',
				'金牛座','双子座','巨蟹座','狮子座','处女座','天平座','天蝎座','射手座','摩羯座','水瓶座','白羊座','双鱼座','星座'
			 ]
		],
		size:		[190],
		prifix:     ['emoji'],
		suffix:     ['.png'],
		isExist:	[0],
		bind:	function(i){
			$("#rl_bq .rl_exp_main").eq(i).find('.rl_exp_item').each(function(){
				$(this).bind('click',function(){
					rl_exp.insertText(document.getElementById('rl_exp_input'),'['+$(this).find('img').attr('title')+']');
					//$('#rl_bq').fadeOut(rl_exp.pace);
				});
			});
		},
		/*加载表情包函数*/
		loadImg:function(i){
			var node = $("#rl_bq .rl_exp_main").eq(i);
			for(var j = 0; j < rl_exp.size[i]; j++){
				var domStr = 	'<li class="rl_exp_item">' + 
									'<img src="' + rl_exp.baseUrl + rl_exp.dir[i] + rl_exp.prifix[i] + j + '.png" alt="' + rl_exp.text[i][j] + 
									'" title="' + rl_exp.text[i][j] + '" />' +
								'</li>';
				$(domStr).appendTo(node);
			}
			rl_exp.isExist[i] = 1;
			rl_exp.bind(i);
		},
		/*在textarea里光标后面插入文字*/
		insertText:function(obj,str){
			obj.focus();
			if (document.selection) {
				var sel = document.selection.createRange();
				sel.text = str;
			} else if (typeof obj.selectionStart == 'number' && typeof obj.selectionEnd == 'number') {
				var startPos = obj.selectionStart,
					endPos = obj.selectionEnd,
					cursorPos = startPos,
					tmpStr = obj.value;
				obj.value = tmpStr.substring(0, startPos) + str + tmpStr.substring(endPos, tmpStr.length);
				cursorPos += str.length;
				obj.selectionStart = obj.selectionEnd = cursorPos;
			} else {
				obj.value += str;
			}
		},
		init:function(){
			$("#rl_bq > ul.rl_exp_tab > li > a").each(function(i){
				$(this).bind('click',function(){
					if( $(this).hasClass('selected') )
						return;
					if( rl_exp.isExist[i] == 0 ){
						rl_exp.loadImg(i);
					}
					$("#rl_bq > ul.rl_exp_tab > li > a.selected").removeClass('selected');
					$(this).addClass('selected');
					$('#rl_bq .rl_selected').removeClass('rl_selected').hide();
					$('#rl_bq .rl_exp_main').eq(i).addClass('rl_selected').show();
				});
			});
			if( rl_exp.isExist[0] == 0 ){
				rl_exp.loadImg(0);
			}
			$('#rl_bq').fadeIn();
			/**
			$("#rl_exp_btn").bind('click',function(){
				if( rl_exp.isExist[0] == 0 ){
					rl_exp.loadImg(0);
				}
				var w = $(this).position();
				$('#rl_bq').fadeIn(400);
			});
			
			$('#rl_bq a.close').bind('click',function(){
				$('#rl_bq').fadeOut(rl_exp.pace);
			});
			
			$(document).bind('click',function(e){
				var target = $(e.target);
				if( target.closest("#rl_exp_btn").length == 1 )
					return;
				if( target.closest("#rl_bq").length == 0 ){
					$('#rl_bq').fadeOut(rl_exp.pace);
				}
			});
			*/
		}
	};
	rl_exp.init();	//调用初始化函数。
});