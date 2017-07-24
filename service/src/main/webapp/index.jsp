<%
    /* response.sendRedirect(request.getContextPath() + "/product/list.do"); */
//    response.sendRedirect(request.getContextPath() + "/index.do");
%>

<%@ page language="java"  contentType="text/html; charset=GB2312" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <!--<script type="text/javascript" src="js/jquery-1.7.2.js"></script>-->
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <title>webSocket测试</title>
    <script type="text/javascript">
        $(function(){
            var websocket;
            var username = Math.random().toString(36).substr(2);
            if ('WebSocket' in window) {
                alert("WebSocket");
                websocket = new WebSocket("ws://localhost:8080/echo?username=" + username);
            } else if ('MozWebSocket' in window) {
                alert("MozWebSocket");
                websocket = new MozWebSocket("ws://echo?username=" + username);
            } else {
                alert("SockJS");
                websocket = new SockJS("http://localhost:8080/sockjs/echo?username=" + username);
            }
            websocket.onopen = function (evnt) {
//                $("#tou").html("链接服务器成功!")
                $("#msg").html($("#msg").html() + "<br/>" + "链接服务器成功!");
            };
            websocket.onmessage = function (evnt) {
                var msg = jQuery.parseJSON(evnt.data);
                console.log(msg);
                if(msg.code == 1){
                    $('.user-section ul').empty();
                    for(var i = 0; i < msg.obj.length; i ++){
                        if(username == msg.obj[i].name){
                            $('.user-section ul').append("<li class=\"active\"><a>"+ msg.obj[i].name +"</a></li>");
                        }else{
                            $('.user-section ul').append("<li><a>"+ msg.obj[i].name +"</a></li>");
                        }
                    }
                }
                if(msg.code == 2){
                    $("#msg").prepend( "<h5>"+ msg.obj.fromName +"</h5>" + "<p>"+ msg.obj.messageText +"</p>"+
                        "<h5>"+ timeStamp2String(msg.obj.messageDate) + "<br/>" + "</h5>");
                }
            };
            websocket.onerror = function (evnt) {
            };
            websocket.onclose = function (evnt) {
                $("#msg").html($("#msg").html() + "<br/>" + "与服务器断开了链接!");
            };
            $('#send').bind('click', function() {
                send();
                $('#message').val("");
            });
            $('#message').bind('keydown', function(event){
                if(event.keyCode == 13){
                    send();
                    $('#message').val("");
                }
            });
            function send(){
                if (websocket != null) {
                    var message = document.getElementById('message').value;
                    websocket.send(message);
                } else {
                    alert('未与服务器链接.');
                }
            }
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

        /* 秒转化时间字符串 */
        function timeStamp2String(time, pattern) {

            if (time == null || time == 0) {
                return "";
            } else {
                if (pattern) {
                    return (new Date(time*1000)).format(pattern);
                }
                return (new Date(time*1000)).format("yyyy-MM-dd hh:mm");
            }
        }
    </script>
    <style>
        .container{
            width: 960px;
            margin: 0 auto;
            height: auto;
        }
        .page-left #msg{
            height: 500px;
            overflow: auto;
        }
        .page-left #msg p{
            color: #bb2136;
            font-size: 14px;
        }
        .page-left #msg h5{
            font-size: 13px;
            color: #999;
        }
        .page-right ul{
            width: 100%;
            height: auto;
            max-height: 500px;
            overflow: auto;
            padding: 15px;
        }
        .page-right ul li{
            list-style: none;
        }
        .page-right ul li a{
            cursor: pointer;
        }
        .page-right ul li.active a{
            color: #bb2136;
        }
        .page-right .user-section{
            width: 100%;
            height: auto;
        }
        .page-right .user-section h5{
            width: 100%;
            height: 30px;
            line-height: 30px;
            color: #bb2136;
            font-size: 14px;
            border-bottom: 1px solid #eee;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="page-header" id="tou">
            webSocket及时聊天Demo程序
        </div>
        <div class="page-left col-xs-8">
            <div class="well" id="msg">

            </div>
            <div class="col-lg">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="发送信息..." id="message">
                    <span class="input-group-btn">
                    <button class="btn btn-default" type="button" id="send" >发送</button>
                  </span>
                </div>
            </div>
        </div>
        <div class="page-right col-xs-4">
            <div class="user-section">
                <h5>用户列表</h5>
                <ul>

                </ul>
            </div>
        </div>
    </div>

</body>

</html>
