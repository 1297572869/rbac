var 
	windowWidth,
	windowHeight,
	popstatus,
	//超时时间
	outTime;
/*
* whWindow()	浏览器宽高的获取
*/
function whWindow() {
	windowWidth = $(window).width();
	windowHeight = $(window).height();
}
/*
* windowSize()	窗口监听函数
*/
$(window).resize(function() {
	whWindow();
	popStatuRe();
});
whWindow();
/*
* popStatus()	提示
* status	1正确，2提示，3加载，4失败
* html	提示信息
* timeout	提示时间,单位：秒(-1为永远)
* url	是否跳转页面,没有则留空
* bremove	是否使用遮照,不为空时使用
*/
function popStatus(status, html, timeout, url, bremove) {
	//请求超时时间 
	var timeous = 20;
	clearTimeout(popstatus);
	clearTimeout(outTime);
	$('body #wstatus_don').remove();
	$('body #bremove_don').remove();
	if (status == 1) {
		$('body').append('<div id="wstatus_don"><div class="wstatus_don_s wstatus_don_s1"></div><span class="wstatus_don_f">'+html+'</span></div>');
	}else if (status == 2) {
		$('body').append('<div id="wstatus_don"><div class="wstatus_don_s wstatus_don_s2"></div><span class="wstatus_don_f">'+html+'</span></div>');
	}else if (status == 3) {
		$('body').append('<div id="wstatus_don"><div class="wstatus_don_s wstatus_don_s3"></div><span class="wstatus_don_f">'+html+'</span></div>');
	}else {
		$('body').append('<div id="wstatus_don"><div class="wstatus_don_s wstatus_don_s4"></div><span class="wstatus_don_f">'+html+'</span></div>');
	}
	popStatuRe();
	//是否使用遮照
	if (bremove) {
		$('body').append('<div id="bremove_don" onclick="stopPop();" />');
	}
	if (!url) {
		url = 0;
	}
	//抖动
	if (status == 2 || status ==4) {
		$("#wstatus_don").css("left",($("#bremove_don").width()-$("#wstatus_don").width())/2+'px');
		var sw = (windowWidth/2)-($('#wstatus_don').width()+18)/2;
		var sh = (windowHeight/2)-($('#wstatus_don').height()+18)/2;
		$('body #wstatus_don').animate({left:sw-15+'px'},100);
		$('body #wstatus_don').animate({left:sw+30+'px'},100);
		$('body #wstatus_don').animate({left:sw-30+'px'},100);
		$('body #wstatus_don').animate({left:sw+30+'px'},100);
		$('body #wstatus_don').animate({left:sw+'px'},100);
		$('body #wstatus_don').animate({top:sh-15+'px'},100);
		$('body #wstatus_don').animate({top:sh+30+'px'},100);
		$('body #wstatus_don').animate({top:sh-30+'px'},100);
		$('body #wstatus_don').animate({top:sh+30+'px'},100);
		$('body #wstatus_don').animate({top:sh+'px'},100);
	}
	popstatus = setTimeout(function() {
		//判断是否有跳转地址
		if (url != 0) {
			if (url == '?') {
				reloads();
			}else {
				location.href = url;
			}
		}
		if(timeout != -1 ){
			$('body #wstatus_don').remove();
			$('body #bremove_don').eq(-1).remove();
		}
	},timeout*1000);
	//超时时间设置
	 if (timeout >= timeous) {
		 outTime = setTimeout(function() { if ($('body #wstatus_don')) {
				 clearTimeout(popstatus);
				 if(timeout != -1 ){
					 $('body #wstatus_don').remove();
				}
				 popStatus(4, '阿哦,请求地址错误或超时,请重试！', 3, '', true); } 
		 		},timeout*1000-1000);
	 }
}
/*
* popStatuRe()	位置矫正
*/
function popStatuRe() {
	$('body #wstatus_don').css({'left':(windowWidth/2)-($('#wstatus_don').width()+18)/2+'px','top':(windowHeight/2)-($('#wstatus_don').height()+18)/2+'px'});
}


function stopPop(){
	$('body #wstatus_don').remove();
	$('body #bremove_don').remove();
	
}