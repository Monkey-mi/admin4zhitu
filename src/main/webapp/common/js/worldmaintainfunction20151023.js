stylerFuncion = function(value,row,index){
	return 'cursor:pointer;';
	},

formatterFunction = function(value, row, rowIndex ) {
	var url = value;
	if(value == '' || value == undefined) {
		var slink;
		if(row['shortLink'] == '')
			slink = row[worldKey];
		else 
			slink = row['shortLink'];
		url = worldURLPrefix + slink;
	}
	return "<a title='播放织图' class='updateInfo' href='javascript:showWorld(\""
	+ url + "\")'>"+url+"</a>";
}
	
	
	function openWorldUrlPage(url,params) {
		ur += "?worldId="params;
		$.fancybox({
			'margin'			: 20,
			'width'				: '100%',
			'height'			: '100%',
			'autoScale'			: true,
			'transitionIn'		: 'none',
			'transitionOut'		: 'none',
			'type'				: 'iframe',
			'href'				: uri 
		/*	'href'				: uri + "?adminKey=" + adminKey*/
		});
	}