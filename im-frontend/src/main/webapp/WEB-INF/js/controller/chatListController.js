define(['views/chatListView','GS'], function (View,GS) {

	var bindings = [{
		element: '.goto-chat',
        event: 'click',
        handler: gotoChat
	}];
	
    function init(query) {
        myApp.showIndicator();
        var chatList = GS.getLocalStorageByKey('chat_list');
        if(chatList!=null){
        	View.render({
                model: JSON.parse(chatList),
                bindings: bindings
            });
        }
        
        myApp.hideIndicator();
    }

    return {
        init: init
    };
    
    function gotoChat(){
    	var friendUserId = $$(this).attr("senduserid");
    	viewChat.loadPage('/pages/chat/chat.html?friendUserId='+friendUserId);
    	GS.clearChatStatus(friendUserId);
    	GS.refreshToolbar();
    }
    
});
