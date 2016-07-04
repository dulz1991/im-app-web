define(['views/chatRoomView','WS'], function (View,WS) {

	var friendUserId;
	
	var bindings = [{
		element: '.btn-send',
        event: 'click',
        handler: sendMsg
	}];

    function init(query) {
        myApp.showIndicator();
        friendUserId = query.friendUserId;
        $$.ajax({
            url: '/auth/chat/chatRoom',
            data: {friendUserId:friendUserId},
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                View.render({
                    model: data,
                    bindings: bindings
                });
                myApp.hideIndicator();
            }
        });
    }

    return {
        init: init
    };
    
    function sendMsg() {
    	var msg = $$('.msg').val();
    	WS.sendMessage(friendUserId,msg);
    }
    
    
});
