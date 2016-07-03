define(['views/friendView'], function (View) {

	var bindings = [{
		element: '.friend-list ul li',
        event: 'click',
        handler: gotoFriendDetail
	}];

    function init(query) {
        myApp.showIndicator();
        $$.ajax({
            url: '/auth/friend/getFriends',
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
    
    function gotoFriendDetail() {
    	
    }
    
    
});
