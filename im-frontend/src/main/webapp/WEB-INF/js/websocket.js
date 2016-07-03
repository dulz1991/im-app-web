var websocket = null;
$(function () {
	/******************************************************************
	 *聊天界面* 
	 ******************************************************************/
    var _editArea = $('#editArea');  
    //显示隐藏发送按钮  
    var _editAreaInterval;  
    $('#editArea').focus(function () {  
        var _this = $(this), html;  
        _editAreaInterval = setInterval(function () {  
            html = _this.html();  
            if (html.length > 0) {  
                $('#web_wechat_pic').hide();  
                $('#btn_send').show();  
            } else {  
                $('#web_wechat_pic').show();  
                $('#btn_send').hide();  
            }  
        }, 200);  
    });  
    $('#editArea').blur(function () {  
        clearInterval(_editAreaInterval);  
    });  
    //显示隐藏表情栏  
    $('.web_wechat_face').click(function () {  
        $('.box_ft_bd').toggleClass('hide');  
        resetMessageArea();  
    });  
    //切换表情主题  
    $('.exp_hd_item').click(function () {  
        var _this = $(this), i = _this.data('i');  
        $('.exp_hd_item,.exp_cont').removeClass('active');  
        _this.addClass('active');  
        $('.exp_cont').eq(i).addClass('active');  
        resetMessageArea();  
    });  
    //选中表情  
    $('.exp_cont a').click(function () {  
        var _this = $(this);  
        var html = '<img class="' + _this[0].className + '" title="' + _this.html() + '" src="images/spacer.gif">';  
        _editArea.html(_editArea.html() + html);  
        $('#web_wechat_pic').hide();  
        $('#btn_send').show();  
    });  
    resetMessageArea(); 
});  

//刷新聊天面板
function resetMessageArea() {  
    $('#messageList').animate({ 'scrollTop': 1999 }, 500);  
}  
//刷新聊天输入框
function resetInputArea() {
	$('#editArea').text('');
	$('#btn_send').hide(); 
}

//显示消息到我的客户端
function appendMeMsg(data) {
	var html = '';
	html += '<div class="message me">';
	html += '	<img class="avatar" src="'+data._avatar+'" />';
	html += '	<div class="content">';
	html += '		<div class="nickname"><span class="time">'+data._createTimeStr+'</span></div>';
	html += '		<div class="bubble bubble_primary right">';
	html += '			<div class="bubble_cont">';
	html += '				<div class="plain">';
	html += '					<pre>'+data.message+'</pre>';
	html += '				</div>';
	html += '			</div>';
	html += '		</div>';
	html += '	</div>';
	html += '</div>';
	$('#messageList').append(html);
	//return html;
}
//显示消息到朋友的客户端
function appendTaMsg(data) {
	var html = '';
	html += '<div class="message">';
	html += '	<img class="avatar" src="'+data._avatar+'" />';
	html += '	<div class="content">';
	html += '		<div class="nickname">'+data._username+'<span class="time">'+data._createTimeStr+'</span></div>';
	html += '		<div class="bubble bubble_default left">';
	html += '			<div class="bubble_cont">';
	html += '				<div class="plain">';
	html += '					<pre>'+data.message+'</pre>';
	html += '				</div>';
	html += '			</div>';
	html += '		</div>';
	html += '	</div>';
	html += '</div>';
	$('#messageList').append(html);
	//return html;
}


/******************************************************************
 *websocke链接* 
 ******************************************************************/
//判断当前浏览器是否支持WebSocket
if('WebSocket' in window){
	var currentUserId = $('input[name="hidden_current_user_id"]').val();
    websocket = new WebSocket("ws://192.168.1.102:8200/websocket/" + currentUserId);
} else {
    alert('Not support websocket');
}
 
//连接发生错误的回调方法
websocket.onerror = function(event){
    //setMessageInnerHTML("error");
};
 
//连接成功建立的回调方法
websocket.onopen = function(event){
    //setMessageInnerHTML("open");
}

//连接关闭的回调方法
websocket.onclose = function(event){
    //setMessageInnerHTML("close");
}
 
//接收到消息的回调方法
websocket.onmessage = function(event){
    //setMessageInnerHTML(event.data);
	setMessageText(event.data);
}
 
//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function(event){
    websocket.close();
}
 
//将消息显示在网页上
function setMessageText(msg){
	var data = eval("(" + msg + ")");
	if (data._friendFlag==1){
		appendMeMsg(data);
	} else {
		appendTaMsg(data);
		updateLocalStorageChatList(data);
	}
	resetMessageArea();
}

//发送消息
function sendMessage(userId){
    var msg_text = $('#editArea').text();
    var msg = userId + ',' + msg_text;
    websocket.send(msg);
	resetInputArea();
}

//关闭连接
function closeWebSocket(){
    websocket.close();
}


