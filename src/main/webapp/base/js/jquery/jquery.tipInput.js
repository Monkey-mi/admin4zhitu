/**
 * jQuery-Plugin "tipInput"
 * 
 * @version: 1.0, 07.20.2012
 * 
 * @author: zhuzhimin
 *          zmzhu09@qq.com
 *          
 * 
 * @example: $('selector').tipInput('邮箱/账号/手机号');
 * 
 */

(function($) {
	
	$.fn.tipInput = function(tipText) {
		
			
			var el = $(this);
			var inputId = el.attr("id");
			var inputName = el.attr("name");
			
			el.wrapAll('<div style="position:relative; display:inline-block; *display:inline; zoom:1;"></div>');
			el.before('<label  id="'+inputId+'Tip" for="'+inputName+'" >'+tipText+'</label>');
			
			var tip = $("#"+inputId+"Tip");
		
			tip.css("padding-top",el.css("padding-top")).css("padding-right",el.css("padding-right")).css("padding-bottom",el.css("padding-bottom")).css("padding-left",el.css("padding-left"));
			tip.css("margin-top",el.css("margin-top")).css("margin-right",el.css("margin-right")).css("margin-bottom",el.css("margin-bottom")).css("margin-left",el.css("margin-left"));
			tip.css("line-height",el.css("height"));
			tip.css("position","absolute").css("left","0px").css("top","0px").css("color","#888888").css("cursor","text");
			
			if(el.val() || el.text()){
				tip.hide();
			}
			
			/**
			 * Set focus action
			 * 
			 */
			    
			el.focus(function() {
				tip.hide();
			});
			
			/**
			 * Set blur action
			 * 
			 */
			
			el.blur(function() {
				if(el.val() || el.text()){
				    tip.hide();
				}
				else{
					tip.show();
				}
				
			});
			
			tip.click(function(){el.focus();});
			
		
	};
	
})(jQuery);
