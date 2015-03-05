/**
 * 初始化表格图片查看按钮
 */
function initViewPlotImgBtn($target) {
	if (!$.jqplot.use_excanvas) {
		var outerDiv = $(document.createElement('div'));
        var header = $(document.createElement('div'));
        var div = $(document.createElement('div'));

        outerDiv.append(header);
        outerDiv.append(div);

        outerDiv.addClass('jqplot-image-container');
        header.addClass('jqplot-image-container-header');
        div.addClass('jqplot-image-container-content');

        header.html('右键图片另存为...');

        var close = $(document.createElement('a'));
        close.addClass('jqplot-image-container-close')
        .html('X')
        .attr('title','点击关闭')
        .attr('href', 'javascript:void(0);')
        .click(function() {
        	$(this).parents('div.jqplot-image-container').hide(500);
        });
        header.append(close);

        $target.after(outerDiv);
        outerDiv.hide();

        outerDiv = header = div = close = null;

        if (!$.jqplot._noToImageButton) {
            var btn = $target.nextAll('a.jqplot-image-button').first();
            btn.bind('click', {chart: $target}, function(evt) {
                var imgelem = evt.data.chart.jqplotToImageElem();
                var div = $target.nextAll('div.jqplot-image-container').first();
                div.children('div.jqplot-image-container-content').empty();
                div.children('div.jqplot-image-container-content').append(imgelem);
                div.show(500);
                div = null;
            }).fadeIn();

            $target.after(btn);
            btn.after('<br />');
            btn = null;
        }
	}
}