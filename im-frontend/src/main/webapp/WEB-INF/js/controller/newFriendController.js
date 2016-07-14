define(['views/newFriendView'], function (View) {

	var bindings = [{
		element: '.request-agree',
        event: 'click',
        handler: agree
	},{
		element: '.request-reject',
        event: 'click',
        handler: reject
	}];

    function init(query) {
        myApp.showIndicator();
        $$.ajax({
            url: '/auth/friend/getNewFriends',
            data: {},
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
    
    function agree() {
    	var swipeout = $$(this).parents('li.swipeout');
        $$.ajax({
            url: '/auth/friend/agreeNewFriend',
            data: {
            	friendUserId: $$(this).attr('frienduserid'),
            	id: $$(this).attr('id')
            },
            type: 'POST',
            dataType: 'json',
            success: function (data) {
            	if(data.errorNo==200){
            		myApp.swipeoutDelete(swipeout);	
            	}
            }
        });
    }
    
    function reject(){
    	$$.ajax({
            url: '/auth/friend/rejectNewFriend',
            data: {
            	friendUserId: $$(this).attr('frienduserid'),
            	id: $$(this).attr('id')
            },
            type: 'POST',
            dataType: 'json',
            success: function (data) {
            }
        });
    }
    
});
