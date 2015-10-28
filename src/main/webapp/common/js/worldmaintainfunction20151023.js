function showWorldWithURI(uri) {
	$.fancybox({
		'margin'			: 20,
		'width'				: '100%',
		'height'			: '100%',
		'autoScale'			: true,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'href'				: uri ,
		'title' : uri,
		'titlePosition' : 'over',
		'titleShow'   : true
	/*	'href'				: uri + "?adminKey=" + adminKey*/
	});
}
