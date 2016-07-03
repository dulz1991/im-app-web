define(['views/chatRoomView'], function (View) {

	var bindings = [{
		element: '.btn-chat',
        event: 'click',
        handler: gotoChat
	},{
		element: '.btn-delete',
        event: 'click',
        handler: doDelete
	}];

    function init(query) {
        myApp.showIndicator();
        $$.ajax({
            url: '/auth/chat/chatRoom',
            data: {friendUserId:query.friendUserId},
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
    
    function gotoChat() {
    	
    }
    
    function doDelete() {
    	
    }
    
    
});
