"use strict";
/**
 * Created by ZXW on 2014/8/19.
 */
var kanConsole = angular.module('kanConsole', ['ui.router']);

kanConsole.directive('ngBlur', function($parse){
    return function(scope, element, attr){
        var fn = $parse(attr['ngBlur']);
        $(element).on('focusout', function(event){
            fn(scope, {$event: event});
        });
    }
});

/*时间 yyyy-MM-dd hh:mm:ss*/
kanConsole.filter("timeFormat",function(){
    return function(name) {
        var str="";
        if(name) {
            str = (new Date(name * 1000)).format("yyyy-MM-dd hh:mm:ss");
        }
        return str;
    }
})

/*时间 yyyy-MM-dd */
kanConsole.filter("timeFormatDay",function(){
    return function(name) {
        var str="";
        if(name) {
            str = (new Date(name * 1000)).format("yyyy-MM-dd");
        }
        return str;
    }
})

angular.bootstrap('kanConsole');

jQuery(document).ready(function() {
    Metronic.init(); // init metronic core components
    Layout.init(); // init current layout
    //Demo.init(); // init demo features

    var browser = myBrowser();
    if(browser=="IE55" || browser=="IE6" || browser=="IE7" || browser=="IE8"){
        alert("您使用的是"+browser+"浏览器，可能出现不兼容情况，请及时升级您的浏览器");
    }
});
jQuery.extend(jQuery.validator.messages, {
    required: "必选字段",
    remote: "请修正该字段",
    email: "请输入正确格式的电子邮件",
    url: "请输入合法的网址",
    date: "请输入合法的日期",
    dateISO: "请输入合法的日期 (ISO).",
    number: "请输入合法的数字",
    digits: "只能输入整数",
    creditcard: "请输入合法的信用卡号",
    equalTo: "请再次输入相同的值",
    accept: "请输入拥有合法后缀名的字符串",
    maxlength: jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串"),
    minlength: jQuery.validator.format("请输入一个 长度最少是 {0} 的字符串"),
    rangelength: jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
    range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
    max: jQuery.validator.format("请输入一个最大为{0} 的值"),
    min: jQuery.validator.format("请输入一个最小为{0} 的值")
});
Date.prototype.format = function(format) {
    var o = {
        "M+": this.getMonth() + 1,
        // month
        "d+": this.getDate(),
        // day
        "h+": this.getHours(),
        // hour
        "m+": this.getMinutes(),
        // minute
        "s+": this.getSeconds(),
        // second
        "q+": Math.floor((this.getMonth() + 3) / 3),
        // quarter
        "S": this.getMilliseconds()
        // millisecond
    };
    if (/(y+)/.test(format) || /(Y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};
String.prototype.limit = function(num) {
    if(this.length>num){
        var text=this.substring(0,num)+"...";
        return text;
    }
    return this;
}

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
    var r = window.location.search.substr(1).match(reg);
    if (r!=null) return r[2]; return null;

}
function myBrowser(){
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera ; //判断是否IE浏览器
    var isFF = userAgent.indexOf("Firefox") > -1 ; //判断是否Firefox浏览器
    var isSafari = userAgent.indexOf("Safari") > -1 ; //判断是否Safari浏览器


    if(isIE){
        var IE5 = false;
        var IE55 = false;
        var IE6 = false;
        var IE7 = false;
        var IE8 = false;
        var IE9 = false;
        var reIE5 = new RegExp("MSIE 5.0;");
        var reIE55 = new RegExp("MSIE 5.5;");
        var reIE6 = new RegExp("MSIE 6.0;");
        var reIE7 = new RegExp("MSIE 7.0;");
        var reIE8 = new RegExp("MSIE 8.0;");
        var reIE9 = new RegExp("MSIE 9.0;");

        IE5 = userAgent.search(reIE55) > -1;
        IE55 = userAgent.search(reIE55) > -1;
        IE6 = userAgent.search(reIE6) > -1;
        IE7 = userAgent.search(reIE7) > -1;
        IE8 = userAgent.search(reIE8) > -1;
        IE9 = userAgent.search(reIE9) > -1;

        if(IE5){ return "IE5"; }
        if(IE55){ return "IE55"; }
        if(IE6){ return "IE6"; }
        if(IE7){ return "IE7"; }
        if(IE8){ return "IE8"; }
        if(document.documentMode <= 8){
            alert("请不要使用IE8及以下的浏览器兼容模式，否则图表可能看不到");
        }
        if(IE9){ return "IE9"; }
    }//isIE end

    if(isFF){ return "FF"; }
    if(isOpera){ return "Opera"; }

}

var dataTableLanguage = {
    "sProcessing": "正在加载中......",
    "sLengthMenu": "每页显示 _MENU_ 条记录",
    "sZeroRecords": "对不起，查询不到相关数据！",
    "sEmptyTable": "表中无数据存在！",
    "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
    "sInfoEmpty": "当前显示 0 到 0 条，共 0 条记录",
    "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
    "sSearch": "搜索",
    "oPaginate": {
        "sFirst": "首页",
        "sPrevious": "上一页",
        "sNext": "下一页",
        "sLast": "末页"
    }
};
