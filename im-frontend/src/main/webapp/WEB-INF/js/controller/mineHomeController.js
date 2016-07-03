define(['views/mineHomeView'], function (View) {

	var bindings = [{
		element: '.setting',
        event: 'click',
        handler: setting
	}];

    function init(query) {
        myApp.showIndicator();
        $$.ajax({
            url: '/auth/mine/index',
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
    
    function setting() {
    	
    }
    
});
