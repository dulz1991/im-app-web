define(['GS'], function (GS) {

	var websocket = null;
	
	/******************************************************************
	 *websocke链接* 
	 ******************************************************************/
	//判断当前浏览器是否支持WebSocket
	if('WebSocket' in window){
		var currentUserId = GS.getCurrentUserId();
	    websocket = new WebSocket("ws://192.168.1.115:8200/websocket/" + currentUserId);
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
		var html='';
		var data = eval("(" + event.data + ")");
		if (data._friendFlag==1){
			html = appendMeMsg(data);
		} else {
			html = appendTaMsg(data);
			GS.updateLocalStorageChatList(data);
			appendMsgToToolbar(data);
		}
		$$('.messages-auto-layout').append(html);
	}
	 
	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	window.onbeforeunload = function(event){
	    websocket.close();
	}
	
	//发送消息
	function sendMessage(userId, msg){
	    var msg_content = userId + ',' + msg;
	    websocket.send(msg_content);
	}
	
	//关闭连接
	function closeWebSocket(){
	    websocket.close();
	}
	
	//显示消息到我的客户端
	function appendMeMsg(data) {
		var html = '';
		html += '<div class="message message-sent message-first">';
		html += '	<div class="message-text">'+data.message+'</div>';
		html += '</div>';
		return html;
	}
	//显示消息到朋友的客户端
	function appendTaMsg(data) {
		var html = '';
		html += '<div class="message message-received message-with-avatar message-last message-with-tail message-first">';
		html += '	<div class="message-name">'+data._username+'</div>';
		html += '	<div class="message-text">'+data.message+'</div>';
		html += '	<div style="background-image:url('+data._avatar+')" class="message-avatar"></div>';
		html += '</div>';
		return html;
	}
	//显示消息到客户端
	function appendMiddle(data) {
		var html = '<div class="messages-date">Sunday, Feb 9 <span>12:58</span></div>';
		return html;
	}
	//工具栏显示消息提示
	function appendMsgToToolbar(data) {
		var num = $$('.tab-link span.bg-red').text();
		if(num==""){
			num=1;
		} else {
			 num = parseInt(num) + 1;
		}
		$$('.tab-link span.bg-red').text(num);
	}
	
	return {
		sendMessage: sendMessage
	};

});


