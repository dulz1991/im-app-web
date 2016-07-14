define(['views/friendDetailView'], function (View) {

	var bindings = [{
		element: '.btn-chat',
        event: 'click',
        handler: gotoChat
	},{
		element: '.btn-delete',
        event: 'click',
        handler: doDelete
	},{
		element: '.btn-newfriend',
        event: 'click',
        handler: addNewFriend
	}];

    function init(query) {
        myApp.showIndicator();
        $$.ajax({
            url: '/auth/friend/friendDtail',
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
    
    function addNewFriend(){
    	var friendUserId = $$(this).attr("frienduserid");
    	$$.ajax({
            url: '/auth/friend/addNewFriend',
            type: 'GET',
            data: {friendUserId:friendUserId},
            dataType: 'json',
            success: function (data) {
            	if(data.errorNo==200){
            		myApp.alert('发送请求成功, 请等待对方回应!', '提示');
            	}
            }
        });
    }
    
});
