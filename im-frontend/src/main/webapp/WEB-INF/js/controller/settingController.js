define(['views/settingView'], function (View) {

	var bindings = [{
		element: '.logout',
        event: 'click',
        handler: logout
	},{
		element: '.clear-chat',
        event: 'click',
        handler: chearChat
	},{
		element: '.clear-cache',
        event: 'click',
        handler: clearCache
	}];

    function init(query) {
    	myApp.showIndicator();
    	View.render({
            bindings: bindings
        });
    	myApp.hideIndicator();
    }

    return {
        init: init
    };
    
    function logout() {
    	myApp.confirm("确定退出?","退出提示", function () {
    		$$.ajax({
                url: '/account/logout',
                data: {},
                type: 'GET',
                dataType: 'json',
                success: function (data) {
                	if (data.errorNo == 200) {
                		window.location.href="/account/signIn.html";
                	} else {
                		myApp.alert(data.errorInfo, '提示：');
                	}
                }
            });
    	});
    }
    
    function chearChat() {
    	
    }

	function clearCache() {
	
	}
    
});
